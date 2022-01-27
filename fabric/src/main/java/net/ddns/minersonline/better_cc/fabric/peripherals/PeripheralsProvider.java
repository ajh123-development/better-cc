package net.ddns.minersonline.better_cc.fabric.peripherals;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.ddns.minersonline.better_cc.blocks.RandomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PeripheralsProvider implements IPeripheralProvider {
    @Override
    public IPeripheral getPeripheral(@NotNull Level world, @NotNull BlockPos pos, @NotNull Direction side) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if(block instanceof RandomBlock) {
            RandomPeripheral RANDOM_PERIPHERAL = new RandomPeripheral();
            RANDOM_PERIPHERAL.setPos(pos);
            RANDOM_PERIPHERAL.setWorld(world);
            return RANDOM_PERIPHERAL;
        }
        return null;
    }
}
