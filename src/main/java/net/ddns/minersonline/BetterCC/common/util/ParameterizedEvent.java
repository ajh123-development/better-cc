/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import java.util.HashSet;
import java.util.function.Consumer;

public final class ParameterizedEvent<TEventParameter> extends HashSet<Consumer<TEventParameter>> implements Consumer<TEventParameter> {
    @Override
    public void accept(final TEventParameter event) {
        for (final Consumer<TEventParameter> listener : this) {
            listener.accept(event);
        }
    }
}
