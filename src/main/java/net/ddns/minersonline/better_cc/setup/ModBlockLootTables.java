package net.ddns.minersonline.better_cc.setup;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.fml.RegistryObject;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.SILVER_BLOCK.get());
        dropSelf(ModBlocks.SILVER_ORE.get());
        dropSelf(ModBlocks.HARDWOOD_LOG_BLOCK.get());
        dropSelf(ModBlocks.COMPUTER.get());
        dropSelf(ModBlocks.METAL_PRESS.get());
        dropSelf(ModBlocks.RANDOM.get());
        dropSelf(ModBlocks.MODEM.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}