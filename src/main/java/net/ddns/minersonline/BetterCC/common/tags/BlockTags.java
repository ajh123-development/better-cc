/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.tags;

import net.ddns.minersonline.BetterCC.api.API;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class BlockTags {
    public static final TagKey<Block> DEVICES = tag("devices");
    public static final TagKey<Block> CABLES = tag("cables");
    public static final TagKey<Block> WRENCH_BREAKABLE = tag("wrench_breakable");

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
    }

    ///////////////////////////////////////////////////////////////////

    private static TagKey<Block> tag(final String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(API.MOD_ID, name));
    }
}
