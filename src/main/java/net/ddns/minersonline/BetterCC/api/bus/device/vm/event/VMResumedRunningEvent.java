/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.api.bus.device.vm.event;

import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.VMContext;

/**
 * Fired when the VM resumed running, either when first starting up, when resuming after
 * being loaded, or after a {@link VMSynchronizeEvent}.
 * <p>
 * May be used to ensure asynchronous work started in {@link VMDevice#mount(VMContext)}
 * completes before regular execution of the virtual machine begins.
 * <p>
 * May also be used in combination with {@link VMSynchronizeEvent}, to re-enable external
 * interactions after VM state is guaranteed to be safe to modify again.
 */
public final class VMResumedRunningEvent { }
