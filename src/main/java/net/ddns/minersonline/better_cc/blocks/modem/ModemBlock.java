package net.ddns.minersonline.better_cc.blocks.modem;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.interfaces.I_dsl_message;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ModemBlock extends HorizontalBlock implements I_dsl_message {
    Properties my_properties;
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public ModemBlock(Properties properties) {
        super(properties);
        my_properties = properties;
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState().setValue(FACING, itemUseContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING);
    }

    public static void update(ModemBlock me, BlockState state, World world, BlockPos pos) {

    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.CONSUME;
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ModemTileEntity();
    }

    @Override
    public String[] receive() {
        return better_cc.MESSAGES.get(better_cc.MESSAGES.size()-1);
    }

    @Override
    public String send(String destIP, String message) {
        String[] data = new String[2];
        data[0] = destIP;
        data[1] = message;
        better_cc.MESSAGES.add(data);
        return "Success";
    }
}
