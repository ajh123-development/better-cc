package net.ddns.minersonline.better_cc.forge;

import net.ddns.minersonline.better_cc.BetterCCExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class BetterCCExpectPlatformImpl {
    /**
     * This is our actual method to {@link BetterCCExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
