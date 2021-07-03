package net.ddns.minersonline.better_cc.blocks.randomizer;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;


public class RandomBlock extends HorizontalBlock implements IWrenchMe {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;


    public RandomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, Integer.valueOf(0)).setValue(ENABLED, true));
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState().setValue(FACING, itemUseContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, POWER, ENABLED);
    }

    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    public int getSignal(BlockState state, IBlockReader blockReader, BlockPos pos, Direction direction) {
        return state.getValue(POWER);
    }



    public static void updateSignalStrength(RandomBlock me, BlockState state, World world, BlockPos pos) {
        if (state.getValue(ENABLED) == true) {
            Random random = new Random();
            int i = random.nextInt(15);

            i = MathHelper.clamp(i, 0, 15);
            world.setBlock(pos, state.setValue(POWER, Integer.valueOf(i)), 3);
        }
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {

    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        world.setBlock(pos, state.setValue(POWER, 0).setValue(ENABLED, !state.getValue(ENABLED)), 3);
        return ActionResultType.CONSUME;
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new RandomTileEntity();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropContents(world, pos, (IInventory) tileEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean onWrench(World world, BlockState state, PlayerEntity player) {
        player.swing(player.getUsedItemHand());
        better_cc.LOGGER.info("HELLO wrench");
        return true;
    }
}
