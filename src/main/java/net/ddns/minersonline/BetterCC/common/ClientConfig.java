/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm;

import net.ddns.minersonline.BetterCC.common.ConfigManager;
import net.ddns.minersonline.BetterCC.common.ConfigManager.Path;
import net.ddns.minersonline.BetterCC.common.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.fml.config.ModConfig;

import java.util.UUID;

@ConfigManager.Type(ModConfig.Type.CLIENT)
public final class ClientConfig {
    @Path("rendering.reload_ray_tracer_each_tick") public static boolean reloadRayTracerEachTick = false;
    @Path("rendering.enabledLeftClick") public static boolean enabledLeftClick  = true;
}
