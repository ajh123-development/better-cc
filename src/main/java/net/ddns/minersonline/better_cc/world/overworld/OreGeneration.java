package net.ddns.minersonline.better_cc.world.overworld;

import net.ddns.minersonline.better_cc.setup.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {
    public static final ConfiguredFeature<?, ?> silverOre = Feature.ORE.configured(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.SILVER_ORE.get().defaultBlockState(), 4)) //vein size
            .range(20).squared() //maximum y level where ore can spawn
            .count(3); //how many veins maximum per chunck

    public static void generateOre(final BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        if (!event.getCategory().equals(Biome.Category.NONE)) {
            generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, silverOre);
        }
    }
}
