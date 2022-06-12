/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ItemDeviceUtils {
    private static final String ITEM_DEVICE_DATA_TAG_NAME = "item_device";

    ///////////////////////////////////////////////////////////////////

    public static CompoundTag getItemDeviceData(final ItemStack stack) {
        return ItemStackUtils.getModDataTag(stack).getCompound(ITEM_DEVICE_DATA_TAG_NAME);
    }

    public static void setItemDeviceData(final ItemStack stack, final CompoundTag data) {
        ItemStackUtils.getOrCreateModDataTag(stack).put(ITEM_DEVICE_DATA_TAG_NAME, data);
    }
}
