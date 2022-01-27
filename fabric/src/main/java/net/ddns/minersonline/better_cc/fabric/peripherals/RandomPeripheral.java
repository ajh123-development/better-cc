package net.ddns.minersonline.better_cc.fabric.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.blocks.RandomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RandomPeripheral implements IPeripheral {
    private IComputerAccess my_computer;
    private Level world;
    private BlockPos pos;

    public void setWorld(Level world) {
        this.world = world;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public @NotNull String getType() {
        return "randomizer";
    }

    @Override
    public void attach(IComputerAccess computer) {
        my_computer = computer;
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof RandomPeripheral;
    }

    @LuaFunction
    public final int getRandom()
    {
        return Objects.requireNonNull(world).getBlockState(pos).getValue(RandomBlock.POWER);
    }

    @LuaFunction
    public final void toggle(boolean state)
    {
        Block be = world.getBlockState(pos).getBlock();
        if (be instanceof RandomBlock){
            BlockState randomState = Objects.requireNonNull(world).getBlockState(pos);
            world.setBlockAndUpdate(pos, randomState.setValue(RandomBlock.ENABLED, state).setValue(RandomBlock.POWER, 0));
        }
    }
}
