package net.ddns.minersonline.better_cc.interfaces;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IWrenchMe {
    boolean onWrench(Level world, BlockPos pos, BlockState state, ServerPlayer player);
}