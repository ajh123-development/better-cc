/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.bus.device.vm.item;

import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDeviceLoadResult;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.VMContext;
import net.ddns.minersonline.BetterCC.api.capabilities.NetworkInterface;
import net.ddns.minersonline.BetterCC.common.bus.device.util.IdentityProxy;
import net.ddns.minersonline.BetterCC.common.bus.device.util.OptionalAddress;
import net.ddns.minersonline.BetterCC.common.bus.device.util.OptionalInterrupt;
import net.ddns.minersonline.BetterCC.common.capabilities.Capabilities;
import net.ddns.minersonline.BetterCC.common.serialization.NBTSerialization;
import net.ddns.minersonline.BetterCC.common.util.NBTTagIds;
import li.cil.sedna.device.virtio.VirtIONetworkDevice;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractNetworkInterfaceDevice extends IdentityProxy<ItemStack> implements VMDevice, ItemDevice, ICapabilityProvider {
    private static final String DEVICE_TAG_NAME = "device";
    private static final String ADDRESS_TAG_NAME = "address";
    private static final String INTERRUPT_TAG_NAME = "interrupt";

    ///////////////////////////////////////////////////////////////

    private VirtIONetworkDevice device;
    private final NetworkInterface networkInterface = new NetworkInterfaceImpl();
    private boolean isRunning;

    private final OptionalAddress address = new OptionalAddress();
    private final OptionalInterrupt interrupt = new OptionalInterrupt();
    private CompoundTag deviceTag;

    ///////////////////////////////////////////////////////////////

    protected AbstractNetworkInterfaceDevice(final ItemStack identity) {
        super(identity);
    }

    ///////////////////////////////////////////////////////////////

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> cap, @Nullable final Direction side) {
        if (cap == Capabilities.networkInterface()) {
            return LazyOptional.of(() -> networkInterface).cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public VMDeviceLoadResult mount(final VMContext context) {
        device = new VirtIONetworkDevice(context.getMemoryMap());

        if (!address.claim(context, device)) {
            return VMDeviceLoadResult.fail();
        }

        if (interrupt.claim(context)) {
            device.getInterrupt().set(interrupt.getAsInt(), context.getInterruptController());
        } else {
            return VMDeviceLoadResult.fail();
        }

        if (deviceTag != null) {
            NBTSerialization.deserialize(deviceTag, device);
        }

        context.getEventBus().register(this);

        isRunning = true;

        return VMDeviceLoadResult.success();
    }

    @Override
    public void unmount() {
        device = null;
        isRunning = false;
    }

    @Override
    public void dispose() {
        address.clear();
        interrupt.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();

        if (device != null) {
            deviceTag = NBTSerialization.serialize(device);
        }
        if (deviceTag != null) {
            tag.put(DEVICE_TAG_NAME, deviceTag);
        }
        if (address.isPresent()) {
            tag.putLong(ADDRESS_TAG_NAME, address.getAsLong());
        }
        if (interrupt.isPresent()) {
            tag.putInt(INTERRUPT_TAG_NAME, interrupt.getAsInt());
        }

        return tag;
    }

    @Override
    public void deserializeNBT(final CompoundTag tag) {
        if (tag.contains(DEVICE_TAG_NAME, NBTTagIds.TAG_COMPOUND)) {
            deviceTag = tag.getCompound(DEVICE_TAG_NAME);
        }
        if (tag.contains(ADDRESS_TAG_NAME, NBTTagIds.TAG_LONG)) {
            address.set(tag.getLong(ADDRESS_TAG_NAME));
        }
        if (tag.contains(INTERRUPT_TAG_NAME, NBTTagIds.TAG_INT)) {
            interrupt.set(tag.getInt(INTERRUPT_TAG_NAME));
        }
    }

    ///////////////////////////////////////////////////////////////

    protected NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    ///////////////////////////////////////////////////////////////

    private final class NetworkInterfaceImpl implements NetworkInterface {
        @Override
        public byte[] readEthernetFrame() {
            if (device != null && isRunning) {
                return device.readEthernetFrame();
            } else {
                return null;
            }
        }

        @Override
        public void writeEthernetFrame(final NetworkInterface source, final byte[] frame, final int timeToLive) {
            if (device != null && isRunning) {
                device.writeEthernetFrame(frame);
            }
        }
    }
}
