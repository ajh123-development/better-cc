/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.ext;

import com.mojang.blaze3d.pipeline.RenderTarget;

import javax.annotation.Nullable;

public interface MinecraftExt {
    void setMainRenderTargetOverride(@Nullable RenderTarget renderTarget);
}
