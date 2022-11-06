package net.ddns.minersonline.BetterCC.setup;

import dev.architectury.registry.registries.DeferredRegister;
import net.ddns.minersonline.BetterCC.BetterCC;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class Registration {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterCC.MOD_ID, Registry.ITEM_REGISTRY);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BetterCC.MOD_ID, Registry.BLOCK_REGISTRY);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BetterCC.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);


	public static void register() {
		ITEMS.register();
		BLOCKS.register();
		BLOCK_ENTITIES.register();

		ModItems.register();
		ModBlocks.register();
		ModBlocksEntities.register();
	}
}