package net.ddns.minersonline.BetterCC;

import dev.architectury.registry.CreativeTabRegistry;
import net.ddns.minersonline.BetterCC.setup.ModItems;
import net.ddns.minersonline.BetterCC.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BetterCC {
	public static final String MOD_ID = "better_cc";

	public static final CreativeModeTab MAIN_TAB = CreativeTabRegistry.create(
			new ResourceLocation(BetterCC.MOD_ID, "main"), // Tab ID
			() -> new ItemStack(ModItems.WRENCH_ITEM.get()) // Icon
	);

	public static void init() {
		Registration.register();
	}
}