package net.ddns.minersonline.better_cc.blocks;

import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    public boolean onWrench(Level world, BlockPos pos, BlockState state, ServerPlayer player) {
        world.setBlockAndUpdate(pos, state.setValue(POWER, 0).setValue(ENABLED, !state.getValue(ENABLED)));
        state = world.getBlockState(pos);
        if(state.getValue(ENABLED)) {
            player.sendMessage(new TextComponent("enabled:§a"+true), ChatType.GAME_INFO, player.getUUID());
        } else {
            player.sendMessage(new TextComponent("enabled:§c"+false), ChatType.GAME_INFO, player.getUUID());
        }
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
        return new RandomEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((RandomEntity) blockEntity).tick(blockState, level);
    }
}
