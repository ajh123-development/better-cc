/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.util;

import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceProvider;

import javax.annotation.Nullable;

public final class BlockDeviceInfo extends AbstractDeviceInfo<BlockDeviceProvider, Device> {
    public BlockDeviceInfo(@Nullable final BlockDeviceProvider blockDeviceProvider, final Device device) {
        super(blockDeviceProvider, device);
    }
}
