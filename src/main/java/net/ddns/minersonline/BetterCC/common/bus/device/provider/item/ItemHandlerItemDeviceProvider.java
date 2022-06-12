/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.ItemHandlerDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public final class ItemHandlerItemDeviceProvider extends AbstractItemStackCapabilityDeviceProvider<IItemHandler> {
    public ItemHandlerItemDeviceProvider() {
        super(Capabilities::itemHandler);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query, final IItemHandler value) {
        return Optional.of(new ObjectDevice(new ItemHandlerDevice(value)));
    }
}
