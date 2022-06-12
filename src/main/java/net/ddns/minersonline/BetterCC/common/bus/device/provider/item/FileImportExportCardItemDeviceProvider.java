/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.provider.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.provider.ItemDeviceQuery;
import net.ddns.minersonline.BetterCC.api.capabilities.TerminalUserProvider;
import net.ddns.minersonline.BetterCC.common.Config;
import net.ddns.minersonline.BetterCC.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.ddns.minersonline.BetterCC.common.item.Items;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public final class FileImportExportCardItemDeviceProvider extends AbstractItemDeviceProvider {
    public FileImportExportCardItemDeviceProvider() {
        super(Items.FILE_IMPORT_EXPORT_CARD);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected boolean matches(final ItemDeviceQuery query) {
        return super.matches(query) && getTerminalUserProvider(query).isPresent();
    }

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        return getTerminalUserProvider(query).map(provider ->
            new FileImportExportCardItemDevice(query.getItemStack(), provider));
    }

    @Override
    protected int getItemDeviceEnergyConsumption(final ItemDeviceQuery query) {
        return Config.fileImportExportCardEnergyPerTick;
    }

    ///////////////////////////////////////////////////////////////////

    private Optional<TerminalUserProvider> getTerminalUserProvider(final ItemDeviceQuery query) {
        if (query.getContainerBlockEntity().isPresent()) {
            final LazyOptional<TerminalUserProvider> capability = query.getContainerBlockEntity().get()
                .getCapability(Capabilities.terminalUserProvider());
            if (capability.isPresent()) {
                return capability.resolve();
            }
        }

        if (query.getContainerEntity().isPresent()) {
            final LazyOptional<TerminalUserProvider> capability = query.getContainerEntity().get()
                .getCapability(Capabilities.terminalUserProvider());
            if (capability.isPresent()) {
                return capability.resolve();
            }
        }

        return Optional.empty();
    }
}
