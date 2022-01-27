package net.ddns.minersonline.better_cc.fabric;

import dan200.computercraft.api.ComputerCraftAPI;
import net.ddns.minersonline.better_cc.BetterCC;
import net.ddns.minersonline.better_cc.fabric.peripherals.PeripheralsProvider;
import net.fabricmc.api.ModInitializer;

public class BetterCCFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterCC.init();
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralsProvider());
    }
}
