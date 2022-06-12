/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.capabilities;

import net.ddns.minersonline.BetterCC.api.bus.DeviceBusElement;
import net.ddns.minersonline.BetterCC.api.bus.device.Device;
import net.ddns.minersonline.BetterCC.api.capabilities.NetworkInterface;
import net.ddns.minersonline.BetterCC.api.capabilities.RedstoneEmitter;
import net.ddns.minersonline.BetterCC.api.capabilities.Robot;
import net.ddns.minersonline.BetterCC.api.capabilities.TerminalUserProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public final class CapabilityRegistry {
    static final Capability<IEnergyStorage> ENERGY_STORAGE = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<IFluidHandler> FLUID_HANDLER = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>() { });

    static final Capability<DeviceBusElement> DEVICE_BUS_ELEMENT = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<Device> DEVICE = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<RedstoneEmitter> REDSTONE_EMITTER = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<NetworkInterface> NETWORK_INTERFACE = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<TerminalUserProvider> TERMINAL_USER_PROVIDER = CapabilityManager.get(new CapabilityToken<>() { });
    static final Capability<Robot> ROBOT = CapabilityManager.get(new CapabilityToken<>() { });

    ///////////////////////////////////////////////////////////////////

    @SubscribeEvent
    public static void initialize(final RegisterCapabilitiesEvent event) {
        Capabilities.registerCapabilities(event::register);
    }
}
