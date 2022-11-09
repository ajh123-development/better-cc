package net.ddns.minersonline.BetterCC.blocks.modem;

import net.ddns.minersonline.BetterCC.bases.NetworkBlockEntity;
import net.ddns.minersonline.BetterCC.setup.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class SerialModemEntity extends NetworkBlockEntity {
	public SerialModemEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlocksEntities.SERIAL_MODEM.get(), blockPos, blockState);
	}

	public void tick(BlockState blockState, Level level) {
		super.tick(blockState, level);

		Block block = blockState.getBlock();
		if (block instanceof SerialModem randomBlock) {
			randomBlock.updateSignalStrength(blockState, level, getBlockPos());
		}
	}
}