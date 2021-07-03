package net.ddns.minersonline.better_cc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomBlock;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomTileEntity;
import net.minecraft.block.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RandomPeripheral implements IPeripheral {
    private final RandomTileEntity randomizer;
    private final RandomBlock randomBlock;
    private IComputerAccess my_computer;

    public RandomPeripheral(RandomTileEntity randomizerEntity, RandomBlock randomBlock)
    {
        this.randomizer = randomizerEntity;
        this.randomBlock = randomBlock;
    }

    @Nonnull
    @Override
    public String getType() {
        return "randomizer";
    }

    @Override
    public void attach(@Nonnull IComputerAccess computer) {
        my_computer = computer;
    }

    @Override
    public void detach(@Nonnull IComputerAccess computer) {

    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }

    @LuaFunction
    public final int getRandom()
    {
        return randomizer.getLevel().getBlockState(randomizer.getBlockPos()).getValue(randomBlock.POWER);
    }

    @LuaFunction
    public final void toggle(boolean state)
    {
        BlockState randomState = randomizer.getLevel().getBlockState(randomizer.getBlockPos());
        randomizer.getLevel().setBlock(randomizer.getBlockPos(), randomState.setValue(randomBlock.ENABLED, state).setValue(randomBlock.POWER, 0), 3);
    }
}
