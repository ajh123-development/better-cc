package net.ddns.minersonline.BetterCC.blocks;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkAttachable;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkDevice;
import net.ddns.minersonline.BetterCC.api.network.transfer.NetworkCable;
import net.ddns.minersonline.BetterCC.bases.CableBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class SerialCable extends CableBase implements NetworkCable {

	public SerialCable(Properties properties) {
		super(properties);
	}


	@Override
	public void addPacket(NetworkPacket packet, Direction from, BlockState me, Level level, BlockPos pos) {
		BlockState north = level.getBlockState(pos.north());
		BlockState east = level.getBlockState(pos.east());
		BlockState south = level.getBlockState(pos.south());
		BlockState west = level.getBlockState(pos.west());
		BlockState up = level.getBlockState(pos.above());
		BlockState down = level.getBlockState(pos.below());

		if (from != Direction.NORTH) {
			if (north.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.NORTH.getOpposite(), north, level, pos.north());
			}
			BlockEntity entity = level.getBlockEntity(pos.north());
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
		if (from != Direction.EAST) {
			if (east.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.EAST.getOpposite(), east, level, pos.east());
			}
			BlockEntity entity = level.getBlockEntity(pos.east());
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
		if (from != Direction.SOUTH) {
			if (south.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.SOUTH.getOpposite(), south, level, pos.south());
			}
			BlockEntity entity = level.getBlockEntity(pos.south());
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
		if (from != Direction.WEST) {
			if (west.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.WEST.getOpposite(), west, level, pos.west());
			}
			BlockEntity entity = level.getBlockEntity(pos.west());
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
		if (from != Direction.UP) {
			if (up.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.UP.getOpposite(), up, level, pos.above());
			}
			BlockEntity entity = level.getBlockEntity(pos.above());
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
		if (from != Direction.DOWN) {
			if (down.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.DOWN.getOpposite(), down, level, pos.below());
			}
		}
		BlockEntity entity = level.getBlockEntity(pos.below());
		if (entity instanceof NetworkDevice device) {
			for (NetworkAttachable attachable : device.getAttachable()) {
				attachable.addPacket(packet);
			}
		}
	}
}
