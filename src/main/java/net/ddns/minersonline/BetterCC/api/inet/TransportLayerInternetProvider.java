package net.ddns.minersonline.BetterCC.api.inet;

import net.ddns.minersonline.BetterCC.common.inet.DefaultNetworkLayer;

/**
 * An {@link InternetProvider} partial implementation that expects an {@link TransportLayer} implementation from
 * protected method {@link TransportLayerInternetProvider#provideTransportLayer()}.
 *
 * @see InternetProvider
 * @see TransportLayer
 */
public abstract class TransportLayerInternetProvider extends NetworkLayerInternetProvider {

    /**
     * This method is called from {@link TransportLayerInternetProvider#provideNetworkLayer()} in order to get a
     * {@link TransportLayer} implementation.
     * Retrieved {@link TransportLayer} implementation will be wrapped with internal {@link NetworkLayer}
     * implementation.
     *
     * @return an implementation of transport TCP/IP layer for internet cards
     * @throws Exception this method is allowed to fail with an exception
     */
    protected abstract TransportLayer provideTransportLayer() throws Exception;

    @Override
    protected final NetworkLayer provideNetworkLayer() throws Exception {
        return new DefaultNetworkLayer(provideTransportLayer());
    }
}
