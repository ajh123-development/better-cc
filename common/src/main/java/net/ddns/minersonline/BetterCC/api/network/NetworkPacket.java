package net.ddns.minersonline.BetterCC.api.network;

import net.minecraft.nbt.CompoundTag;

public class NetworkPacket {
	private static int idCount = 0;
	private final int myId;
	private final int dest;
	private final int source;
	private final CompoundTag data;

	public NetworkPacket(int dest, int source, CompoundTag data) {
		this.myId = idCount;
		idCount+=1;
		this.dest = dest;
		this.source = source;
		this.data = data;
	}

	public int getMyId() {
		return myId;
	}

	public int getDest() {
		return dest;
	}

	public int getSource() {
		return source;
	}

	public CompoundTag getData() {
		return data;
	}
}
