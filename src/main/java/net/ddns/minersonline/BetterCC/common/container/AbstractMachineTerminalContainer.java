/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.container;

import net.ddns.minersonline.BetterCC.common.vm.Terminal;
import net.minecraft.world.inventory.MenuType;

import java.nio.ByteBuffer;

public abstract class AbstractMachineTerminalContainer extends AbstractMachineContainer {
    protected AbstractMachineTerminalContainer(final MenuType<?> type, final int id, final IntPrecisionContainerData energyInfo) {
        super(type, id, energyInfo);
    }

    ///////////////////////////////////////////////////////////////////

    public abstract void switchToTerminal();

    public abstract Terminal getTerminal();

    public abstract void sendTerminalInputToServer(final ByteBuffer input);
}
