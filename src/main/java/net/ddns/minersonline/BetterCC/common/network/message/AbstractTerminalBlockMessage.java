/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.blockentity.ComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.nio.ByteBuffer;

public abstract class AbstractTerminalBlockMessage extends AbstractMessage {
    protected BlockPos pos;
    protected byte[] data;

    ///////////////////////////////////////////////////////////////////

    protected AbstractTerminalBlockMessage(final ComputerBlockEntity computer, final ByteBuffer data) {
        this.pos = computer.getBlockPos();
        this.data = data.array();
    }

    protected AbstractTerminalBlockMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
        data = buffer.readByteArray();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByteArray(data);
    }
}
