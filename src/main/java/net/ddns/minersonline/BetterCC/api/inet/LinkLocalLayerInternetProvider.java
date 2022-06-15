package net.ddns.minersonline.BetterCC.api.inet;

import net.ddns.minersonline.BetterCC.common.inet.NullLinkLocalLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An {@link InternetProvider} partial implementation that expects an {@link LinkLocalLayer} implementation from
 * protected method {@link LinkLocalLayerInternetProvider#provideLinkLocalLayer()}.
 *
 * @see InternetProvider
 * @see LinkLocalLayer
 */
public abstract class LinkLocalLayerInternetProvider implements InternetProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * This method is called from {@link LinkLocalLayerInternetProvider#provideInternet()} in order to get an
     * {@link LinkLocalLayer} implementation.
     * If it fails, then a dummy {@link LinkLocalLayer} implementation will be used.
     * Dummy implementation will do nothing.
     *
     * @return an implementation of link local TCP/IP layer for internet cards
     * @throws Exception this method is allowed to fail with an exception
     */
    protected abstract LinkLocalLayer provideLinkLocalLayer() throws Exception;

    @Override
    public final LinkLocalLayer provideInternet() {
        try {
            return provideLinkLocalLayer();
        } catch (Exception e) {
            LOGGER.error("Failed to instantiate the implementation " +
                "of Internet provider for internet card (provider {})", this.getClass(), e);
            return NullLinkLocalLayer.INSTANCE;
        }
    }
}
