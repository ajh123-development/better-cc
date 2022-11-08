package net.ddns.minersonline.BetterCC.api.network.transfer;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface NetworkCable {
	void addPacket(NetworkPacket packet, Direction from, BlockState me, Level level, BlockPos pos);
}
