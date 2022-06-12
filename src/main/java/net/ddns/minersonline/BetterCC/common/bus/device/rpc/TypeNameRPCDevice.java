/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.rpc;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.rpc.RPCDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.rpc.RPCMethodGroup;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public record TypeNameRPCDevice(String typeName) implements RPCDevice, ItemDevice {
    @Override
    public List<String> getTypeNames() {
        return singletonList(typeName);
    }

    @Override
    public List<RPCMethodGroup> getMethodGroups() {
        return emptyList();
    }
}
