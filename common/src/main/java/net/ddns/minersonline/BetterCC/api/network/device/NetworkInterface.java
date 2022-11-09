package net.ddns.minersonline.BetterCC.api.network.device;

import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetworkInterface {
	private static int idCount = 0;
	private final int myId;
	private final List<NetworkPacket> packets = new ArrayList<>();
	private final UUID id;

	public NetworkInterface(UUID id) {
		this.myId = idCount;
		idCount+=1;
		this.id = id;
	}

	public int getMyId() {
		return myId;
	}

	public NetworkPacket getPacket() {
		return packets.remove(packets.size()-1);
	}

	public int getPacketCount() {
		return packets.size();
	}

	public boolean addPacket(NetworkPacket packet) {
		return packets.add(packet);
	}

	public UUID getId() {
		return id;
	}
}
