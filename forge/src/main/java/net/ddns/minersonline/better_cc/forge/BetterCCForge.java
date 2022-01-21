package net.ddns.minersonline.better_cc.forge;

import dev.architectury.platform.forge.EventBuses;
import net.ddns.minersonline.better_cc.BetterCC;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterCC.MOD_ID)
public class BetterCCForge {
    public BetterCCForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BetterCC.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BetterCC.init();
    }
}
