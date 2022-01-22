package net.ddns.minersonline.better_cc;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.ddns.minersonline.better_cc.items.WrenchItem;
import net.ddns.minersonline.better_cc.setup.ModItems;
import net.ddns.minersonline.better_cc.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class BetterCC {
    public static final String MOD_ID = "better_cc";
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    public static final CreativeModeTab MAIN_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "better_cc"), () ->
            new ItemStack(ModItems.WRENCH_ITEM.get()));
    

    public static void init() {
        Registration.register();
        
        System.out.println(BetterCCExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
