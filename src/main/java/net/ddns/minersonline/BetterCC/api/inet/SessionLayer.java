package net.ddns.minersonline.BetterCC.api.inet;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;

/**
 * A session layer interface of TCP/IP stack.
 */
public interface SessionLayer {
    default void receiveSession(final Receiver receiver) {

    }

    default void sendSession(final Session session, @Nullable final ByteBuffer data) {
        session.close();
    }

    interface Receiver {
        @Nullable
        ByteBuffer receive(Session session);
    }
}
