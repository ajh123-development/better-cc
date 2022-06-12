/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.container;

import net.ddns.minersonline.BetterCC.common.bus.CommonDeviceBusController;
import net.ddns.minersonline.BetterCC.common.energy.FixedEnergyStorage;
import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.Network;
import net.ddns.minersonline.BetterCC.common.network.message.OpenRobotInventoryMessage;
import net.ddns.minersonline.BetterCC.common.network.message.OpenRobotTerminalMessage;
import net.ddns.minersonline.BetterCC.common.network.message.RobotPowerMessage;
import net.ddns.minersonline.BetterCC.common.network.message.RobotTerminalInputMessage;
import net.ddns.minersonline.BetterCC.common.vm.Terminal;
import net.ddns.minersonline.BetterCC.common.vm.VirtualMachine;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

import java.nio.ByteBuffer;

public abstract class AbstractRobotContainer extends AbstractMachineTerminalContainer {
    private final Robot robot;

    ///////////////////////////////////////////////////////////////////

    public AbstractRobotContainer(final MenuType<?> type, final int id, final Player player, final Robot robot, final IntPrecisionContainerData energyInfo) {
        super(type, id, energyInfo);
        this.robot = robot;

        this.robot.addTerminalUser(player);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void switchToInventory() {
        Network.sendToServer(new OpenRobotInventoryMessage(robot));
    }

    @Override
    public void switchToTerminal() {
        Network.sendToServer(new OpenRobotTerminalMessage(robot));
    }

    public Robot getRobot() {
        return robot;
    }

    @Override
    public VirtualMachine getVirtualMachine() {
        return robot.getVirtualMachine();
    }

    @Override
    public void sendPowerStateToServer(final boolean value) {
        Network.sendToServer(new RobotPowerMessage(robot, value));
    }

    @Override
    public Terminal getTerminal() {
        return robot.getTerminal();
    }

    @Override
    public void sendTerminalInputToServer(final ByteBuffer input) {
        Network.sendToServer(new RobotTerminalInputMessage(robot, input));
    }

    @Override
    public boolean stillValid(final Player player) {
        return robot.isAlive() && robot.closerThan(player, 8);
    }

    @Override
    public void removed(final Player player) {
        super.removed(player);

        this.robot.removeTerminalUser(player);
    }

    ///////////////////////////////////////////////////////////////////

    protected static IntPrecisionContainerData createEnergyInfo(final FixedEnergyStorage energy, final CommonDeviceBusController busController) {
        return new IntPrecisionContainerData.Server() {
            @Override
            public int getInt(final int index) {
                return switch (index) {
                    case AbstractMachineContainer.ENERGY_STORED_INDEX -> energy.getEnergyStored();
                    case AbstractMachineContainer.ENERGY_CAPACITY_INDEX -> energy.getMaxEnergyStored();
                    case AbstractMachineContainer.ENERGY_CONSUMPTION_INDEX -> busController.getEnergyConsumption();
                    default -> 0;
                };
            }

            @Override
            public int getIntCount() {
                return ENERGY_INFO_SIZE;
            }
        };
    }
}
