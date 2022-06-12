/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.context;

import li.cil.sedna.api.device.MemoryMappedDevice;

import java.util.OptionalLong;

public interface MemoryRangeManager {
    OptionalLong findMemoryRange(MemoryMappedDevice device, long start);

    void releaseMemoryRange(MemoryMappedDevice device);
}
