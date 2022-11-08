package net.ddns.minersonline.BetterCC.blocks.modem.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.BetterCC.api.network.NetworkPacket;
import net.ddns.minersonline.BetterCC.api.network.device.NetworkAttachable;
import net.ddns.minersonline.BetterCC.blocks.modem.SerialModemEntity;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SerialModemPeripheral implements IPeripheral {
	private final SerialModemEntity modem;
	public SerialModemPeripheral(SerialModemEntity modem) {
		this.modem = modem;
	}

	@LuaFunction
	public final void send(int dest, String message) {
		CompoundTag data = new CompoundTag();
		data.putString("data", message);
		NetworkPacket packet = new NetworkPacket(dest, -1, data);
		modem.packets.add(packet);
	}

	@LuaFunction
	public final List<String> getReceivedMessages() {
		List<String> data = new ArrayList<>();
		for (NetworkPacket packet : modem.getReceivedPackets()) {
			data.add(packet.getData().getString("data"));
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
