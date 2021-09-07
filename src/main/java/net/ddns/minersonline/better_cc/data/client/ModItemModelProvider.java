package net.ddns.minersonline.better_cc.data.client;

import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, better_cc.MOD_ID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    protected void registerModels() {
        ResourceLocation blockPath = modLoc("block");
        System.out.println("Path  ::::"+blockPath.getPath());

        //Blocks
        withExistingParent("silver_block", modLoc("block/silver_block"));
        withExistingParent("silver_ore", modLoc("block/silver_ore"));
        withExistingParent("hardwood_log", modLoc("block/hardwood_log"));
        withExistingParent("computer", modLoc("block/computer"));
        withExistingParent("random", modLoc("block/random"));
        withExistingParent("modem", modLoc("block/modem"));

        // Items
        ModelFile ItemGenerated = getExistingFile(mcLoc("item/generated"));
        itemBuilder(ItemGenerated, "silver_ingot");
        itemBuilder(ItemGenerated, "wrench");
        itemBuilder(ItemGenerated, "punch_card");
    }

    private void itemBuilder(ModelFile ItemGenerated, String name) {
        getBuilder(name).parent(ItemGenerated).texture("layer0", "item/"+name);
    }
}
