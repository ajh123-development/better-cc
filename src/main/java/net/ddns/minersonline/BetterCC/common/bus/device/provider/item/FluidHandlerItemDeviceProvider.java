/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.FluidHandlerDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Optional;

public final class FluidHandlerItemDeviceProvider extends AbstractItemStackCapabilityDeviceProvider<IFluidHandler> {
    public FluidHandlerItemDeviceProvider() {
        super(Capabilities::fluidHandler);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query, final IFluidHandler value) {
        return Optional.of(new ObjectDevice(new FluidHandlerDevice(value)));
    }
}
