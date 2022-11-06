package net.ddns.minersonline.BetterCC.blocks;

import net.ddns.minersonline.BetterCC.setup.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CraftingMachineEntity extends BlockEntity {
	int ticks = 0;
	public CraftingMachineEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlocksEntities.CRAFTING_MACHINE.get(), blockPos, blockState);
	}

	public void tick(BlockState blockState, Level level) {
		Block block = blockState.getBlock();
		if (block instanceof CraftingMachine randomBlock) {
			randomBlock.updateSignalStrength(blockState, level, getBlockPos());
		}
		ticks++;
	}
}