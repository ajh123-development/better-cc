package net.ddns.minersonline.BetterCC.blocks;

import net.ddns.minersonline.BetterCC.api.network.device.NetworkAttachable;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkDevice;
import net.ddns.minersonline.BetterCC.setup.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CraftingMachineEntity extends BlockEntity implements NetworkDevice {
	private final List<NetworkAttachable> attachable = new ArrayList<>();

	public CraftingMachineEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlocksEntities.CRAFTING_MACHINE.get(), blockPos, blockState);
		attachable.add(new NetworkAttachable(Direction.NORTH));
		attachable.add(new NetworkAttachable(Direction.EAST));
		attachable.add(new NetworkAttachable(Direction.SOUTH));
		attachable.add(new NetworkAttachable(Direction.WEST));
		attachable.add(new NetworkAttachable(Direction.UP));
		attachable.add(new NetworkAttachable(Direction.DOWN));
	}

	public void tick(BlockState blockState, Level level) {
		Block block = blockState.getBlock();
		if (block instanceof CraftingMachine randomBlock) {
			randomBlock.updateSignalStrength(blockState, level, getBlockPos());
		}

		BlockPos myPos = getBlockPos();
		BlockEntity north = level.getBlockEntity(myPos.north());
		BlockEntity east = level.getBlockEntity(myPos.east());
		BlockEntity south = level.getBlockEntity(myPos.south());
		BlockEntity west = level.getBlockEntity(myPos.west());
		BlockEntity up = level.getBlockEntity(myPos.above());
		BlockEntity down = level.getBlockEntity(myPos.below());
	}

	@Override
	public List<NetworkAttachable> getAttachable() {
		return attachable;
	}
}