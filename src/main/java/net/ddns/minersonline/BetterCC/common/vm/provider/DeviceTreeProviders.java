/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.provider;

import net.ddns.minersonline.BetterCC.common.vm.device.SimpleFramebufferDevice;
import li.cil.sedna.devicetree.DeviceTreeRegistry;

public final class DeviceTreeProviders {
    public static void initialize() {
        DeviceTreeRegistry.putProvider(SimpleFramebufferDevice.class, new SimpleFramebufferDeviceProvider());
    }
}
