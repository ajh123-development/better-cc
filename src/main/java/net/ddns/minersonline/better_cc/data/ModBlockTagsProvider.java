package net.ddns.minersonline.better_cc.data;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.setup.ModBlocks;
import net.ddns.minersonline.better_cc.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, better_cc.MOD_ID, existingFileHelper);
        System.out.println("[Data gen|Blocks Tags] Created!");
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.ORES_SILVER).add(ModBlocks.SILVER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_SILVER);
        tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER);

        System.out.println("[Data gen|Blocks Tags] Generated!");
    }
}
