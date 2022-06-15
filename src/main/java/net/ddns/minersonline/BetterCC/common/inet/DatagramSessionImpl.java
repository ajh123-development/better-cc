package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.DatagramSession;

public final class DatagramSessionImpl extends SessionBase implements DatagramSession {
    private final DatagramSessionDiscriminator discriminator;

    public DatagramSessionImpl(final int ipAddress, final short port, final DatagramSessionDiscriminator discriminator) {
        super(ipAddress, port);
        this.discriminator = discriminator;
    }

    @Override
    public DatagramSessionDiscriminator getDiscriminator() {
        return discriminator;
    }
}
