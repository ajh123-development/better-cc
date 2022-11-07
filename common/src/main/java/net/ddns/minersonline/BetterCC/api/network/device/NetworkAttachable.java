package net.ddns.minersonline.BetterCC.api.network.device;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class NetworkAttachable {
	private static int idCount = 0;
	private final int myId;
	private final List<NetworkPacket> packets = new ArrayList<>();
	private final Direction side;

	public NetworkAttachable(Direction side) {
		this.myId = idCount;
		idCount+=1;
		this.side = side;
	}

	public int getMyId() {
		return myId;
	}

	public NetworkPacket getPacket() {
		return packets.remove(packets.size()-1);
	}

	public boolean addPacket(NetworkPacket packet) {
		return packets.add(packet);
	}

	public Direction getSide() {
		return side;
	}
}
