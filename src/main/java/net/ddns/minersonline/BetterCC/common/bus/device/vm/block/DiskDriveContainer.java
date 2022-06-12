/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.vm.block;

import net.minecraft.world.item.ItemStack;

public interface DiskDriveContainer {
    ItemStack getDiskItemStack();

    void handleDataAccess();
}
