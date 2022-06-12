/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.client;

import net.ddns.minersonline.BetterCC.api.bus.device.DeviceType;
import net.ddns.minersonline.BetterCC.client.gui.*;
import net.ddns.minersonline.BetterCC.client.gui.*;
import net.ddns.minersonline.BetterCC.client.item.CustomItemColors;
import net.ddns.minersonline.BetterCC.client.item.CustomItemModelProperties;
import net.ddns.minersonline.BetterCC.client.model.BusCableModelLoader;
import net.ddns.minersonline.BetterCC.client.renderer.BusInterfaceNameRenderer;
import net.ddns.minersonline.BetterCC.client.renderer.ProjectorDepthRenderer;
import net.ddns.minersonline.BetterCC.client.renderer.blockentity.*;
import net.ddns.minersonline.BetterCC.client.renderer.blockentity.*;
import net.ddns.minersonline.BetterCC.client.renderer.color.BusCableBlockColor;
import net.ddns.minersonline.BetterCC.client.renderer.entity.RobotRenderer;
import net.ddns.minersonline.BetterCC.client.renderer.entity.model.RobotModel;
import net.ddns.minersonline.BetterCC.common.block.Blocks;
import net.ddns.minersonline.BetterCC.common.blockentity.BlockEntities;
import net.ddns.minersonline.BetterCC.common.bus.device.DeviceTypes;
import net.ddns.minersonline.BetterCC.common.container.Containers;
import net.ddns.minersonline.BetterCC.common.entity.Entities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Objects;

public final class ClientSetup {
    @SubscribeEvent
    public static void handleSetupEvent(final FMLClientSetupEvent event) {
        BusInterfaceNameRenderer.initialize();

        BlockEntityRenderers.register(BlockEntities.COMPUTER.get(), ComputerRenderer::new);
        BlockEntityRenderers.register(BlockEntities.NETWORK_CONNECTOR.get(), NetworkConnectorRenderer::new);
        BlockEntityRenderers.register(BlockEntities.DISK_DRIVE.get(), DiskDriveRenderer::new);
        BlockEntityRenderers.register(BlockEntities.CHARGER.get(), ChargerRenderer::new);
        BlockEntityRenderers.register(BlockEntities.PROJECTOR.get(), ProjectorRenderer::new);

        event.enqueueWork(() -> {
            CustomItemModelProperties.initialize();
            CustomItemColors.initialize();

            MenuScreens.register(Containers.COMPUTER.get(), ComputerContainerScreen::new);
            MenuScreens.register(Containers.COMPUTER_TERMINAL.get(), ComputerTerminalScreen::new);
            MenuScreens.register(Containers.ROBOT.get(), RobotContainerScreen::new);
            MenuScreens.register(Containers.ROBOT_TERMINAL.get(), RobotTerminalScreen::new);
            MenuScreens.register(Containers.NETWORK_TUNNEL.get(), NetworkTunnelScreen::new);

            ItemBlockRenderTypes.setRenderLayer(Blocks.BUS_CABLE.get(), renderType -> true);
            Minecraft.getInstance().getBlockColors().register(new BusCableBlockColor(), Blocks.BUS_CABLE.get());

            // We need to register this manually, because static init throws errors when running data generation.
            MinecraftForge.EVENT_BUS.register(ProjectorDepthRenderer.class);
        });
    }

    @SubscribeEvent
    public static void handleModelRegistryEvent(final ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(Blocks.BUS_CABLE.getId(), new BusCableModelLoader());
    }

    @SubscribeEvent
    public static void handleTextureStitchEvent(final TextureStitchEvent.Pre event) {
        if (!Objects.equals(event.getAtlas().location(), InventoryMenu.BLOCK_ATLAS)) {
            return;
        }

        for (final DeviceType deviceType : DeviceTypes.DEVICE_TYPE_REGISTRY.get().getValues()) {
            event.addSprite(deviceType.getBackgroundIcon());
        }

        event.addSprite(ComputerRenderer.OVERLAY_POWER_LOCATION);
        event.addSprite(ComputerRenderer.OVERLAY_STATUS_LOCATION);
        event.addSprite(ComputerRenderer.OVERLAY_TERMINAL_LOCATION);

        event.addSprite(ChargerRenderer.EFFECT_LOCATION);
    }

    @SubscribeEvent
    public static void handleEntityRendererRegisterEvent(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Entities.ROBOT.get(), RobotRenderer::new);
    }

    @SubscribeEvent
    public static void handleRegisterLayerDefinitionsEvent(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RobotModel.ROBOT_MODEL_LAYER, RobotModel::createRobotLayer);
    }
}
