/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.vm.item;

import net.ddns.minersonline.BetterCC.common.serialization.BlobStorage;
import net.ddns.minersonline.BetterCC.common.util.BlockLocation;
import net.ddns.minersonline.BetterCC.common.util.SoundEvents;
import net.ddns.minersonline.BetterCC.common.util.ThrottledSoundEmitter;
import li.cil.sedna.device.block.ByteBufferBlockDevice;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class HardDriveDevice extends AbstractBlockStorageDevice<ByteBufferBlockDevice, ItemStack> {
    private final int size;
    private final ThrottledSoundEmitter soundEmitter;

    ///////////////////////////////////////////////////////////////////

    public HardDriveDevice(final ItemStack identity, final int size, final boolean readonly, final Supplier<Optional<BlockLocation>> location) {
        super(identity, readonly);
        this.size = size;
        this.soundEmitter = new ThrottledSoundEmitter(location, SoundEvents.HDD_ACCESS.get())
            .withMinInterval(Duration.ofSeconds(1));
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected CompletableFuture<ByteBufferBlockDevice> createBlockDevice() {
        blobHandle = BlobStorage.validateHandle(blobHandle);

        return CompletableFuture.supplyAsync(() -> {
            try {
                final FileChannel channel = BlobStorage.getOrOpen(blobHandle);
                final MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
                return ByteBufferBlockDevice.wrap(buffer, readonly);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }, WORKERS);
    }

    @Override
    protected void handleDataAccess() {
        soundEmitter.play();
    }
}
