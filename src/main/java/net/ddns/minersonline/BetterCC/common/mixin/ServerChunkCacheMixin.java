/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.mixin;

import net.ddns.minersonline.BetterCC.common.util.ChunkUtils;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.chunk.ChunkSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin extends ChunkSource {
    @Inject(method = "save", at = @At("HEAD"))
    private void applyLazyUnsavedChunks(final CallbackInfo ci) {
        ChunkUtils.applyChunkLazyUnsaved();
    }
}
