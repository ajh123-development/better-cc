package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.items.wrench.WrenchItem;
import net.ddns.minersonline.better_cc.items.punch_card.PunchCardItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import static net.ddns.minersonline.better_cc.better_cc.ITEM_GROUP;

public class ModItems {
    public static final RegistryObject<WrenchItem> WRENCH = Registration.ITEMS.register("wrench", () ->
            new WrenchItem(new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistryObject<Item> SILVER_INGOT = Registration.ITEMS.register("silver_ingot", () ->
        new Item(new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistryObject<PunchCardItem> PUNCH_CARD = Registration.ITEMS.register("punch_card", () ->
            new PunchCardItem(new Item.Properties().tab(ITEM_GROUP)));

    static void register(){}
}
