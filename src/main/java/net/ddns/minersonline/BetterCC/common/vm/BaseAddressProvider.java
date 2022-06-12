/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm;

import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDevice;

import java.util.OptionalLong;

public interface BaseAddressProvider {
    OptionalLong getBaseAddress(final VMDevice wrapper);
}
