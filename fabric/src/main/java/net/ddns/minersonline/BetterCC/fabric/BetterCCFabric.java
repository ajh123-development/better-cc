package net.ddns.minersonline.BetterCC.fabric;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.fabricmc.api.ModInitializer;

public class BetterCCFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		BetterCC.init();
	}
}