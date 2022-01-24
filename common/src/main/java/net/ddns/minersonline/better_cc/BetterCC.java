package net.ddns.minersonline.better_cc;

import dev.architectury.registry.CreativeTabRegistry;
import net.ddns.minersonline.better_cc.setup.ModItems;
import net.ddns.minersonline.better_cc.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;


public class BetterCC {
    public static final String MOD_ID = "better_cc";

   public static final CreativeModeTab MAIN_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "better_cc"), () ->
            new ItemStack(ModItems.WRENCH_ITEM.get()));

    public static void init() {
        Registration.register();
        
        System.out.println(BetterCCExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
