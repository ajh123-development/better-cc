/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider;

import net.ddns.minersonline.BetterCC.api.bus.device.provider.BlockDeviceProvider;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceProvider;
import net.ddns.minersonline.BetterCC.api.util.Registries;
import net.ddns.minersonline.BetterCC.common.util.RegistryUtils;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public final class ProviderRegistry {
    private static final DeferredRegister<BlockDeviceProvider> BLOCK_DEVICE_PROVIDERS = RegistryUtils.getInitializerFor(Registries.BLOCK_DEVICE_PROVIDER);
    public static final Supplier<IForgeRegistry<BlockDeviceProvider>> BLOCK_DEVICE_PROVIDER_REGISTRY = BLOCK_DEVICE_PROVIDERS.makeRegistry(BlockDeviceProvider.class, RegistryBuilder::new);

    ///////////////////////////////////////////////////////////////////

    private static final DeferredRegister<ItemDeviceProvider> ITEM_DEVICE_PROVIDERS = RegistryUtils.getInitializerFor(Registries.ITEM_DEVICE_PROVIDER);
    public static final Supplier<IForgeRegistry<ItemDeviceProvider>> ITEM_DEVICE_PROVIDER_REGISTRY = ITEM_DEVICE_PROVIDERS.makeRegistry(ItemDeviceProvider.class, RegistryBuilder::new);

    ///////////////////////////////////////////////////////////////////

    public static void initialize() {
        Providers.registerBlockDeviceProviders(BLOCK_DEVICE_PROVIDERS::register);
        Providers.registerItemDeviceProviders(ITEM_DEVICE_PROVIDERS::register);
    }
}
