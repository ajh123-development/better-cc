package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressContainer;
import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;


public final class ModContainerTypes {
    public static final RegistryObject<ContainerType<MetalPressContainer>> METAL_PRESS = register("metal_press", MetalPressContainer::new);

    private ModContainerTypes() {}

    static void register() {}

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.register(METAL_PRESS.get(), MetalPressScreen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return Registration.CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }
}