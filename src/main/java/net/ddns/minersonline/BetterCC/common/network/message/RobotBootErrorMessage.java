/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;

public final class RobotBootErrorMessage extends AbstractMessage {
    private int entityId;
    private Component value;

    ///////////////////////////////////////////////////////////////////

    public RobotBootErrorMessage(final Robot robot, @Nullable final Component value) {
        this.entityId = robot.getId();
        this.value = value;
    }

    public RobotBootErrorMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
        value = buffer.readComponent();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeComponent(value);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> robot.getVirtualMachine().setBootErrorClient(value));
    }
}
