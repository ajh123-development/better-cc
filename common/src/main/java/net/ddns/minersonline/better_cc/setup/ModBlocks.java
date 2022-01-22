package net.ddns.minersonline.better_cc.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.better_cc.BetterCC;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ModBlocks {
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
