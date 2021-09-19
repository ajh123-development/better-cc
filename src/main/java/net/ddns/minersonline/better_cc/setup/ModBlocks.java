package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.blocks.computer.ComputerBlock;
import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressBlock;
import net.ddns.minersonline.better_cc.blocks.modem.ModemBlock;
import net.ddns.minersonline.better_cc.blocks.powermachine.PowerMachineBlock;
import net.ddns.minersonline.better_cc.blocks.punchcardreader.PunchCardReaderBlock;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

import static net.ddns.minersonline.better_cc.better_cc.ITEM_GROUP;

public class ModBlocks {
    // Resources
    public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () ->
            new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(3, 10).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(3, 10)));

    public static final RegistryObject<RotatedPillarBlock> HARDWOOD_LOG_BLOCK = register("hardwood_log", () ->
            new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F)));

    // Machines
    public static final RegistryObject<MetalPressBlock> METAL_PRESS = register("metal_press", () ->
            new MetalPressBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(4, 20)
                    .sound(SoundType.METAL)));


    public static final RegistryObject<PowerMachineBlock> POWER_MACHINE = register("power_machine", () ->
            new PowerMachineBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));


    // Computer and peripherals
    public static final RegistryObject<ComputerBlock> COMPUTER = register("computer", () ->
            new ComputerBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));

    public static final RegistryObject<RandomBlock> RANDOM = register("random", () ->
            new RandomBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1)));

    public static final RegistryObject<ModemBlock> MODEM = register("modem", () ->
            new ModemBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(4, 20)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<PunchCardReaderBlock> PUNCH_CARD_READER = register("punch_card_reader", () ->
            new PunchCardReaderBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(4, 20)
                    .sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return Registration.BLOCKS.register(name, block);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ITEM_GROUP)));

        return ret;
    }


    static void register(){}
}
