/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockEntityCapabilityDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.EnergyStorageDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyStorageBlockDeviceProvider extends AbstractBlockEntityCapabilityDeviceProvider<IEnergyStorage, BlockEntity> {
    public EnergyStorageBlockDeviceProvider() {
        super(Capabilities::energyStorage);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final IEnergyStorage value) {
        return Invalidatable.of(new ObjectDevice(new EnergyStorageDevice(value)));
    }
}
