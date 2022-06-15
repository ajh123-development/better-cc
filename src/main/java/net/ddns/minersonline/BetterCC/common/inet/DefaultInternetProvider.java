package net.ddns.minersonline.BetterCC.common.inet;

import net.ddns.minersonline.BetterCC.api.inet.InternetProvider;
import net.ddns.minersonline.BetterCC.api.inet.SessionLayer;
import net.ddns.minersonline.BetterCC.api.inet.SessionLayerInternetProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DefaultInternetProvider extends SessionLayerInternetProvider {
    public static final InternetProvider INSTANCE = new DefaultInternetProvider();
    private static final Logger LOGGER = LogManager.getLogger();

    private DefaultInternetProvider() {
    }

    @Override
    protected SessionLayer provideSessionLayer() {
        return new DefaultSessionLayer();
    }
}
