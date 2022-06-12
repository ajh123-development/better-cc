/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.item.SoundCardItemDevice;
import net.ddns.minersonline.BetterCC.common.item.Items;
import net.ddns.minersonline.BetterCC.common.util.LocationSupplierUtils;

import java.util.Optional;

public final class SoundCardItemDeviceProvider extends AbstractItemDeviceProvider {
    public SoundCardItemDeviceProvider() {
        super(Items.SOUND_CARD);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        return Optional.of(new SoundCardItemDevice(query.getItemStack(), LocationSupplierUtils.of(query)));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        return Config.soundCardEnergyPerTick;
    }
}
