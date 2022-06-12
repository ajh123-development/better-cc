/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.item.BlockOperationsModuleDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.ddns.minersonline.BetterCC.common.item.Items;

import java.util.Optional;

public final class BlockOperationsModuleDeviceProvider extends AbstractItemDeviceProvider {
    public BlockOperationsModuleDeviceProvider() {
        super(Items.BLOCK_OPERATIONS_MODULE);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        return query.getContainerEntity().flatMap(entity ->
            entity.getCapability(Capabilities.robot()).map(robot ->
                new BlockOperationsModuleDevice(query.getItemStack(), entity, robot)));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        return Config.blockOperationsModuleEnergyPerTick;
    }
}
