/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.item.RedstoneInterfaceCardItemDevice;
import net.ddns.minersonline.BetterCC.common.item.Items;

import java.util.Optional;

public final class RedstoneInterfaceCardItemDeviceProvider extends AbstractItemDeviceProvider {
    public RedstoneInterfaceCardItemDeviceProvider() {
        super(Items.REDSTONE_INTERFACE_CARD);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        return query.getContainerBlockEntity().map(blockEntity ->
            new RedstoneInterfaceCardItemDevice(query.getItemStack(), blockEntity));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        return Config.redstoneInterfaceCardEnergyPerTick;
    }
}
