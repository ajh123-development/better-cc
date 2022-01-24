package net.ddns.minersonline.better_cc.blocks;

import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.ddns.minersonline.better_cc.setup.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RandomBlock extends HorizontalDirectionalBlock implements IWrenchMe, EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public RandomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWER, 0)
                .setValue(ENABLED, true));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER, ENABLED);
    }

    @Override
    public boolean onWrench(Level world, BlockPos pos, BlockState state, Player player) {
        world.setBlock(pos, state.setValue(POWER, 0).setValue(ENABLED, !state.getValue(ENABLED)), 3);
        return true;
    }

    @Override
    @Deprecated
    public boolean isSignalSource(BlockState blockState) {
        return true;
    }

    @Override
    @Deprecated
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getValue(POWER);
    }

    public void updateSignalStrength(BlockState state, Level world, BlockPos pos) {
        if (state.getValue(ENABLED)) {
            Random random = new Random();
            int i = random.nextInt(15);
            world.setBlockAndUpdate(pos, state.setValue(POWER, i));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RandomEntity(ModBlocksEntities.RANDOM.get().getType(), blockPos, blockState);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        BlockState blockstate = serverLevel.getBlockState(blockPos);
        Block block = blockstate.getBlock();
        if (block instanceof RandomBlock randomBlock) {
            randomBlock.updateSignalStrength(blockstate, serverLevel, blockPos);
        }
    }

}
