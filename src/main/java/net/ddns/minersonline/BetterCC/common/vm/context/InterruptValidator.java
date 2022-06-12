/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.context;

public interface InterruptValidator {
    boolean isMaskValid(int mask);

    int getMaskedInterrupts(int interrupts);
}
