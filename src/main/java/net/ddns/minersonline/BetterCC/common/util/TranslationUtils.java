/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import net.ddns.minersonline.BetterCC.api.API;
import net.minecraft.network.chat.TranslatableComponent;

public final class TranslationUtils {
    public static String key(final String pattern) {
        return pattern.replaceAll("\\{mod}", API.MOD_ID);
    }

    public static TranslatableComponent text(final String pattern) {
        return new TranslatableComponent(key(pattern));
    }

    private TranslationUtils() {
    }
}
