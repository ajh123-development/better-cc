/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm.context;

import java.util.BitSet;

public interface InterruptManager {
    int getInterruptCount();

    void releaseInterrupts(BitSet interrupts);
}
