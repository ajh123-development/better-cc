/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.vm.item;

import com.google.common.eventbus.Subscribe;
import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.data.Firmware;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.FirmwareLoader;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDeviceLoadResult;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.VMContext;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMInitializationException;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMInitializingEvent;
import net.ddns.minersonline.BetterCC.common.Constants;
import net.ddns.minersonline.BetterCC.common.bus.device.util.IdentityProxy;
import li.cil.sedna.api.memory.MemoryMap;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public final class FirmwareFlashStorageDevice extends IdentityProxy<ItemStack> implements VMDevice, ItemDevice, FirmwareLoader {
    private final Firmware firmware;
    private MemoryMap memoryMap;

    ///////////////////////////////////////////////////////////////

    public FirmwareFlashStorageDevice(final ItemStack identity, final Firmware firmware) {
        super(identity);
        this.firmware = firmware;
    }

    ///////////////////////////////////////////////////////////////

    @Override
    public VMDeviceLoadResult mount(final VMContext context) {
        memoryMap = context.getMemoryMap();

        context.getEventBus().register(this);

        return VMDeviceLoadResult.success();
    }

    @Override
    public void unmount() {
        memoryMap = null;
    }

    @Override
    public void dispose() {
    }

    @Subscribe
    public void handleInitializingEvent(final VMInitializingEvent event) {
        copyDataToMemory(event.programStartAddress());
    }

    ///////////////////////////////////////////////////////////////

    private void copyDataToMemory(final long address) {
        if (!firmware.run(memoryMap, address)) {
            throw new VMInitializationException(new TranslatableComponent(Constants.COMPUTER_ERROR_INSUFFICIENT_MEMORY));
        }
    }
}
