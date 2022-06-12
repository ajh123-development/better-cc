/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc;

import net.ddns.minersonline.BetterCC.api.bus.device.object.Callback;
import net.ddns.minersonline.BetterCC.api.bus.device.object.NamedDevice;
import net.ddns.minersonline.BetterCC.common.bus.device.util.IdentityProxy;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.Collections;

public final class ItemHandlerDevice extends IdentityProxy<IItemHandler> implements NamedDevice {
    public ItemHandlerDevice(final IItemHandler identity) {
        super(identity);
    }

    @Override
    public Collection<String> getDeviceTypeNames() {
        return Collections.singleton("item_handler");
    }

    @Callback
    public int getItemSlotCount() {
        return identity.getSlots();
    }

    @Callback
    public ItemStack getItemStackInSlot(final int slot) {
        return identity.getStackInSlot(slot);
    }

    @Callback
    public int getItemSlotLimit(final int slot) {
        return identity.getSlotLimit(slot);
    }
}
