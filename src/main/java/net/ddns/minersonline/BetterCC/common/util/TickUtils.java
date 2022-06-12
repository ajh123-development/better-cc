/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import net.ddns.minersonline.BetterCC.common.Constants;

import java.time.Duration;

public final class TickUtils {
    public static int toTicks(final Duration duration) {
        return (int) (duration.getSeconds() * Constants.SECONDS_TO_TICKS);
    }
}
