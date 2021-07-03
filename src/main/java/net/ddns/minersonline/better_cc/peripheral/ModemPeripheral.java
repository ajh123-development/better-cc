package net.ddns.minersonline.better_cc.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.LuaValues;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.blocks.modem.ModemBlock;
import net.ddns.minersonline.better_cc.blocks.modem.ModemTileEntity;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomBlock;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomTileEntity;
import net.ddns.minersonline.better_cc.interfaces.IWrenchMe;
import net.minecraft.block.BlockState;
import org.squiddev.cobalt.LuaString;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModemPeripheral implements IPeripheral {
    private final ModemTileEntity modemTileEntity;
    private final ModemBlock modemBlock;
    private IComputerAccess my_computer;

    public ModemPeripheral(ModemTileEntity modemTileEntity, ModemBlock modemBlock)
    {
        this.modemTileEntity = modemTileEntity;
        this.modemBlock = modemBlock;
    }

    @Nonnull
    @Override
    public String getType() {
        return "dsl_modem";
    }

    @Override
    public void attach(@Nonnull IComputerAccess computer) {
        this.my_computer = computer;
    }

    @Override
    public void detach(@Nonnull IComputerAccess computer) {

    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }

    @LuaFunction
    public final boolean send(String destIP, String message)
    {
        this.modemBlock.send(destIP, message);
        return true;
    }
    @LuaFunction
    public final String[] receiveLast()
    {
        String[] data = this.modemBlock.receive();
        return data;
    }

}
