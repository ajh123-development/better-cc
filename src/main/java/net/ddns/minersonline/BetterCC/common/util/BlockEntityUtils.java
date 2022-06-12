/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

@SuppressWarnings("LocalCanBeFinal")
public final class BlockEntityUtils {
    @SuppressWarnings("unchecked")
    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> haveType, BlockEntityType<E> wantType, BlockEntityTicker<? super E> ticker) {
        return wantType == haveType ? (BlockEntityTicker<A>) ticker : null;
    }
}
