/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.item;

import net.ddns.minersonline.BetterCC.common.Config;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public final class ChargerItem extends ModBlockItem {
    public ChargerItem(final Block block) {
        super(block);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fillItemCategory(final CreativeModeTab tab, final NonNullList<ItemStack> items) {
        if (Config.chargerUseEnergy()) {
            super.fillItemCategory(tab, items);
        }
    }
}
