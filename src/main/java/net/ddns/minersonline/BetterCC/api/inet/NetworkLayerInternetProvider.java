package net.ddns.minersonline.BetterCC.api.inet;

import net.ddns.minersonline.BetterCC.common.inet.DefaultLinkLocalLayer;

/**
 * An {@link InternetProvider} partial implementation that expects an {@link NetworkLayer} implementation from
 * protected method {@link NetworkLayerInternetProvider#provideNetworkLayer()}.
 *
 * @see InternetProvider
 * @see NetworkLayer
 */
public abstract class NetworkLayerInternetProvider extends LinkLocalLayerInternetProvider {

    /**
     * This method is called from {@link NetworkLayerInternetProvider#provideLinkLocalLayer()} in order to get a
     * {@link NetworkLayer} implementation.
     * Retrieved {@link NetworkLayer} implementation will be wrapped with internal {@link LinkLocalLayer}
     * implementation.
     *
     * @return an implementation of network TCP/IP layer for internet cards
     * @throws Exception this method is allowed to fail with an exception
     */
    protected abstract NetworkLayer provideNetworkLayer() throws Exception;

    @Override
    protected final LinkLocalLayer provideLinkLocalLayer() throws Exception {
        return new DefaultLinkLocalLayer(provideNetworkLayer());
    }
}
