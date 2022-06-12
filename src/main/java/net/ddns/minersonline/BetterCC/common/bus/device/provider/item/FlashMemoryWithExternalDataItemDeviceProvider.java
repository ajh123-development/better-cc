/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.data.Firmware;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.vm.item.FirmwareFlashStorageDevice;
import net.ddns.minersonline.BetterCC.common.item.FlashMemoryWithExternalDataItem;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class FlashMemoryWithExternalDataItemDeviceProvider extends AbstractItemDeviceProvider {
    public FlashMemoryWithExternalDataItemDeviceProvider() {
        super(FlashMemoryWithExternalDataItem.class);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        final ItemStack stack = query.getItemStack();
        final FlashMemoryWithExternalDataItem item = (FlashMemoryWithExternalDataItem) stack.getItem();

        final Firmware firmware = item.getFirmware(stack);
        if (firmware == null) {
            return Optional.empty();
        }

        return Optional.of(new FirmwareFlashStorageDevice(stack, firmware));
    }
}
