package net.ddns.minersonline.BetterCC.blocks.modem;

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

import java.util.ArrayList;
import java.util.List;


public class SerialCable extends CableBase implements NetworkCable {
	private final List<NetworkPacket> used = new ArrayList<>();

	public SerialCable(Properties properties) {
		super(properties);
	}

	@Override
	public void addPacket(NetworkPacket packet, Direction from, BlockState me, Level level, BlockPos pos) {
		if (packetNotUsed(packet)) {
			transmit(packet, pos.north(), from, level, Direction.NORTH);
			transmit(packet, pos.east(), from, level, Direction.EAST);
			transmit(packet, pos.south(), from, level, Direction.SOUTH);
			transmit(packet, pos.west(), from, level, Direction.WEST);
			transmit(packet, pos.above(), from, level, Direction.UP);
			transmit(packet, pos.below(), from, level, Direction.DOWN);
			used.add(packet);
		}
	}

	private void transmit(NetworkPacket packet, BlockPos dest, Direction from, Level level, Direction destDir) {
		BlockState block = level.getBlockState(dest);
		if (block.getBlock() instanceof NetworkCable cable) {
			sendTo(cable, packet, block, level, from.getOpposite(), dest, destDir);
		}
		BlockEntity entity = level.getBlockEntity(dest);
		if (entity instanceof NetworkDevice device) {
			for (NetworkAttachable attachable : device.getAttachable()) {
				attachable.addPacket(packet);
			}
		}
	}

	private void sendTo(NetworkCable cable, NetworkPacket packet, BlockState block, Level level, Direction from, BlockPos dest, Direction destDir) {
		if (from != destDir) {
			if (destDir == Direction.NORTH) {
				cable.addPacket(packet, Direction.NORTH, block, level, dest);
			}
			if (destDir == Direction.EAST) {
				cable.addPacket(packet, Direction.EAST, block, level, dest);
			}
			if (destDir ==  Direction.SOUTH) {
				cable.addPacket(packet, Direction.SOUTH, block, level, dest);
			}
			if (destDir == Direction.WEST) {
				cable.addPacket(packet, Direction.WEST, block, level, dest);
			}
			if (destDir == Direction.UP) {
				cable.addPacket(packet, Direction.UP, block, level, dest);
			}
			if (destDir == Direction.DOWN) {
				cable.addPacket(packet, Direction.DOWN, block, level, dest);
			}
		}
	}

	private boolean packetNotUsed(NetworkPacket packet) {
		for (NetworkPacket pct : used) {
			if (pct.getMyId() == packet.getMyId()) {
				return false;
			}
		}
		return true;
	}
}
