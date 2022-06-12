/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.blockentity.ComputerBlockEntity;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.nio.ByteBuffer;

public final class ComputerTerminalInputMessage extends AbstractTerminalBlockMessage {
    public ComputerTerminalInputMessage(final ComputerBlockEntity computer, final ByteBuffer data) {
        super(computer, data);
    }

    public ComputerTerminalInputMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, ComputerBlockEntity.class,
            (player, computer) -> computer.getTerminal().putInput(ByteBuffer.wrap(data)));
    }
}
