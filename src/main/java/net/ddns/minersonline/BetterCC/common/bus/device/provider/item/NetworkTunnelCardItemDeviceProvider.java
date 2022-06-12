/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.vm.item.NetworkTunnelDevice;
import net.ddns.minersonline.BetterCC.common.item.Items;

import java.util.Optional;

public final class NetworkTunnelCardItemDeviceProvider extends AbstractItemDeviceProvider {
    public NetworkTunnelCardItemDeviceProvider() {
        super(Items.NETWORK_TUNNEL_CARD);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        return Optional.of(new NetworkTunnelDevice(query.getItemStack()));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        return Config.networkTunnelEnergyPerTick;
    }
}
