/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.util;

import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.VMContext;
import li.cil.sedna.api.device.MemoryMappedDevice;

import java.util.OptionalLong;

public final class OptionalAddress {
    private Long value;

    public boolean isPresent() {
        return value != null;
    }

    public long getAsLong() {
        return value;
    }

    public void set(final long value) {
        this.value = value;
    }

    public void clear() {
        this.value = null;
    }

    public boolean claim(final VMContext context, final MemoryMappedDevice device) {
        final OptionalLong claimedAddress;
        if (value != null && context.getMemoryRangeAllocator().claimMemoryRange(value, device)) {
            claimedAddress = OptionalLong.of(value);
        } else {
            claimedAddress = context.getMemoryRangeAllocator().claimMemoryRange(device);
        }

        if (claimedAddress.isPresent()) {
            value = claimedAddress.getAsLong();
            return true;
        } else {
            return false;
        }
    }
}
