package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.InternetProvider;
import net.ddns.minersonline.BetterCC.api.inet.LinkLocalLayer;
import net.ddns.minersonline.BetterCC.common.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public final class InternetManager {
    private static final Logger LOGGER = LogManager.getLogger();

    //////////////////////////////////////////////////////////////

    private static final InternetProvider internetProvider;
    private static final Map<InternetAccess, PendingFrames> internetAccesses = new HashMap<>();

    //////////////////////////////////////////////////////////////
    private static final List<Runnable> actions = new ArrayList<>();
    private static Executor executor;
    private static Ipv4Space ipSpace;

    static {
        ServiceLoader<InternetProvider> serviceLoader = ServiceLoader.load(InternetProvider.class);
        Iterator<InternetProvider> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            internetProvider = iterator.next();
        } else {
            internetProvider = DefaultInternetProvider.INSTANCE;
        }
    }

    public static void initialize() {
        if (!Config.internetCardEnabled) {
            LOGGER.info("Internet card is disabled; Internet manager will not start");
            return;
        }
        LOGGER.warn("Internet card is enabled; Players may access to the internal network");
        executor = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, "Internet"));
        ipSpace = InetUtils.computeIpSpace(Config.deniedHosts, Config.allowedHosts);
        MinecraftForge.EVENT_BUS.register(InternetManager.class);
    }

    public static void connect(final InternetAccess internetAccess) {
        if (!Config.internetCardEnabled) {
            return;
        }
        if (internetAccesses.containsKey(internetAccess)) {
            return;
        }
        internetAccesses.put(internetAccess, new PendingFrames(internetProvider.provideInternet()));
    }

    private static void runCatching(final Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            LOGGER.error("Uncaught exception", e);
        }
    }

    private static void processInternetAccess(final Map.Entry<InternetAccess, PendingFrames> entry) {
        final PendingFrames frames = entry.getValue();
        final InternetAccess access = entry.getKey();
        final byte[] received = frames.incoming.get();
        if (received != null) {
            access.sendEthernetFrame(received);
        }
        final byte[] sending = access.receiveEthernetFrame();
        if (sending != null) {
            frames.outcoming.put(sending);
        }
        executor.execute(frames);
    }

    public static void registerNetworkThreadAction(final Runnable action) {
        if (!Config.internetCardEnabled) {
            return;
        }
        actions.add(action);
    }

    public static boolean isAllowedToConnect(final int ipAddress) {
        return ipSpace.isAllowed(ipAddress);
    }

    //////////////////////////////////////////////////////////////

    @SubscribeEvent
    public static void onTick(final TickEvent.ServerTickEvent event) {
        executor.execute(() -> actions.forEach(InternetManager::runCatching));
        final Iterator<Map.Entry<InternetAccess, PendingFrames>> iterator = internetAccesses.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<InternetAccess, PendingFrames> entry = iterator.next();
            if (entry.getKey().isValid()) {
                processInternetAccess(entry);
            } else {
                iterator.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onStopping(final ServerStoppingEvent event) {
        internetAccesses.clear();
    }

    //////////////////////////////////////////////////////////////

    private static final class PendingFrames implements Runnable {

        public final PendingFrame incoming = new PendingFrame();
        public final PendingFrame outcoming = new PendingFrame();

        private final ByteBuffer receiveBuffer = ByteBuffer.allocate(LinkLocalLayer.FRAME_SIZE);
        private final LinkLocalLayer ethernet;

        //////////////////////////////////////////////////////////////

        public PendingFrames(final LinkLocalLayer ethernet) {
            this.ethernet = ethernet;
        }

        @Override
        public void run() {
            try {
                final byte[] outFrame = outcoming.get();
                if (outFrame != null) {
                    ethernet.sendEthernetFrame(ByteBuffer.wrap(outFrame));
                }
                receiveBuffer.clear();
                if (ethernet.receiveEthernetFrame(receiveBuffer)) {
                    final byte[] inFrame = new byte[receiveBuffer.remaining()];
                    receiveBuffer.get(inFrame);
                    incoming.put(inFrame);
                }
            } catch (Exception e) {
                LOGGER.error("Uncaught exception", e);
            }
        }

        //////////////////////////////////////////////////////////////

        private static final class PendingFrame {

            private final AtomicReference<byte[]> pendingFrame = new AtomicReference<>();

            //////////////////////////////////////////////////////////////

            @Nullable
            public byte[] get() {
                return pendingFrame.getAndSet(null);
            }

            public void put(final byte[] frame) {
                pendingFrame.set(frame);
            }
        }
    }
}
