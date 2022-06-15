package net.ddns.minersonline.BetterCC.common.block;

import net.ddns.minersonline.BetterCC.common.blockentity.BlockEntities;
import net.ddns.minersonline.BetterCC.common.blockentity.NetworkHubBlockEntity;
import net.ddns.minersonline.BetterCC.common.blockentity.NetworkSwitchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public final class NetworkSwitchBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public NetworkSwitchBlock() {
        super(Properties
            .of(Material.METAL)
            .sound(SoundType.METAL)
            .strength(1.5f, 6.0f));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return super.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public void neighborChanged(final BlockState state, final Level level, final BlockPos pos, final Block changedBlock, final BlockPos changedBlockPos, final boolean isMoving) {
//        final BlockEntity blockEntity = level.getBlockEntity(pos);
//        if (blockEntity instanceof final NetworkSwitchBlockEntity networkHub) {
//            networkHub.
//        }
//    }

    ///////////////////////////////////////////////////////////////////
    // EntityBlock

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos pos, final BlockState state) {
        return BlockEntities.NETWORK_SWITCH.get().create(pos, state);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
