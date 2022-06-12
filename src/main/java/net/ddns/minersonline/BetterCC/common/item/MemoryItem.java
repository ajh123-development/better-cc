/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.item;

import net.ddns.minersonline.BetterCC.api.API;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public final class MemoryItem extends AbstractStorageItem {
    @Nullable private String descriptionId;

    ///////////////////////////////////////////////////////////////////

    public MemoryItem(final int defaultCapacity) {
        super(defaultCapacity);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected String getOrCreateDescriptionId() {
        if (descriptionId == null) {
            descriptionId = Util.makeDescriptionId("item", new ResourceLocation(API.MOD_ID, "memory"));
        }
        return descriptionId;
    }
}
