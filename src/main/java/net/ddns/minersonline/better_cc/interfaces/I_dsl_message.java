package net.ddns.minersonline.better_cc.interfaces;

public interface I_dsl_message {
    String[] receive();
    String send(String destIP, String message);
}


