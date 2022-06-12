/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.blockentity.ComputerBlockEntity;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class OpenComputerTerminalMessage extends AbstractMessage {
    private BlockPos pos;

    ///////////////////////////////////////////////////////////////////

    public OpenComputerTerminalMessage(final ComputerBlockEntity computer) {
        this.pos = computer.getBlockPos();
    }

    public OpenComputerTerminalMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, ComputerBlockEntity.class,
            (player, computer) -> computer.openTerminalScreen(player));
    }
}
