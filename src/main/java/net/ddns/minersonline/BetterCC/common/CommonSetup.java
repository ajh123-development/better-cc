/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common;

import net.ddns.minersonline.BetterCC.common.bus.device.rpc.RPCMethodParameterTypeAdapters;
import net.ddns.minersonline.BetterCC.common.inet.InternetManager;
import net.ddns.minersonline.BetterCC.common.integration.IMC;
import net.ddns.minersonline.BetterCC.common.network.Network;
import net.ddns.minersonline.BetterCC.common.util.ServerScheduler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class CommonSetup {
    @SubscribeEvent
    public static void handleSetupEvent(final FMLCommonSetupEvent event) {
        IMC.initialize();
        Network.initialize();
        RPCMethodParameterTypeAdapters.initialize();
        ServerScheduler.initialize();
        InternetManager.initialize();
    }
}
