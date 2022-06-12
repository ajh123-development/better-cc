/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.vm.item.ByteBufferFlashStorageDevice;
import net.ddns.minersonline.BetterCC.common.item.FlashMemoryItem;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class FlashMemoryItemDeviceProvider extends AbstractItemDeviceProvider {
    public FlashMemoryItemDeviceProvider() {
        super(FlashMemoryItem.class);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        final ItemStack stack = query.getItemStack();
        final FlashMemoryItem item = (FlashMemoryItem) stack.getItem();

        final int capacity = Math.max(item.getCapacity(stack), 0);
        return Optional.of(new ByteBufferFlashStorageDevice(stack, capacity));
    }
}
