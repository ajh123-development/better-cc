/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.block;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.object.Callbacks;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractBlockDeviceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class BlockStateObjectDeviceProvider extends AbstractBlockDeviceProvider {
    @Override
    public Invalidatable<Device> getDevice(final BlockDeviceQuery query) {
        final LevelAccessor level = query.getLevel();
        final BlockPos position = query.getQueryPosition();

        final BlockState blockState = level.getBlockState(position);

        if (blockState.isAir()) {
            return Invalidatable.empty();
        }

        final Block block = blockState.getBlock();
        if (!Callbacks.hasMethods(block)) {
            return Invalidatable.empty();
        }

        return Invalidatable.of(new ObjectDevice(block));
    }
}
