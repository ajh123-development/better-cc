/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.entity.Robot;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.nio.ByteBuffer;

public final class RobotTerminalInputMessage extends AbstractTerminalEntityMessage {
    public RobotTerminalInputMessage(final Robot robot, final ByteBuffer data) {
        super(robot, data);
    }

    public RobotTerminalInputMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withNearbyServerEntity(context, entityId, Robot.class,
            robot -> robot.getTerminal().putInput(ByteBuffer.wrap(data)));
    }
}
