/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.integration.jei;

import net.ddns.minersonline.BetterCC.api.API;
import net.ddns.minersonline.BetterCC.client.gui.AbstractMachineInventoryScreen;
import net.ddns.minersonline.BetterCC.client.gui.AbstractMachineTerminalScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class ExtraGuiAreasJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(API.MOD_ID, "extra_gui_areas");
    }

    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(AbstractMachineInventoryScreen.class, new AbstractMachineInventoryScreenGuiContainerHandler());
        registration.addGenericGuiContainerHandler(AbstractMachineTerminalScreen.class, new AbstractMachineTerminalScreenGuiContainerHandler());
    }

    private static final class AbstractMachineInventoryScreenGuiContainerHandler implements IGuiContainerHandler<AbstractMachineInventoryScreen<?>> {
        @Override
        public List<Rect2i> getGuiExtraAreas(final AbstractMachineInventoryScreen<?> screen) {
            return screen.getExtraAreas();
        }
    }

    private static final class AbstractMachineTerminalScreenGuiContainerHandler implements IGuiContainerHandler<AbstractMachineTerminalScreen<?>> {
        @Override
        public List<Rect2i> getGuiExtraAreas(final AbstractMachineTerminalScreen<?> screen) {
            return screen.getExtraAreas();
        }
    }
}
