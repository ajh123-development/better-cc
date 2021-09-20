package net.ddns.minersonline.better_cc.setup;

import net.ddns.minersonline.better_cc.blocks.computer.ComputerContainer;
import net.ddns.minersonline.better_cc.blocks.computer.ComputerScreen;
import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressContainer;
import net.ddns.minersonline.better_cc.blocks.metalpress.MetalPressScreen;
import net.ddns.minersonline.better_cc.blocks.powermachine.PowerMachineContainer;
import net.ddns.minersonline.better_cc.blocks.powermachine.PowerMachineScreen;
import net.ddns.minersonline.better_cc.blocks.punchcardreader.PunchCardReaderContainer;
import net.ddns.minersonline.better_cc.blocks.punchcardreader.PunchCardReaderScreen;
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
    public static final RegistryObject<ContainerType<ComputerContainer>> COMPUTER = register("computer", ComputerContainer::new);
    public static final RegistryObject<ContainerType<PowerMachineContainer>> POWER_MACHINE = register("power_machine", PowerMachineContainer::new);
    public static final RegistryObject<ContainerType<PunchCardReaderContainer>> PUNCH_CARD_READER = register("punch_card_reader", PunchCardReaderContainer::new);

    private ModContainerTypes() {
    }

    static void register() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.register(METAL_PRESS.get(), MetalPressScreen::new);
        ScreenManager.register(COMPUTER.get(), ComputerScreen::new);
        ScreenManager.register(POWER_MACHINE.get(), PowerMachineScreen::new);
        ScreenManager.register(PUNCH_CARD_READER.get(), PunchCardReaderScreen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return Registration.CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }
}
