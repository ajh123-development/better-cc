/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.data;

import net.ddns.minersonline.BetterCC.api.bus.device.data.Firmware;
import net.ddns.minersonline.BetterCC.api.util.Registries;
import net.ddns.minersonline.BetterCC.common.util.RegistryUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class FirmwareRegistry {
    private static final DeferredRegister<Firmware> INITIALIZER = RegistryUtils.getInitializerFor(Registries.FIRMWARE);

    ///////////////////////////////////////////////////////////////////

    private static final Supplier<IForgeRegistry<Firmware>> REGISTRY = INITIALIZER.makeRegistry(Firmware.class, RegistryBuilder::new);

    ///////////////////////////////////////////////////////////////////

    public static final RegistryObject<Firmware> BUILDROOT = INITIALIZER.register("buildroot", BuildrootFirmware::new);

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
    }

    @Nullable
    public static ResourceLocation getKey(final Firmware firmware) {
        return firmware.getRegistryName();
    }

    @Nullable
    public static Firmware getValue(final ResourceLocation location) {
        return REGISTRY.get().getValue(location);
    }

    public static Stream<Firmware> values() {
        return REGISTRY.get().getValues().stream();
    }
}
