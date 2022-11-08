package net.ddns.minersonline.BetterCC.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.BetterCC.BetterCC;
import net.ddns.minersonline.BetterCC.blocks.crafting_machine.CraftingMachine;
import net.ddns.minersonline.BetterCC.blocks.modem.SerialCable;
import net.ddns.minersonline.BetterCC.blocks.modem.SerialModem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModBlocks {

	public static final RegistrySupplier<CraftingMachine> CRAFTING_MACHINE = register("crafting_machine", () ->
			new CraftingMachine(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));

	public static final RegistrySupplier<SerialCable> SERIAL_CABLE = register("serial_cable", () ->
			new SerialCable(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));

	public static final RegistrySupplier<SerialModem> SERIAL_MODEM = register("serial_modem", () ->
			new SerialModem(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));


	private static <T extends Block> RegistrySupplier<Block> registerNoItem(String name, Supplier<T> block){
		return Registration.BLOCKS.register(name, block);
	}
	private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block) {
		RegistrySupplier<T> ret = (RegistrySupplier<T>) registerNoItem(name, block);
		Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(BetterCC.MAIN_TAB)));

		return ret;
	}

	static void register(){}
}
