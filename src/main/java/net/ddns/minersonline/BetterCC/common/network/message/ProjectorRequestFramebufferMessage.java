/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.common.blockentity.ProjectorBlockEntity;
import net.ddns.minersonline.BetterCC.common.network.MessageUtils;
import net.ddns.minersonline.BetterCC.common.network.ProjectorLoadBalancer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class ProjectorRequestFramebufferMessage extends AbstractMessage {
    private BlockPos pos;

    ///////////////////////////////////////////////////////////////////

    public ProjectorRequestFramebufferMessage(final ProjectorBlockEntity projector) {
        this.pos = projector.getBlockPos();
    }

    public ProjectorRequestFramebufferMessage(final FriendlyByteBuf buffer) {
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
        MessageUtils.withNearbyServerBlockEntity(context, pos, ProjectorBlockEntity.class,
            (player, projector) -> ProjectorLoadBalancer.updateWatcher(projector, player));
    }
}
