/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.util;

import net.ddns.minersonline.BetterCC.api.API;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SoundEvents {
    private static final DeferredRegister<SoundEvent> SOUNDS = RegistryUtils.getInitializerFor(ForgeRegistries.SOUND_EVENTS);

    ///////////////////////////////////////////////////////////////////

    public static final RegistryObject<SoundEvent> COMPUTER_RUNNING = register("computer_running");
    public static final RegistryObject<SoundEvent> FLOPPY_ACCESS = register("floppy_access");
    public static final RegistryObject<SoundEvent> FLOPPY_EJECT = register("floppy_eject");
    public static final RegistryObject<SoundEvent> FLOPPY_INSERT = register("floppy_insert");
    public static final RegistryObject<SoundEvent> HDD_ACCESS = register("hdd_access");

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
    }

    ///////////////////////////////////////////////////////////////////

    private static RegistryObject<SoundEvent> register(final String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(API.MOD_ID, name)));
    }
}
