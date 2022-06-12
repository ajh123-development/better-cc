/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockEntityCapabilityDeviceProvider;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class BlockEntityCapabilityDeviceProvider extends AbstractBlockEntityCapabilityDeviceProvider<Device, BlockEntity> {
    public BlockEntityCapabilityDeviceProvider() {
        super(Capabilities::device);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final Device device) {
        return Invalidatable.of(device);
    }
}
