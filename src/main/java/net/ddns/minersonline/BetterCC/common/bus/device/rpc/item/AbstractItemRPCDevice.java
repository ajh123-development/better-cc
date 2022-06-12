/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.object.ObjectDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.rpc.RPCDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.rpc.RPCMethodGroup;
import net.ddns.minersonline.BetterCC.common.bus.device.util.IdentityProxy;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class AbstractItemRPCDevice extends IdentityProxy<ItemStack> implements RPCDevice, ItemDevice {
    private final ObjectDevice device;

    ///////////////////////////////////////////////////////////////////

    protected AbstractItemRPCDevice(final ItemStack identity, final String typeName) {
        super(identity);
        this.device = new ObjectDevice(this, typeName);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public List<String> getTypeNames() {
        return device.getTypeNames();
    }

    @Override
    public List<RPCMethodGroup> getMethodGroups() {
        return device.getMethodGroups();
    }
}
