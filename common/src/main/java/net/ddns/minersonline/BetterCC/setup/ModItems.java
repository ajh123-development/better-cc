package net.ddns.minersonline.BetterCC.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.BetterCC.BetterCC;
import net.ddns.minersonline.BetterCC.items.PunchTape;
import net.ddns.minersonline.BetterCC.items.WrenchItem;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final RegistrySupplier<WrenchItem> WRENCH_ITEM = Registration.ITEMS.register("wrench", () ->
			new WrenchItem(new Item.Properties().tab(BetterCC.MAIN_TAB)));

	public static final RegistrySupplier<PunchTape> PUNCH_TAPE = Registration.ITEMS.register("punch_tape", () ->
			new PunchTape(new Item.Properties().tab(BetterCC.MAIN_TAB)));

	static void register(){}
}
