/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class RobotPowerMessage extends AbstractMessage {
    private int entityId;
    private boolean power;

    ///////////////////////////////////////////////////////////////////

    public RobotPowerMessage(final Robot robot, final boolean power) {
        this.entityId = robot.getId();
        this.power = power;
    }

    public RobotPowerMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
        power = buffer.readBoolean();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeBoolean(power);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withNearbyServerEntity(context, entityId, Robot.class,
            robot -> {
                if (power) {
                    robot.start();
                } else {
                    robot.stop();
                }
            });
    }
}
