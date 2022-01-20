package net.ddns.minersonline.better_cc.fabric;

import net.ddns.minersonline.better_cc.ExampleMod;
import net.fabricmc.api.ModInitializer;

public class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExampleMod.init();
    }
}
