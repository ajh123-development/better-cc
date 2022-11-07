package net.ddns.minersonline.BetterCC.blocks;

import net.ddns.minersonline.BetterCC.api.network.transfer.NetworkCable;
import net.ddns.minersonline.BetterCC.cables.CableBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;


public class SerialCable extends CableBase implements NetworkCable {

	public SerialCable(Properties properties) {
		super(properties);
	}


	@Override
	@ParametersAreNonnullByDefault
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {


	}
}
