package net.ddns.minersonline.BetterCC.forge;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.ddns.minersonline.BetterCC.blocks.modem.peripheral.SerialModemPeripheral;
import net.ddns.minersonline.BetterCC.setup.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class SerialModemPeripheralProvider implements IPeripheralProvider {
	@Override
	public LazyOptional<IPeripheral> getPeripheral(@NotNull Level world, @NotNull BlockPos pos, @NotNull Direction side) {
		BlockState blockState = world.getBlockState(pos);

		if(blockState.is(ModBlocks.SERIAL_MODEM.get())) {
			return LazyOptional.of(SerialModemPeripheral::new);
		}

		return LazyOptional.empty();
	}
}
