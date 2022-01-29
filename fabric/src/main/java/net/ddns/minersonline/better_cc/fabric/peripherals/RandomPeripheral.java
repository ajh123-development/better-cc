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

/**
 * This is the Fabric version.
 *
 * A Randomizer wrapped as a peripheral.
 *
 * This allows for basic interaction with adjacent randomizers.
 * @cc.module randomizer fabric
 */
public class RandomPeripheral implements IPeripheral {
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
    public boolean equals(IPeripheral other) {
        return other instanceof RandomPeripheral;
    }

    /**
     * Returns a random number that is also the redstone output of the Randomizer Block
     *
     * @return The random number itself
     * @usage
     * ```lua
     * random = peripheral.wrap("left") -- "left" is the side of the computer or turtle that the Randomizer
     * number = random.getRandom() -- This returns a random number between 1 and 15
     * ``
     */
    @LuaFunction
    public final int getRandom()
    {
        return Objects.requireNonNull(world).getBlockState(pos).getValue(RandomBlock.POWER);
    }

    /**
     * Sets the enabled state on the Randomizer Block
     *
     * @param state True or False
     * @usage
     * ```lua
     * random = peripheral.wrap("left") -- "left" is the side of the computer or turtle that the Randomizer
     * random.toggle() -- This enables or disables the Randomizer
     * ``
     */
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
