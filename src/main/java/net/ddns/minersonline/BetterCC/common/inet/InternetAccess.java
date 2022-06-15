package net.ddns.minersonline.BetterCC.common.inet;

import javax.annotation.Nullable;

public interface InternetAccess {
    boolean isValid();

    @Nullable
    byte[] receiveEthernetFrame();

    void sendEthernetFrame(byte[] frame);
}
