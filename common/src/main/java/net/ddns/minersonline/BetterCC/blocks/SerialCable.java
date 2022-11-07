package net.ddns.minersonline.BetterCC.blocks;

import net.ddns.minersonline.BetterCC.setup.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SerialCable extends CrossCollisionBlock {
	private final VoxelShape[] occlusionByIndex;
	private final VoxelShape[] originalCollisionShapeByIndex;
	private final VoxelShape[] originalShapeByIndex;
	public static final BooleanProperty VERTICAL = BooleanProperty.create("vertical");

	static float vertical_shape_f = 2.0F;
	static float vertical_shape_g = 2.0F;
	static float vertical_shape_h = 16.0F;
	static float vertical_shape_i = 16.0F;
	static float vertical_shape_j = 16.0F;

	public SerialCable(Properties properties) {
		super(2.0F, 2.0F, 3.0F, 3.0F, 3.0F, properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(VERTICAL, false)
				.setValue(WATERLOGGED, false)
		);
		this.occlusionByIndex = this.makeShapes(2.0F, 1.0F, 3.0F, 3.0F, 3.0F);
		this.originalCollisionShapeByIndex = collisionShapeByIndex.clone();
		this.originalShapeByIndex = shapeByIndex.clone();
	}

	public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return this.occlusionByIndex[this.getAABBIndex(blockState)];
	}

	public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.getShape(blockState, blockGetter, blockPos, collisionContext);
	}

	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}

	public boolean connectsTo(BlockState blockState) {
		return blockState.is(ModTags.SERIAL_CABLE_CONNECTABLE);
	}

	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockGetter blockGetter = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();
		FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
		BlockPos blockPos2 = blockPos.north();
		BlockPos blockPos3 = blockPos.east();
		BlockPos blockPos4 = blockPos.south();
		BlockPos blockPos5 = blockPos.west();
		BlockPos blockPos6 = blockPos.above();
		BlockState blockState = blockGetter.getBlockState(blockPos2);
		BlockState blockState2 = blockGetter.getBlockState(blockPos3);
		BlockState blockState3 = blockGetter.getBlockState(blockPos4);
		BlockState blockState4 = blockGetter.getBlockState(blockPos5);
		BlockState blockState5 = blockGetter.getBlockState(blockPos6);
		return super.getStateForPlacement(blockPlaceContext)
				.setValue(NORTH, this.connectsTo(blockState))
				.setValue(EAST, this.connectsTo(blockState2))
				.setValue(SOUTH, this.connectsTo(blockState3))
				.setValue(WEST, this.connectsTo(blockState4))
				.setValue(VERTICAL, this.connectsTo(blockState5))
				.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return updateConnection(blockState, direction, blockState2);
	}

	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		VoxelShape[] shape = this.originalShapeByIndex;
		if (blockState.getValue(VERTICAL)) {
			shape = this.makeShapes(vertical_shape_f, vertical_shape_g, vertical_shape_h, 0.0F, vertical_shape_i);
		}
		return shape[this.getAABBIndex(blockState)];
	}

	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		VoxelShape[] shape = this.originalCollisionShapeByIndex;
		if (blockState.getValue(VERTICAL)) {
			shape = this.makeShapes(vertical_shape_f, vertical_shape_g, vertical_shape_h, 0.0F, vertical_shape_j);
		}

		return shape[this.getAABBIndex(blockState)];
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, VERTICAL, WATERLOGGED);
	}

	public BlockState updateConnection(BlockState state, Direction direction, BlockState blockState2){
		if (direction == Direction.NORTH) {
			state = state.setValue(NORTH, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(NORTH, true);
			}
		}
		if (direction == Direction.EAST) {
			state = state.setValue(EAST, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(EAST, true);
			}
		}
		if (direction == Direction.SOUTH) {
			state = state.setValue(SOUTH, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(SOUTH, true);
			}
		}
		if (direction == Direction.WEST) {
			state = state.setValue(WEST, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(WEST, true);
			}
		}
		if (direction == Direction.UP) {
			state = state.setValue(VERTICAL, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(VERTICAL, true);
			}
		}
		return state;
	}
}
