package net.ddns.minersonline.BetterCC.api.inet;

public interface EchoSession extends Session {
    int getSequenceNumber();

    int getTtl();
}
