package net.ddns.minersonline.better_cc.fabric;

import net.ddns.minersonline.better_cc.BetterCCExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class BetterCCExpectPlatformImpl {
    /**
     * This is our actual method to {@link BetterCCExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
