package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.NetworkLayer;

public final class NullNetworkLayer implements NetworkLayer {
    public static final NullNetworkLayer INSTANCE = new NullNetworkLayer();

    private NullNetworkLayer() {
    }
}
