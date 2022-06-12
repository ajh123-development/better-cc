/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common;

import li.cil.ceres.Ceres;
import net.ddns.minersonline.BetterCC.api.API;
import net.ddns.minersonline.BetterCC.client.ClientSetup;
import net.ddns.minersonline.BetterCC.client.manual.Manuals;
import net.ddns.minersonline.BetterCC.common.block.Blocks;
import net.ddns.minersonline.BetterCC.common.blockentity.BlockEntities;
import net.ddns.minersonline.BetterCC.common.bus.device.DeviceTypes;
import net.ddns.minersonline.BetterCC.common.bus.device.data.BlockDeviceDataRegistry;
import net.ddns.minersonline.BetterCC.common.bus.device.data.FirmwareRegistry;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.ProviderRegistry;
import net.ddns.minersonline.BetterCC.common.container.Containers;
import net.ddns.minersonline.BetterCC.common.entity.Entities;
import net.ddns.minersonline.BetterCC.common.item.ItemRenameHandler;
import net.ddns.minersonline.BetterCC.common.item.Items;
import net.ddns.minersonline.BetterCC.common.item.crafting.RecipeSerializers;
import net.ddns.minersonline.BetterCC.common.serialization.ceres.Serializers;
import net.ddns.minersonline.BetterCC.common.tags.BlockTags;
import net.ddns.minersonline.BetterCC.common.tags.ItemTags;
import net.ddns.minersonline.BetterCC.common.util.RegistryUtils;
import net.ddns.minersonline.BetterCC.common.util.SoundEvents;
import net.ddns.minersonline.BetterCC.common.vm.provider.DeviceTreeProviders;
import li.cil.sedna.Sedna;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(API.MOD_ID)
public final class Main {
    public Main() {
        Ceres.initialize();
        Sedna.initialize();
        DeviceTreeProviders.initialize();
        Serializers.initialize();

        ConfigManager.add(Config::new);
        ConfigManager.initialize();

        RegistryUtils.begin();

        ItemTags.initialize();
        BlockTags.initialize();
        Blocks.initialize();
        Items.initialize();
        BlockEntities.initialize();
        Entities.initialize();
        Containers.initialize();
        RecipeSerializers.initialize();
        SoundEvents.initialize();

        ProviderRegistry.initialize();
        DeviceTypes.initialize();
        BlockDeviceDataRegistry.initialize();
        FirmwareRegistry.initialize();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> Manuals::initialize);

        RegistryUtils.finish();

        ItemRenameHandler.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().register(CommonSetup.class);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
            FMLJavaModLoadingContext.get().getModEventBus().register(ClientSetup.class));
    }
}
