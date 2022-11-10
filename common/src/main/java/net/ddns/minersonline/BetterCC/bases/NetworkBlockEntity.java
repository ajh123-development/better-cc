package net.ddns.minersonline.BetterCC.bases;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkInterface;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkDevice;
import net.ddns.minersonline.BetterCC.api.network.transfer.NetworkCable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class NetworkBlockEntity extends BlockEntity implements NetworkDevice {
	private final List<NetworkInterface> attachable = new ArrayList<>();
	public final List<NetworkPacket> packets = new ArrayList<>();

	public NetworkBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
		attachable.add(new NetworkInterface(UUID.randomUUID()));
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
			NetworkPacket packet = packets.remove(0);
			if (north.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.NORTH, north, level, myPos.north());
			}
			if (east.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.EAST, east, level, myPos.east());
			}
			if (south.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.SOUTH, south, level, myPos.south());
			}
			if (west.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.WEST, west, level, myPos.west());
			}
			if (up.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.UP, up, level, myPos.above());
			}
			if (down.getBlock() instanceof NetworkCable cable) {
				cable.addPacket(packet, Direction.DOWN, down, level, myPos.below());
			}
		}
	}

	@Override
	public final List<NetworkInterface> getAttachable() {
		return attachable;
	}

	public final List<NetworkPacket> getReceivedPackets() {
		List<NetworkPacket> packets = new ArrayList<>();
		for (NetworkInterface attachable : getAttachable()) {
			for (int i = 0; i < attachable.getPacketCount(); i ++) {
				packets.add(attachable.getPacket());
			}
		}
		return packets;
	}

	@Override
	public void load(@NotNull CompoundTag compoundTag) {
		super.load(compoundTag);
		if (compoundTag.contains("interfaces")) {
			attachable.clear();
			CompoundTag save_interfaces = (CompoundTag) compoundTag.get("interfaces");
			if (save_interfaces != null) {
				Set<String> inters = save_interfaces.getAllKeys();
				for (String inter : inters) {
					CompoundTag data = save_interfaces.getCompound(inter);
					attachable.add(new NetworkInterface(data.getUUID("id")));
				}
			}
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		CompoundTag save_interfaces = new CompoundTag();
		for (int i = 0; i < attachable.size(); i ++) {
			NetworkInterface networkInterface = attachable.get(i);
			CompoundTag inter = new CompoundTag();
			inter.putUUID("id", networkInterface.getId());
			save_interfaces.put(String.valueOf(i), inter);
		}
		compoundTag.put("interfaces", save_interfaces);
	}
}