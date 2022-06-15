package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.LinkLocalLayer;

public final class NullLinkLocalLayer implements LinkLocalLayer {
    public static final NullLinkLocalLayer INSTANCE = new NullLinkLocalLayer();

    private NullLinkLocalLayer() {
    }
}
