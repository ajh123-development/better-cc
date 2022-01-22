package net.ddns.minersonline.better_cc.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.better_cc.BetterCC;
import net.ddns.minersonline.better_cc.items.WrenchItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final RegistrySupplier<WrenchItem> WRENCH_ITEM = Registration.ITEMS.register("wrench", () ->
            new WrenchItem(new Item.Properties().tab(BetterCC.MAIN_TAB)));

    public static final RegistrySupplier<WrenchItem> SILVER_INGOT  = Registration.ITEMS.register("silver_ingot", () ->
            new WrenchItem(new Item.Properties().tab(BetterCC.MAIN_TAB)));

    static void register(){}
}
