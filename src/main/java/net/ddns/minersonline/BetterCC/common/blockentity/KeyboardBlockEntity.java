/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.blockentity;

import net.ddns.minersonline.BetterCC.common.bus.device.vm.block.KeyboardDevice;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class KeyboardBlockEntity extends ModBlockEntity {
    private final KeyboardDevice<BlockEntity> keyboardDevice = new KeyboardDevice<>(this);

    ///////////////////////////////////////////////////////////////////

    public KeyboardBlockEntity(final BlockPos pos, final BlockState state) {
        super(BlockEntities.KEYBOARD.get(), pos, state);
    }

    ///////////////////////////////////////////////////////////////////

    public void handleInput(final int keycode, final boolean isDown) {
        keyboardDevice.sendKeyEvent(keycode, isDown);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void collectCapabilities(final CapabilityCollector collector, @Nullable final Direction direction) {
        if (direction == Direction.DOWN) {
            collector.offer(Capabilities.device(), keyboardDevice);
        }
    }
}
