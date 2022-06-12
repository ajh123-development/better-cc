/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.context.global;

import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.MemoryAllocator;
import net.ddns.minersonline.BetterCC.common.vm.Allocator;

import java.util.ArrayList;
import java.util.UUID;

final class GlobalMemoryAllocator implements MemoryAllocator {
    private final ArrayList<UUID> claimedMemory = new ArrayList<>();

    ///////////////////////////////////////////////////////////////////

    public void invalidate() {
        for (final UUID handle : claimedMemory) {
            Allocator.freeMemory(handle);
        }

        claimedMemory.clear();
    }

    @Override
    public boolean claimMemory(final int size) {
        final UUID handle = Allocator.createHandle();
        if (!Allocator.claimMemory(handle, size)) {
            return false;
        }

        claimedMemory.add(handle);
        return true;
    }
}
