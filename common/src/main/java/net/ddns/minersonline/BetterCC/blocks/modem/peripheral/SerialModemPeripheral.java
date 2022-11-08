package net.ddns.minersonline.BetterCC.blocks.modem.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerialModemPeripheral implements IPeripheral {
	@LuaFunction
	public final String test() {
		return "Hello World!";
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
