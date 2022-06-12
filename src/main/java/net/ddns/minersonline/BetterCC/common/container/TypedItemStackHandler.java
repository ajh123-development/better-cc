/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.container;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TypedItemStackHandler extends FixedSizeItemStackHandler {
    private final TagKey<Item> deviceType;

    ///////////////////////////////////////////////////////////////////

    public TypedItemStackHandler(final int size, final TagKey<Item> deviceType) {
        super(size);
        this.deviceType = deviceType;
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public boolean isItemValid(final int slot, final ItemStack stack) {
        return super.isItemValid(slot, stack) && !stack.isEmpty() && stack.is(deviceType);
    }
}
