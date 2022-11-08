package net.ddns.minersonline.BetterCC.forge;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import dev.architectury.platform.forge.EventBuses;
import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterCC.MOD_ID)
public class BetterCCForge {
	public BetterCCForge() {
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(BetterCC.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		BetterCC.init();
		ForgeComputerCraftAPI.registerPeripheralProvider(new SerialModemPeripheralProvider());
	}
}