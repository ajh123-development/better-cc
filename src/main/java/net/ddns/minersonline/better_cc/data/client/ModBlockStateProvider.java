package net.ddns.minersonline.better_cc.data.client;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, better_cc.MOD_ID, exFileHelper);
        System.out.println("[Data gen|Block states] Created!");
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_BLOCK.get());
        simpleBlock(ModBlocks.SILVER_ORE.get());
        axisBlock(ModBlocks.HARDWOOD_LOG_BLOCK.get());

        horizontalBlock(ModBlocks.POWER_MACHINE.get(),
                modLoc("block/machine/machine_side"),
                modLoc("block/machine/power_machine_front"),
                modLoc("block/machine/machine_top")
        );

        horizontalBlock(ModBlocks.COMPUTER.get(),
                modLoc("block/machine/machine_side"),
                modLoc("block/computer/computer_front"),
                modLoc("block/machine/machine_top")
        );

        horizontalBlock(ModBlocks.RANDOM.get(),
                modLoc("block/machine/machine_side"),
                modLoc("block/machine/machine_front"),
                modLoc("block/machine/machine_top")
        );

        horizontalBlock(ModBlocks.MODEM.get(),
                modLoc("block/machine/machine_side"),
                modLoc("block/machine/machine_front"),
                modLoc("block/machine/machine_top")
        );

        horizontalBlock(ModBlocks.PUNCH_CARD_READER.get(),
                modLoc("block/machine/machine_side"),
                modLoc("block/computer/punch_card_reader_front"),
                modLoc("block/machine/machine_top")
        );
        System.out.println("[Data gen|Block states] Loaded all Block States and models");
    }
}
