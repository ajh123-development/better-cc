/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.api.bus.device.vm.context;

import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMInitializingEvent;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMResumedRunningEvent;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMSynchronizeEvent;

/**
 * Allows registering for VM lifecycle events.
 *
 * @see VMInitializingEvent
 * @see VMSynchronizeEvent
 * @see VMResumedRunningEvent
 */
public interface VMLifecycleEventBus {
    /**
     * Registers the specified object as a subscriber for events.
     *
     * @param subscriber the object to subscribe methods of.
     */
    void register(Object subscriber);
}
