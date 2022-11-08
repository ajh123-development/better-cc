package net.ddns.minersonline.BetterCC.bases;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkAttachable;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkDevice;
import net.ddns.minersonline.BetterCC.api.network.transfer.NetworkCable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class NetworkBlockEntity extends BlockEntity implements NetworkDevice {
	private final List<NetworkAttachable> attachable = new ArrayList<>();
	private final List<NetworkPacket> packets = new ArrayList<>();

	public NetworkBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
		attachable.add(new NetworkAttachable(Direction.NORTH));
		attachable.add(new NetworkAttachable(Direction.EAST));
		attachable.add(new NetworkAttachable(Direction.SOUTH));
		attachable.add(new NetworkAttachable(Direction.WEST));
		attachable.add(new NetworkAttachable(Direction.UP));
		attachable.add(new NetworkAttachable(Direction.DOWN));
	}

	public void tick(BlockState blockState, Level level) {
		BlockPos myPos = getBlockPos();
		BlockState north = level.getBlockState(myPos.north());
		BlockState east = level.getBlockState(myPos.east());
		BlockState south = level.getBlockState(myPos.south());
		BlockState west = level.getBlockState(myPos.west());
		BlockState up = level.getBlockState(myPos.above());
		BlockState down = level.getBlockState(myPos.below());

		if (packets.size() > 0) {
			if (north.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.NORTH.getOpposite(), north, level, myPos.north());
			}
			if (east.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.EAST.getOpposite(), east, level, myPos.east());
			}
			if (south.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.SOUTH.getOpposite(), south, level, myPos.south());
			}
			if (west.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.WEST.getOpposite(), west, level, myPos.west());
			}
			if (up.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.UP.getOpposite(), up, level, myPos.above());
			}
			if (down.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packets.remove(0), Direction.DOWN.getOpposite(), down, level, myPos.below());
			}
		}
	}

	@Override
	public final List<NetworkAttachable> getAttachable() {
		return attachable;
	}

	protected final List<NetworkPacket> getReceivedPackets() {
		List<NetworkPacket> packets = new ArrayList<>();
		for (NetworkAttachable attachable : getAttachable()) {
			for (int i = 0; i < attachable.getPacketCount(); i ++) {
				packets.add(attachable.getPacket());
			}
		}
		return packets;
	}
}