/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.util;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceProvider;

import javax.annotation.Nullable;

public final class ItemDeviceInfo extends AbstractDeviceInfo<ItemDeviceProvider, ItemDevice> {
    public final int energyConsumption;

    ///////////////////////////////////////////////////////////////////

    public ItemDeviceInfo(@Nullable final ItemDeviceProvider itemDeviceProvider, final ItemDevice device, final int energyConsumption) {
        super(itemDeviceProvider, device);
        this.energyConsumption = energyConsumption;
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public int getEnergyConsumption() {
        return energyConsumption;
    }
}
