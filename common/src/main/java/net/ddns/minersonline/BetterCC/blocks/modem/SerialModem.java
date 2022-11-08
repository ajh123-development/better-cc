package net.ddns.minersonline.BetterCC.blocks.modem;

import net.ddns.minersonline.BetterCC.IWrenchMe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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

import javax.annotation.Nullable;

public class SerialModem extends HorizontalDirectionalBlock implements IWrenchMe, EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

	public SerialModem(Properties properties) {
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
			player.sendSystemMessage(Component.literal("enabled:§a"+true));
		} else {
			player.sendSystemMessage(Component.literal("enabled:§c"+false));
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
			world.setBlockAndUpdate(pos, state.setValue(POWER, 15));
		} else {
			world.setBlockAndUpdate(pos, state.setValue(POWER, 0));
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new SerialModemEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null
				: (level0, pos, state0, blockEntity) -> ((SerialModemEntity) blockEntity).tick(blockState, level);
	}
}
