/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.bus.CommonDeviceBusController;
import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class RobotBusStateMessage extends AbstractMessage {
    private int entityId;
    private CommonDeviceBusController.BusState value;

    ///////////////////////////////////////////////////////////////////

    public RobotBusStateMessage(final Robot robot, final CommonDeviceBusController.BusState value) {
        this.entityId = robot.getId();
        this.value = value;
    }

    public RobotBusStateMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
        value = buffer.readEnum(CommonDeviceBusController.BusState.class);
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnum(value);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> robot.getVirtualMachine().setBusStateClient(value));
    }
}
