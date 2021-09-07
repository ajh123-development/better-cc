package net.ddns.minersonline.better_cc.data;

import net.ddns.minersonline.better_cc.better_cc;
import net.ddns.minersonline.better_cc.data.client.ModBlockStateProvider;
import net.ddns.minersonline.better_cc.data.client.ModItemModelProvider;
import net.ddns.minersonline.better_cc.data.client.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = better_cc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators(){}
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        System.out.println("[Data gen] Loaded Block State provider");
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        System.out.println("[Data gen] Loaded Item Model provider");

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        System.out.println("[Data gen] Loaded Block Tags provider");
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        System.out.println("[Data gen] Loaded Item Tags provider");

        gen.addProvider(new ModLootTableProvider(gen));
        System.out.println("[Data gen] Loaded Loot Tables provider");
        gen.addProvider(new ModRecipeProvider(gen));
        System.out.println("[Data gen] Loaded Recipes provider");
    }
}
