/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.context;

public interface VMContextManagerCollection {
    InterruptManager getInterruptManager();

    MemoryRangeManager getMemoryRangeManager();

    EventManager getEventManager();
}
