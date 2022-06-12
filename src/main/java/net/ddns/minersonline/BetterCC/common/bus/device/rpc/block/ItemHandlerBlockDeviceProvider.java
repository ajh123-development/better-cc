/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockEntityCapabilityDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.ItemHandlerDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

public final class ItemHandlerBlockDeviceProvider extends AbstractBlockEntityCapabilityDeviceProvider<IItemHandler, BlockEntity> {
    public ItemHandlerBlockDeviceProvider() {
        super(Capabilities::itemHandler);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final IItemHandler value) {
        return Invalidatable.of(new ObjectDevice(new ItemHandlerDevice(value)));
    }
}
