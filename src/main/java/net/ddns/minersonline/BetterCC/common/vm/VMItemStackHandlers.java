/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.vm;

import net.ddns.minersonline.BetterCC.api.bus.device.DeviceType;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public interface VMItemStackHandlers {
    Optional<IItemHandler> getItemHandler(DeviceType deviceType);

    boolean isEmpty();

    void exportDeviceDataToItemStacks();
}
