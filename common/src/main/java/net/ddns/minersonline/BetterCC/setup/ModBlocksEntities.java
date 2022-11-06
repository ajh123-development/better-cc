package net.ddns.minersonline.BetterCC.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.BetterCC.blocks.CraftingMachineEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlocksEntities {
	public static final RegistrySupplier<BlockEntityType<CraftingMachineEntity>> CRAFTING_MACHINE = register("crafting_machine",
			() -> BlockEntityType.Builder.of(CraftingMachineEntity::new, ModBlocks.CRAFTING_MACHINE.get()).build(null));

	private static <T extends BlockEntityType<?>> RegistrySupplier<T> register(String name, Supplier<T> block) {
		return Registration.BLOCK_ENTITIES.register(name, block);
	}

	static void register(){}
}
