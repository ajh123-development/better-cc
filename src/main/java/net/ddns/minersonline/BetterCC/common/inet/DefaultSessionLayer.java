package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.DatagramSession;
import net.ddns.minersonline.BetterCC.api.inet.EchoSession;
import net.ddns.minersonline.BetterCC.api.inet.Session;
import net.ddns.minersonline.BetterCC.api.inet.SessionLayer;
import net.ddns.minersonline.BetterCC.common.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public final class DefaultSessionLayer implements SessionLayer {
    private static final Logger LOGGER = LogManager.getLogger();

    ///////////////////////////////////////////////////////////////////

    private static final Executor executor = Executors
        .newSingleThreadExecutor(runnable -> new Thread(runnable, "internet/blocking-session"));

    private static final Selector selector;

    ///////////////////////////////////////////////////////////////////

    static {
        Selector newSelector;
        try {
            newSelector = Selector.open();
        } catch (IOException e) {
            LOGGER.error("Failed to open selector", e);
            newSelector = null;
        }
        selector = newSelector;
        InternetManager.registerNetworkThreadAction(DefaultSessionLayer::selectAction);
    }

    private final AtomicReference<EchoResponse> echoResponse = new AtomicReference<>(null);

    ///////////////////////////////////////////////////////////////////
    private final Set<SelectionKey> datagramKeys = new HashSet<>();

    private static void selectAction() {
        try {
            selector.selectNow();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private static SelectionKey register(
        final SelectableChannel channel,
        final Session session,
        final int ops
    ) throws IOException {
        channel.configureBlocking(false);
        return channel.register(selector, ops, session);
    }

    @Override
    public void receiveSession(final SessionLayer.Receiver receiver) {
        final EchoResponse pending = echoResponse.getAndSet(null);
        if (pending != null) {
            final ByteBuffer data = receiver.receive(pending.session);
            assert data != null;
            data.put(pending.payload);
            data.flip();
            return;
        }

        for (final SelectionKey key : datagramKeys) {
            if (key.isReadable()) {
                LOGGER.info("Datagram received");
                final DatagramChannel channel = (DatagramChannel) key.channel();
                try {
                    final DatagramSession session = (DatagramSession) key.attachment();
                    final ByteBuffer datagram = receiver.receive(session);
                    assert datagram != null;
                    final SocketAddress address = channel.receive(datagram);
                    if (address == null) {
                        continue;
                    }
                    if (Config.useSynchronisedNAT && !address.equals(session.getDestination())) {
                        continue;
                    }
                    datagram.flip();
                    return;
                } catch (IOException e) {
                    LOGGER.error("Trying to read datagram socket", e);
                }
            }
        }
    }

    @Override
    public void sendSession(final Session session, @Nullable final ByteBuffer data) {
        if (session instanceof EchoSession) {
            if (data == null) {
                return; // session closed due expiration
            }
            final EchoSession echoSession = (EchoSession) session;
            final EchoResponse response = new EchoResponse(data, echoSession);
            final InetAddress address = session.getDestination().getAddress();
            executor.execute(() -> {
                try {
                    if (address.isReachable(null, echoSession.getTtl(), Config.defaultEchoRequestTimeoutMs)) {
                        echoResponse.set(response);
                    }
                } catch (IOException e) {
                    LOGGER.error("Failed to get echo response", e);
                }
            });
        } else if (session instanceof DatagramSession) {
            try {
                switch (session.getState()) {
                    case NEW: {
                        final DatagramChannel channel = DatagramChannel.open();
                        channel.configureBlocking(false);
                        final SelectionKey key =
                            register(channel, session, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        session.setUserdata(key);
                        datagramKeys.add(key);
                        LOGGER.info("Open datagram socket {}", session.getDestination());
                        /* Fallthrough */
                    }
                    case ESTABLISHED: {
                        final SelectionKey key = (SelectionKey) session.getUserdata();
                        assert key != null;
                        if (key.isWritable()) {
                            final DatagramChannel channel = (DatagramChannel) key.channel();
                            assert data != null;
                            channel.send(data, session.getDestination());
                        }
                        break;
                    }
                    case EXPIRED: {
                        final SelectionKey key = (SelectionKey) session.getUserdata();
                        assert key != null;
                        key.channel().close();
                        datagramKeys.remove(key);
                        LOGGER.info("Close datagram socket {}", session.getDestination());
                        break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Datagram session failure", e);
                session.close();
            }
        } else {
            session.close();
        }
    }

    ///////////////////////////////////////////////////////////////////

    private static final class EchoResponse {
        final byte[] payload;
        final EchoSession session;

        public EchoResponse(final ByteBuffer payload, final EchoSession session) {
            this.payload = new byte[payload.remaining()];
            payload.get(this.payload);
            this.session = session;
        }
    }
}
