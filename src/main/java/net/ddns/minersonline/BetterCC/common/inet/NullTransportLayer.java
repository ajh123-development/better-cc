package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.TransportLayer;

public final class NullTransportLayer implements TransportLayer {
    public static final NullTransportLayer INSTANCE = new NullTransportLayer();

    private NullTransportLayer() {
    }
}
