package net.ddns.minersonline.better_cc.blocks.punchcardreader;

import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.luaj.vm2.*;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;

import javax.annotation.Nullable;


public class PunchCardReaderBlock extends HorizontalBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.better-cc.punch_card_reader");
    PlayerEntity lastPlayer;

    public PunchCardReaderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState().setValue(FACING, itemUseContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, POWER);
    }

    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    public int getSignal(BlockState state, IBlockReader blockReader, BlockPos pos, Direction direction) {
        return state.getValue(POWER);
    }
    public static void updateSignalStrength(BlockState state, World world, BlockPos pos) {
        if (world.dimensionType().hasSkyLight()) {
            int i = 0;

            i = MathHelper.clamp(i, 0, 15);
            if (state.getValue(POWER) != i) {
                world.setBlock(pos, state.setValue(POWER, Integer.valueOf(i)), 3);
            }

        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        updateSignalStrength(state, world, pos);
        this.interactWith(world, pos, player);
        this.lastPlayer = player;
        return ActionResultType.CONSUME;
    }

    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof PunchCardReaderTileEntity && player instanceof ServerPlayerEntity) {
            PunchCardReaderTileEntity te = (PunchCardReaderTileEntity) tileEntity;
            NetworkHooks.openGui((ServerPlayerEntity) player, te, te::encodeExtraData);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PunchCardReaderTileEntity();
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

    public void tick(World world, BlockState state){

    }

}
