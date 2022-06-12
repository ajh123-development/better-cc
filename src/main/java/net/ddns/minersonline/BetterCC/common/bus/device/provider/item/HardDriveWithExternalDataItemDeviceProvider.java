/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.data.BlockDeviceData;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.Constants;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.vm.item.HardDriveDeviceWithInitialData;
import net.ddns.minersonline.BetterCC.common.item.HardDriveWithExternalDataItem;
import net.ddns.minersonline.BetterCC.common.util.LocationSupplierUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public final class HardDriveWithExternalDataItemDeviceProvider extends AbstractItemDeviceProvider {
    public HardDriveWithExternalDataItemDeviceProvider() {
        super(HardDriveWithExternalDataItem.class);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void unmount(@Nullable final ItemDeviceQuery query, final CompoundTag tag) {
        super.unmount(query, tag);
        HardDriveDeviceWithInitialData.unmount(tag);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        final ItemStack stack = query.getItemStack();
        final HardDriveWithExternalDataItem item = (HardDriveWithExternalDataItem) stack.getItem();
        final BlockDeviceData data = item.getData(stack);
        if (data == null) {
            return Optional.empty();
        }

        return Optional.of(new HardDriveDeviceWithInitialData(stack, data.getBlockDevice(), false, LocationSupplierUtils.of(query)));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        final ItemStack stack = query.getItemStack();
        final HardDriveWithExternalDataItem item = (HardDriveWithExternalDataItem) stack.getItem();
        final BlockDeviceData data = item.getData(stack);
        if (data == null) {
            return 0;
        }

        final long capacity = Math.max(data.getBlockDevice().getCapacity(), 0);
        return Math.max(1, (int) Math.round(capacity * Config.hardDriveEnergyPerMegabytePerTick / Constants.MEGABYTE));
    }
}
