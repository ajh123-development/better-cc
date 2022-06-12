/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.blockentity.ComputerBlockEntity;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.ddns.minersonline.BetterCC.common.vm.VMRunState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class ComputerRunStateMessage extends AbstractMessage {
    private BlockPos pos;
    private VMRunState value;

    ///////////////////////////////////////////////////////////////////

    public ComputerRunStateMessage(final ComputerBlockEntity computer, final VMRunState value) {
        this.pos = computer.getBlockPos();
        this.value = value;
    }

    public ComputerRunStateMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
        value = buffer.readEnum(VMRunState.class);
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnum(value);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        MessageUtils.withClientBlockEntityAt(pos, ComputerBlockEntity.class,
            computer -> computer.getVirtualMachine().setRunStateClient(value));
    }
}
