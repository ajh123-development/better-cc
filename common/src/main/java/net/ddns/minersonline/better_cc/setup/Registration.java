package net.ddns.minersonline.better_cc.setup;

import dev.architectury.registry.registries.DeferredRegister;
import net.ddns.minersonline.better_cc.BetterCC;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterCC.MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BetterCC.MOD_ID, Registry.BLOCK_REGISTRY);

    public static void register() {
        ITEMS.register();

        ModItems.register();
    }
}
