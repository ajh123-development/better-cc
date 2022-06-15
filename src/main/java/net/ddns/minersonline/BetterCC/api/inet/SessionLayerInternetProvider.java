package net.ddns.minersonline.BetterCC.api.inet;

import net.ddns.minersonline.BetterCC.common.inet.DefaultTransportLayer;

/**
 * An {@link InternetProvider} partial implementation that expects an {@link SessionLayer} implementation from
 * protected method {@link SessionLayerInternetProvider#provideSessionLayer()}.
 *
 * @see InternetProvider
 * @see SessionLayer
 */
public abstract class SessionLayerInternetProvider extends TransportLayerInternetProvider {

    /**
     * This method is called from {@link SessionLayerInternetProvider#provideTransportLayer()} in order to get a
     * {@link SessionLayer} implementation.
     * Retrieved {@link SessionLayer} implementation will be wrapped with internal {@link TransportLayer}
     * implementation.
     *
     * @return an implementation of session TCP/IP layer for internet cards
     * @throws Exception this method is allowed to fail with an exception
     */
    protected abstract SessionLayer provideSessionLayer() throws Exception;

    @Override
    protected final TransportLayer provideTransportLayer() throws Exception {
        return new DefaultTransportLayer(provideSessionLayer());
    }
}
