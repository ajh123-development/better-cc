/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.object.Callbacks;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockEntityDeviceProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class BlockEntityObjectDeviceProvider extends AbstractBlockEntityDeviceProvider<BlockEntity> {
    @Override
    public Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final BlockEntity blockEntity) {
        if (Callbacks.hasMethods(blockEntity)) {
            return Invalidatable.of(new ObjectDevice(blockEntity));
        } else {
            return Invalidatable.empty();
        }
    }
}
