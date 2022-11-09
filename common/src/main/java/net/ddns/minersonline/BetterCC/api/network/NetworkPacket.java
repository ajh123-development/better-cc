package net.ddns.minersonline.BetterCC.api.network;

import java.util.Map;

public class NetworkPacket {
	private static int idCount = 0;
	private final int myId;
	private final Map<?, ?> data;

	public NetworkPacket(Map<?, ?> data) {
		this.myId = idCount;
		idCount+=1;
		this.data = data;
	}

	public int getMyId() {
		return myId;
	}

	public Map<?, ?> getData() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NetworkPacket pkt) {
			return this.myId == pkt.myId;
		}
		return super.equals(obj);
	}
}
