package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.*;
import net.ddns.minersonline.BetterCC.common.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

public final class DefaultTransportLayer implements TransportLayer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final byte ICMP_TYPE_ECHO_REPLY = 0;

    ///////////////////////////////////////////////////////////
    private static final byte ICMP_TYPE_ECHO_REQUEST = 8;
    private static final byte ICMP_TYPE_ECHO_UNREACHABLE = 3;
    private static final byte ICMP_CODE_ECHO_UNREACHABLE_PORT = 3;
    private static final byte ICMP_CODE_ECHO_UNREACHABLE_PROHIBITED = 13;
    private static final short PORT_ECHO = 7;
    private static final int ICMP_HEADER_SIZE = 8;
    private static final int UDP_HEADER_SIZE = 8;
    private static int allSessionCount = 0;

    ///////////////////////////////////////////////////////////
    private final SessionLayer sessionLayer;

    private final SessionReceiver receiver = new SessionReceiver();

    private final NavigableMap<Instant, SessionBase> expirationQueue = new TreeMap<>();
    private final Map<SessionDiscriminator<?>, SessionBase> sessions = new HashMap<>();

    private ICMPReply icmpReply = null;

    ///////////////////////////////////////////////////////////

    public DefaultTransportLayer(final SessionLayer sessionLayer) {
        this.sessionLayer = sessionLayer;
    }

    private void processExpirationQueue() {
        if (expirationQueue.isEmpty()) {
            return;
        }
        final Instant now = Instant.now();
        final Iterator<Map.Entry<Instant, SessionBase>> iterator = expirationQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Instant, SessionBase> entry = iterator.next();
            if (entry.getKey().compareTo(now) < 0) {
                iterator.remove();
                final SessionBase session = entry.getValue();
                sessions.remove(session.getDiscriminator());
                --allSessionCount;
                LOGGER.info("Expired session {}", session.getDiscriminator());
                session.setState(Session.States.EXPIRED);
                sessionLayer.sendSession(session, null);
            } else {
                return;
            }
        }
    }

    private void updateSession(final SessionBase session) {
        final Instant oldKey = session.getExpireTime();
        if (oldKey != null) {
            expirationQueue.remove(oldKey);
        }
        session.updateExpireTime();
        final Instant newExpireTime = session.getExpireTime();
        SessionBase previous = expirationQueue.put(newExpireTime, session);
        assert previous == null;
    }

    private void closeSession(final SessionBase session) {
        LOGGER.info("Close session {}", session.getDiscriminator());
        sessions.remove(session.getDiscriminator());
        expirationQueue.remove(session.getExpireTime());
        --allSessionCount;
    }

    private void prepareIcmpHeader(final ByteBuffer buffer, final byte type, final byte code) {
        final int position = buffer.position();
        buffer.put(type);
        buffer.put(code);
        buffer.putShort((short) 0);
        buffer.position(position);
        short checksum = InetUtils.rfc1071Checksum(buffer);
        buffer.putShort(position + 2, checksum);
        buffer.position(position);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <S extends SessionBase, D extends SessionDiscriminator<S>> S getOrCreateSession(
        final D discriminator,
        final Function<D, S> factory
    ) {
        final S session = (S) sessions.get(discriminator);
        if (session != null) {
            return session;
        }
        if (sessions.size() >= Config.defaultSessionsNumberPerCardLimit) {
            LOGGER.warn("Session count per card limit has reached");
            return null;
        }
        if (allSessionCount >= Config.defaultSessionsNumberLimit) {
            LOGGER.warn("Session count limit has reached");
            return null;
        }
        ++allSessionCount;
        LOGGER.info("New session: {}", discriminator);
        final S newSession = factory.apply(discriminator);
        sessions.put(discriminator, newSession);
        updateSession(newSession);
        return newSession;
    }

    private void reject(final ByteBuffer payload, final int srcIpAddress) {
        final byte[] data = InetUtils.quickICMPBody(payload);
        icmpReply = new ICMPReply(
            ICMP_TYPE_ECHO_UNREACHABLE,
            ICMP_CODE_ECHO_UNREACHABLE_PROHIBITED,
            0,
            srcIpAddress,
            data
        );
    }

    private void sessionSendFinish(final SessionBase session, final ByteBuffer payload, final int srcIpAddress) {
        final Session.States state = session.getState();
        switch (state) {
            case NEW:
                session.setState(Session.States.ESTABLISHED);
                break;
            case REJECT: {
                reject(payload, srcIpAddress);
                LOGGER.info("Reject session {}", session.getDiscriminator());
                /* Fallthrough */
            }
            case FINISH:
                closeSession(session);
                break;
            case ESTABLISHED:
                break;
            default:
                throw new IllegalStateException(state.name());
        }
    }

    @Override
    public byte receiveTransportMessage(final TransportMessage message) {
        processExpirationQueue();

        while (true) {
            if (icmpReply != null) {
                message.updateIpv4(icmpReply.srcIpAddress, icmpReply.dstIpAddress);
                final ByteBuffer data = message.getData();
                final int position = data.position();
                data.putInt(0);
                data.put(icmpReply.payload);
                data.limit(data.position());
                data.position(position);
                prepareIcmpHeader(data, icmpReply.type, icmpReply.code);
                icmpReply = null;
                return PROTOCOL_ICMP;
            }

            receiver.prepare(message.getData());
            sessionLayer.receiveSession(receiver);

            final SessionBase session = receiver.session;

            if (session == null) {
                return PROTOCOL_NONE;
            }

            updateSession(session);

            if (session instanceof EchoSession) {
                final EchoSessionImpl echoSession = (EchoSessionImpl) session;
                switch (session.getState()) {
                    case FINISH:
                        closeSession(session);
                        break;
                    case ESTABLISHED: {
                        final EchoSessionDiscriminator discriminator = echoSession.getDiscriminator();
                        final ByteBuffer buffer = receiver.getBuffer();
                        final int position = buffer.position();
                        buffer.putShort(position + 4, discriminator.getIdentity());
                        buffer.putShort(position + 6, (short) echoSession.getSequenceNumber());
                        prepareIcmpHeader(buffer, ICMP_TYPE_ECHO_REPLY, (byte) 0);
                        message.updateIpv4(discriminator.getDstIpAddress(), discriminator.getSrcIpAddress());
                        return PROTOCOL_ICMP;
                    }
                    default:
                        throw new IllegalStateException();
                }
            } else if (session instanceof DatagramSession) {
                final DatagramSessionImpl datagramSession = (DatagramSessionImpl) session;
                switch (session.getState()) {
                    case FINISH:
                        closeSession(session);
                        break;
                    case ESTABLISHED: {
                        final DatagramSessionDiscriminator discriminator = datagramSession.getDiscriminator();
                        final ByteBuffer buffer = receiver.getBuffer();
                        final int position = buffer.position();
                        buffer.putShort(position, discriminator.getDstPort());
                        buffer.putShort(position + 2, discriminator.getSrcPort());
                        buffer.putShort(position + 4, (short) buffer.remaining());
                        buffer.putShort(position + 6, (short) 0);
                        short checksum = InetUtils.transportRfc1071Checksum(
                            buffer,
                            discriminator.getDstIpAddress(),
                            discriminator.getSrcIpAddress(),
                            PROTOCOL_UDP
                        );
                        buffer.putShort(position + 6, checksum);
                        buffer.position(position);
                        message.updateIpv4(discriminator.getDstIpAddress(), discriminator.getSrcIpAddress());
                        return PROTOCOL_UDP;
                    }
                    default:
                        throw new IllegalStateException();
                }
            } else if (session instanceof StreamSession) {
                throw new UnsupportedOperationException("TODO");
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public void sendTransportMessage(final byte protocol, final TransportMessage message) {
        processExpirationQueue();

        final int srcIpAddress = message.getSrcIpv4Address();
        final int dstIpAddress = message.getDstIpv4Address();

        final ByteBuffer data = message.getData();

        switch (protocol) {
            case PROTOCOL_ICMP: {
                if (data.remaining() < ICMP_HEADER_SIZE) {
                    return;
                }

                final byte type = data.get();
                final byte code = data.get();
                data.getShort(); // we don't expect incorrect checksum

                if (type == ICMP_TYPE_ECHO_REQUEST) {
                    if (code != 0) {
                        return;
                    }
                    final short identity = data.getShort();
                    final short sequence = data.getShort();
                    final EchoSessionDiscriminator discriminator =
                        new EchoSessionDiscriminator(srcIpAddress, dstIpAddress, identity);
                    final EchoSessionImpl session =
                        getOrCreateSession(discriminator, it -> new EchoSessionImpl(dstIpAddress, PORT_ECHO, it));
                    if (session == null) {
                        reject(data, srcIpAddress);
                    } else {
                        session.setSequenceNumber(sequence);
                        session.setTtl(message.getTtl());
                        sessionLayer.sendSession(session, data);
                        sessionSendFinish(session, data, srcIpAddress);
                    }
                }
                break;
            }
            case PROTOCOL_UDP: {
                if (data.remaining() < UDP_HEADER_SIZE) {
                    return;
                }

                final short srcPort = data.getShort();
                final short dstPort = data.getShort();
                final int datagramLength = Short.toUnsignedInt(data.getShort());
                data.getShort(); // udp checksum

                if (data.remaining() + UDP_HEADER_SIZE < datagramLength) {
                    return;
                }
                data.limit(data.position() + datagramLength - UDP_HEADER_SIZE);

                final DatagramSessionDiscriminator discriminator =
                    new DatagramSessionDiscriminator(srcIpAddress, srcPort, dstIpAddress, dstPort);
                final DatagramSessionImpl session =
                    getOrCreateSession(discriminator, it -> new DatagramSessionImpl(dstIpAddress, dstPort, it));
                if (session == null) {
                    reject(data, srcIpAddress);
                } else {
                    sessionLayer.sendSession(session, data);
                    sessionSendFinish(session, data, srcIpAddress);
                }
                break;
            }
        }
    }

    private static final class ICMPReply {
        private final byte type;
        private final byte code;
        private final int srcIpAddress;
        private final int dstIpAddress;
        private final byte[] payload;

        public ICMPReply(final byte type, final byte code, final int srcIpAddress, final int dstIpAddress, final byte[] payload) {
            this.type = type;
            this.code = code;
            this.srcIpAddress = srcIpAddress;
            this.dstIpAddress = dstIpAddress;
            this.payload = payload;
        }
    }

    private static final class SessionReceiver implements SessionLayer.Receiver {
        private SessionBase session = null;
        private ByteBuffer buffer = null;
        private int position = 0;
        private int limit = 0;

        private void prepare(final ByteBuffer buffer) {
            session = null;
            this.buffer = buffer;
            position = buffer.position();
            limit = buffer.limit();
        }

        private ByteBuffer getBuffer() {
            buffer.position(position);
            return buffer;
        }

        @Nullable
        @Override
        public ByteBuffer receive(final Session session) {
            buffer.position(position);
            buffer.limit(limit);
            this.session = (SessionBase) session;
            switch (session.getState()) {
                case NEW:
                case FINISH:
                case REJECT:
                    return null;
                case ESTABLISHED:
                    if (session instanceof EchoSession || session instanceof DatagramSession) {
                        buffer.putLong(0);
                        return buffer;
                    } else if (session instanceof StreamSession) {
                        throw new UnsupportedOperationException("TODO");
                    } else {
                        throw new IllegalArgumentException("session");
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
