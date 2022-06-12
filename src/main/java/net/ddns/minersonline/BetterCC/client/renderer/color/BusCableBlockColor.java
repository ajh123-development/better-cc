/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.client.renderer.color;

import net.ddns.minersonline.BetterCC.common.blockentity.BusCableBlockEntity;
import net.ddns.minersonline.BetterCC.common.util.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public final class BusCableBlockColor implements BlockColor {
    @Override
    public int getColor(final BlockState state, @Nullable final BlockAndTintGetter level, @Nullable final BlockPos pos, final int tintIndex) {
        if (level == null || pos == null) {
            return 0;
        }

        final BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof final BusCableBlockEntity busCable) {
            final BlockState facade = ItemStackUtils.getBlockState(busCable.getFacade());
            if (facade != null) {
                return Minecraft.getInstance().getBlockColors().getColor(facade, level, pos, tintIndex);
            }
        }

        return 0;
    }
}
