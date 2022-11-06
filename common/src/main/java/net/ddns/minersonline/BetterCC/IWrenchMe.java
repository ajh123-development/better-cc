package net.ddns.minersonline.BetterCC;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IWrenchMe {
	boolean onWrench(Level level, BlockPos blockPos, BlockState blockState, ServerPlayer player);
}
