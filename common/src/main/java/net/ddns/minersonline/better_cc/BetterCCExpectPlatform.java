package net.ddns.minersonline.better_cc;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;

import java.nio.file.Path;

public class BetterCCExpectPlatform {
    /**
     * We can use {@link Platform#getConfigFolder()} but this is just an example of {@link dev.architectury.injectables.annotations.ExpectPlatform}.
     * <p>
     * This must be a public static method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: net.examplemod.ExampleExpectPlatform#getConfigDirectory()
     * Actual Fabric: net.ddns.minersonline.better_cc.fabric.ExpectPlatformImpl#getConfigDirectory()
     * Actual Forge: net.ddns.minersonline.better_cc.forge.ExpectPlatformImpl#getConfigDirectory()
     */
    @dev.architectury.injectables.annotations.ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
