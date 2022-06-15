package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.StreamSession;

public class StreamSessionImpl extends SessionBase implements StreamSession {
    private final StreamSessionDiscriminator discriminator;

    public StreamSessionImpl(final int ipAddress, final short port, final StreamSessionDiscriminator discriminator) {
        super(ipAddress, port);
        this.discriminator = discriminator;
    }

    @Override
    public StreamSessionDiscriminator getDiscriminator() {
        return discriminator;
    }
}
