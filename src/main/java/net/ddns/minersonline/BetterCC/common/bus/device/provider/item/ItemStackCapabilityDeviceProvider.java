/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;

import java.util.Optional;

public class ItemStackCapabilityDeviceProvider extends AbstractItemStackCapabilityDeviceProvider<Device> {
    public ItemStackCapabilityDeviceProvider() {
        super(Capabilities::device);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query, final Device value) {
        if (value instanceof ItemDevice itemDevice) {
            return Optional.of(itemDevice);
        } else {
            return Optional.empty();
        }
    }
}
