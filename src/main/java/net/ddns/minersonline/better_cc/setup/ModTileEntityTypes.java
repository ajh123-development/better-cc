package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.blocks.computer.ComputerTileEntity;
import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressTileEntity;
import net.ddns.minersonline.better_cc.blocks.modem.ModemTileEntity;
import net.ddns.minersonline.better_cc.blocks.punchcardreader.PunchCardReaderTileEntity;
import net.ddns.minersonline.better_cc.blocks.randomizer.RandomTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import java.util.function.Supplier;

public class ModTileEntityTypes {
    public static final RegistryObject<TileEntityType<MetalPressTileEntity>> METAL_PRESS = register(
            "metal_press",
            MetalPressTileEntity::new,
            ModBlocks.METAL_PRESS
    );

    public static final RegistryObject<TileEntityType<ComputerTileEntity>> COMPUTER = register(
            "computer",
            ComputerTileEntity::new,
            ModBlocks.COMPUTER
    );

    public static final RegistryObject<TileEntityType<RandomTileEntity>> RANDOM = register(
            "random",
            RandomTileEntity::new,
            ModBlocks.RANDOM
    );

    public static final RegistryObject<TileEntityType<ModemTileEntity>> MODEM = register(
            "modem",
            ModemTileEntity::new,
            ModBlocks.MODEM
    );

    public static final RegistryObject<TileEntityType<PunchCardReaderTileEntity>> PUNCH_CARD_READER = register(
            "punch_card_reader",
            PunchCardReaderTileEntity::new,
            ModBlocks.MODEM
    );

    static void register() {}

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
}