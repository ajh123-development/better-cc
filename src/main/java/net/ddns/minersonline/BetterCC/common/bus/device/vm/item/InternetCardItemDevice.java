package net.ddns.minersonline.BetterCC.common.bus.device.vm.item;

import com.google.common.eventbus.Subscribe;
import li.cil.sedna.device.virtio.VirtIONetworkDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.ItemDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDevice;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.VMDeviceLoadResult;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.context.VMContext;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMResumedRunningEvent;
import net.ddns.minersonline.BetterCC.api.bus.device.vm.event.VMSynchronizeEvent;
import net.ddns.minersonline.BetterCC.common.bus.device.util.IdentityProxy;
import net.ddns.minersonline.BetterCC.common.bus.device.util.OptionalAddress;
import net.ddns.minersonline.BetterCC.common.bus.device.util.OptionalInterrupt;
import net.ddns.minersonline.BetterCC.common.inet.InternetAccess;
import net.ddns.minersonline.BetterCC.common.inet.InternetManager;
import net.ddns.minersonline.BetterCC.common.serialization.NBTSerialization;
import net.ddns.minersonline.BetterCC.common.util.NBTTagIds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
public final class InternetCardItemDevice extends IdentityProxy<ItemStack> implements VMDevice, ItemDevice {
    private static final String DEVICE_TAG_NAME = "device";
    private static final String ADDRESS_TAG_NAME = "address";
    private static final String INTERRUPT_TAG_NAME = "interrupt";

    ///////////////////////////////////////////////////////////////
    private final InternetAccess internetAccess = new InternetAccessImpl();
    private final OptionalAddress address = new OptionalAddress();
    private final OptionalInterrupt interrupt = new OptionalInterrupt();
    private VirtIONetworkDevice device;
    private boolean isRunning;
    private CompoundTag deviceTag;

    ///////////////////////////////////////////////////////////////

    public InternetCardItemDevice(final ItemStack identity) {
        super(identity);
    }

    ///////////////////////////////////////////////////////////////

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

        return VMDeviceLoadResult.success();
    }

    @Override
    public void unmount() {
        dispose();
        isRunning = false;
        address.clear();
        interrupt.clear();
    }

    @Override
    public void dispose() {
        device = null;
    }


    @Subscribe
    public void handlePausingEvent(final VMSynchronizeEvent event) {
        isRunning = false;
    }

    @Subscribe
    public void handleResumingRunningEvent(final VMResumedRunningEvent event) {
        isRunning = true;
        InternetManager.connect(internetAccess);
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

    private class InternetAccessImpl implements InternetAccess {

        @Nullable
        @Override
        public byte[] receiveEthernetFrame() {
            return device.readEthernetFrame();
        }

        @Override
        public void sendEthernetFrame(final byte[] frame) {
            device.writeEthernetFrame(frame);
        }

        @Override
        public boolean isValid() {
            return isRunning && device != null;
        }
    }
}
