/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockEntityCapabilityDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.FluidHandlerDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;

public final class FluidHandlerBlockDeviceProvider extends AbstractBlockEntityCapabilityDeviceProvider<IFluidHandler, BlockEntity> {
    public FluidHandlerBlockDeviceProvider() {
        super(Capabilities::fluidHandler);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final IFluidHandler value) {
        return Invalidatable.of(new ObjectDevice(new FluidHandlerDevice(value)));
    }
}
