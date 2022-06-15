package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.SessionLayer;

public final class NullSessionLayer implements SessionLayer {
    public static final NullSessionLayer INSTANCE = new NullSessionLayer();

    private NullSessionLayer() {
    }
}
