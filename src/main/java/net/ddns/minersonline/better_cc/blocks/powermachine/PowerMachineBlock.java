package net.ddns.minersonline.better_cc.blocks.powermachine;

import net.ddns.minersonline.better_cc.blocks.machinebase.UpgradeableMachineBlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class PowerMachineBlock extends UpgradeableMachineBlockBase {

    public PowerMachineBlock(Properties properties) {
        super(properties);
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

    @Override
    public void tick(World world, BlockState state){
        super.tick(world, state);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PowerMachineTileEntity();
    }

}
