/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.util;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceQuery;
import net.ddns.minersonline.BetterCC.api.util.Invalidatable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class AbstractBlockEntityDeviceProvider<T extends BlockEntity> extends AbstractBlockDeviceProvider {
    private final BlockEntityType<T> blockEntityType;

    ///////////////////////////////////////////////////////////////////

    protected AbstractBlockEntityDeviceProvider(final BlockEntityType<T> blockEntityType) {
        this.blockEntityType = blockEntityType;
    }

    protected AbstractBlockEntityDeviceProvider() {
        this.blockEntityType = null;
    }

    ///////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    @Override
    public final Invalidatable<Device> getDevice(final BlockDeviceQuery query) {
        final BlockEntity blockEntity = query.getLevel().getBlockEntity(query.getQueryPosition());
        if (blockEntity == null) {
            return Invalidatable.empty();
        }

        if (blockEntityType != null && blockEntity.getType() != blockEntityType) {
            return Invalidatable.empty();
        }

        return getBlockDevice(query, (T) blockEntity);
    }

    ///////////////////////////////////////////////////////////////////

    protected abstract Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final T blockEntity);
}
