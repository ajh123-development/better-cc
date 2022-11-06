package net.ddns.minersonline.BetterCC.setup;

import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static final TagKey<Block> SERIAL_CABLE_CONNECTABLE = createBlockTag("serial_cable_connectable");

	private static TagKey<Block> createBlockTag(String string) {
		return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BetterCC.MOD_ID, string));
	}

	static void register(){}
}
