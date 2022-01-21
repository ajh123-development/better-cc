package net.ddns.minersonline.better_cc.fabric;

import net.ddns.minersonline.better_cc.BetterCC;
import net.fabricmc.api.ModInitializer;

public class BetterCCFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterCC.init();
    }
}
