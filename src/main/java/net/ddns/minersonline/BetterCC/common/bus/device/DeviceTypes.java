/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device;

import net.ddns.minersonline.BetterCC.api.API;
import net.ddns.minersonline.BetterCC.api.bus.device.DeviceType;
import net.ddns.minersonline.BetterCC.common.bus.device.util.DeviceTypeImpl;
import net.ddns.minersonline.BetterCC.common.tags.ItemTags;
import net.ddns.minersonline.BetterCC.common.util.RegistryUtils;
import net.ddns.minersonline.BetterCC.common.util.TranslationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public final class DeviceTypes {
    private static final DeferredRegister<DeviceType> DEVICE_TYPES = RegistryUtils.getInitializerFor(DeviceType.REGISTRY);

    ///////////////////////////////////////////////////////////////////

    public static final Supplier<IForgeRegistry<DeviceType>> DEVICE_TYPE_REGISTRY = DEVICE_TYPES.makeRegistry(DeviceType.class, RegistryBuilder::new);

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
        register(ItemTags.DEVICES_MEMORY);
        register(ItemTags.DEVICES_HARD_DRIVE);
        register(ItemTags.DEVICES_FLASH_MEMORY);
        register(ItemTags.DEVICES_CARD);
        register(ItemTags.DEVICES_ROBOT_MODULE);
        register(ItemTags.DEVICES_FLOPPY);
        register(ItemTags.DEVICES_NETWORK_TUNNEL);
    }

    ///////////////////////////////////////////////////////////////////

    private static void register(final TagKey<Item> tag) {
        final String id = tag.location().getPath().replaceFirst("^devices/", "");
        DEVICE_TYPES.register(id, () -> new DeviceTypeImpl(
            tag,
            new ResourceLocation(API.MOD_ID, "gui/icon/" + id),
            TranslationUtils.text("gui.{mod}.device_type." + id)
        ));
    }
}
