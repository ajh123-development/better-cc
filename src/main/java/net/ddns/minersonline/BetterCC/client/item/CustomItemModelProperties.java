/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.client.item;

import net.ddns.minersonline.BetterCC.api.API;
import net.ddns.minersonline.BetterCC.common.item.Items;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public final class CustomItemModelProperties {
    public static final ResourceLocation COLOR_PROPERTY = new ResourceLocation(API.MOD_ID, "color");

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
        ItemProperties.register(Items.HARD_DRIVE_SMALL.get(), CustomItemModelProperties.COLOR_PROPERTY,
            (stack, level, entity, seed) -> CustomItemColors.getColor(stack));
        ItemProperties.register(Items.HARD_DRIVE_MEDIUM.get(), CustomItemModelProperties.COLOR_PROPERTY,
            (stack, level, entity, seed) -> CustomItemColors.getColor(stack));
        ItemProperties.register(Items.HARD_DRIVE_LARGE.get(), CustomItemModelProperties.COLOR_PROPERTY,
            (stack, leve, entity, seed) -> CustomItemColors.getColor(stack));
        ItemProperties.register(Items.HARD_DRIVE_CUSTOM.get(), CustomItemModelProperties.COLOR_PROPERTY,
            (stack, level, entity, seed) -> CustomItemColors.getColor(stack));
        ItemProperties.register(Items.FLOPPY.get(), CustomItemModelProperties.COLOR_PROPERTY,
            (stack, level, entity, seed) -> CustomItemColors.getColor(stack));
    }
}
