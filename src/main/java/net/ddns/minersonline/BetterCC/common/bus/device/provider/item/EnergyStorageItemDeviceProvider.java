/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.EnergyStorageDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Optional;

public final class EnergyStorageItemDeviceProvider extends AbstractItemStackCapabilityDeviceProvider<IEnergyStorage> {
    public EnergyStorageItemDeviceProvider() {
        super(Capabilities::energyStorage);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query, final IEnergyStorage value) {
        return Optional.of(new ObjectDevice(new EnergyStorageDevice(value)));
    }
}
