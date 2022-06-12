/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.blockentity;

import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class CreativeEnergyBlockEntity extends ModBlockEntity implements TickableBlockEntity {
    private final Direction[] SIDES = Direction.values();

    ///////////////////////////////////////////////////////////////////

    public CreativeEnergyBlockEntity(final BlockPos pos, final BlockState state) {
        super(BlockEntities.CREATIVE_ENERGY.get(), pos, state);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void serverTick() {
        assert level != null;

        for (final Direction side : SIDES) {
            final BlockPos neighborPos = getBlockPos().relative(side);
            final ChunkPos neighborChunkPos = new ChunkPos(neighborPos);
            if (level.hasChunk(neighborChunkPos.x, neighborChunkPos.z)) {
                final BlockEntity blockEntity = level.getBlockEntity(neighborPos);
                if (blockEntity != null) {
                    blockEntity.getCapability(Capabilities.energyStorage(), side.getOpposite()).ifPresent(energy ->
                        energy.receiveEnergy(Integer.MAX_VALUE, false));
                }
            }
        }
    }
}
