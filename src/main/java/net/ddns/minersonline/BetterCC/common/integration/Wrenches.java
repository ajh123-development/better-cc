/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.integration;

import net.ddns.minersonline.BetterCC.common.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public final class Wrenches {
    public static boolean isWrench(@Nullable final ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.is(ItemTags.WRENCHES);
    }

    public static boolean isHoldingWrench(final Entity entity) {
        for (final ItemStack stack : entity.getHandSlots()) {
            if (isWrench(stack)) {
                return true;
            }
        }

        return false;
    }
}
