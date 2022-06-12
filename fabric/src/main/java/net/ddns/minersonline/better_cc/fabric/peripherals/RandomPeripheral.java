package net.ddns.minersonline.better_cc.fabric.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.ddns.minersonline.better_cc.blocks.RandomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A Randomizer wrapped as a peripheral allows for, basic interaction with adjacent randomizers. <br>
 * <b>This is the Fabric version</b>.
 *
 * :::note
 * It may seem a bit weird that there are two versions for forge and fabric,<br>
 * but that is how it is defined in the Java code.
 * :::
 * <br>
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
     * @cc.usage Wrap a Randomizer and store its value in ```number```
     *
     * <pre>{@code
     * local random = peripheral.find("randomizer") or error("No Randomizer attached", 0)
     * number = random.getRandom() -- This returns a random number between 1 and 15
     * }</pre>
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
     * @cc.usage Wrap a Randomizer and turn it off
     *
     * <pre>{@code
     * local random = peripheral.find("randomizer") or error("No Randomizer attached", 0)
     * random.toggle(false)
     * }</pre>
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
