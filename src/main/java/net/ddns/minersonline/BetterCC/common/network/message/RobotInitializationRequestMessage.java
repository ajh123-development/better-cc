/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class RobotInitializationRequestMessage extends AbstractMessage {
    private int entityId;

    ///////////////////////////////////////////////////////////////////

    public RobotInitializationRequestMessage(final Robot robot) {
        this.entityId = robot.getId();
    }

    public RobotInitializationRequestMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withServerEntity(context, entityId, Robot.class,
            robot -> reply(new RobotInitializationMessage(robot), context));
    }
}
