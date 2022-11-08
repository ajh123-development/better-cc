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


public class SerialCable extends CableBase implements NetworkCable {

	public SerialCable(Properties properties) {
		super(properties);
	}

	@Override
	public void addPacket(NetworkPacket packet, Direction from, BlockState me, Level level, BlockPos pos) {
		transmit(packet, pos.north(), from.getOpposite(), Direction.NORTH.getOpposite(), level);
		transmit(packet, pos.east(), from.getOpposite(), Direction.EAST.getOpposite(), level);
		transmit(packet, pos.south(), from.getOpposite(), Direction.SOUTH.getOpposite(), level);
		transmit(packet, pos.west(), from.getOpposite(), Direction.EAST.getOpposite(), level);
		transmit(packet, pos.above(), from.getOpposite(), Direction.UP.getOpposite(), level);
		transmit(packet, pos.below(), from.getOpposite(), Direction.DOWN.getOpposite(), level);
	}

	private void transmit(NetworkPacket packet, BlockPos dest, Direction from, Direction to, Level level) {
		BlockState block = level.getBlockState(dest);
		if (from == to) {
			System.out.print("Transmit: ");
			System.out.print(from);
			System.out.print(" To: ");
			System.out.println(to.getOpposite());
			if (block.getBlock() instanceof NetworkCable cable) {
				if (Direction.NORTH != from) {
					cable.addPacket(packet, Direction.NORTH, block, level, dest);
				}
				if (Direction.EAST != from) {
					cable.addPacket(packet, Direction.EAST, block, level, dest);
				}
				if (Direction.SOUTH != from) {
					cable.addPacket(packet, Direction.SOUTH, block, level, dest);
				}
				if (Direction.WEST != from) {
					cable.addPacket(packet, Direction.WEST, block, level, dest);
				}
				if (Direction.UP != from) {
					cable.addPacket(packet, Direction.UP, block, level, dest);
				}
				if (Direction.DOWN != from) {
					cable.addPacket(packet, Direction.DOWN, block, level, dest);
				}
			}
			BlockEntity entity = level.getBlockEntity(dest);
			if (entity instanceof NetworkDevice device) {
				for (NetworkAttachable attachable : device.getAttachable()) {
					attachable.addPacket(packet);
				}
			}
		}
	}
}
