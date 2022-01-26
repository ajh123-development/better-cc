package net.ddns.minersonline.better_cc.forge.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.blocks.RandomBlock;
import net.ddns.minersonline.better_cc.blocks.RandomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
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

    @Nonnull
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
        RandomEntity randomizer = (RandomEntity) world.getBlockEntity(pos);
        assert randomizer != null;
        BlockState randomState = Objects.requireNonNull(randomizer.getLevel()).getBlockState(randomizer.getBlockPos());
        randomizer.getLevel().setBlockAndUpdate(randomizer.getBlockPos(), randomState.setValue(RandomBlock.ENABLED, state).setValue(RandomBlock.POWER, 0));
    }
}
