package net.ddns.minersonline.BetterCC.blocks.modem.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.ddns.minersonline.BetterCC.blocks.modem.SerialModemEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerialModemPeripheral implements IPeripheral {
	private final SerialModemEntity modem;
	public SerialModemPeripheral(SerialModemEntity modem) {
		this.modem = modem;
	}

	@LuaFunction
	public final void send(Map<?, ?> message) {
		NetworkPacket packet = new NetworkPacket(message);
		modem.packets.add(packet);
	}

	@LuaFunction
	public final List<Map<?, ?>> getReceivedMessages() {
		List<Map<?, ?>> data = new ArrayList<>();
		for (NetworkPacket packet : modem.getReceivedPackets()) {
			data.add(packet.getData());
		}
		return data;
	}

	@NotNull
	@Override
	public String getType() {
		return "serial_modem";
	}

	@Override
	public boolean equals(@Nullable IPeripheral other) {
		return other instanceof SerialModemPeripheral;
	}
}
