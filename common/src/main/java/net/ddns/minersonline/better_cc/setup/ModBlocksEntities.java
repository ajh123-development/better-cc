package net.ddns.minersonline.better_cc.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.better_cc.blocks.RandomEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlocksEntities {

    public static final RegistrySupplier<RandomEntity> RANDOM = register("random",
            () -> BlockEntityType.Builder.of(RandomEntity::new, ModBlocks.RANDOM.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> register(String name, Supplier<T> block) {
        return Registration.BLOCK_ENTITIES.register(name, block);
    }

    static void register(){}
}
