/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.bus.CommonDeviceBusController;
import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.ddns.minersonline.BetterCC.common.serialization.NBTSerialization;
import net.ddns.minersonline.BetterCC.common.vm.VMRunState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

public final class RobotInitializationMessage extends AbstractMessage {
    private int entityId;
    private CommonDeviceBusController.BusState busState;
    private VMRunState runState;
    private Component bootError;
    private CompoundTag terminal;

    ///////////////////////////////////////////////////////////////////

    public RobotInitializationMessage(final Robot robot) {
        this.entityId = robot.getId();
        this.busState = robot.getVirtualMachine().getBusState();
        this.runState = robot.getVirtualMachine().getRunState();
        this.bootError = robot.getVirtualMachine().getBootError();
        this.terminal = NBTSerialization.serialize(robot.getTerminal());
    }

    public RobotInitializationMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
        busState = buffer.readEnum(CommonDeviceBusController.BusState.class);
        runState = buffer.readEnum(VMRunState.class);
        bootError = buffer.readComponent();
        terminal = buffer.readNbt();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnum(busState);
        buffer.writeEnum(runState);
        buffer.writeComponent(bootError);
        buffer.writeNbt(terminal);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> {
                robot.getVirtualMachine().setBusStateClient(busState);
                robot.getVirtualMachine().setRunStateClient(runState);
                robot.getVirtualMachine().setBootErrorClient(bootError);
                NBTSerialization.deserialize(terminal, robot.getTerminal());
            });
    }
}
