package net.ddns.minersonline.better_cc.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.better_cc.BetterCC;
import net.ddns.minersonline.better_cc.blocks.RandomBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModBlocks {
    // Resources
    public static final RegistrySupplier<Block> SILVER_ORE = register("silver_ore", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).strength(4.5f, 3.0f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(3, 10)));


    // Machines
    public static final RegistrySupplier<RandomBlock> RANDOM = register("random", () ->
            new RandomBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));

    private static <T extends Block> RegistrySupplier<Block> registerNoItem(String name, Supplier<T> block){
        return Registration.BLOCKS.register(name, block);
    }
    private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block) {
        RegistrySupplier<T> ret = (RegistrySupplier<T>) registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(BetterCC.MAIN_TAB)));

        return ret;
    }


    static void register(){}
}
