package site.alex_xu.dev.alex2d.system;

import org.lwjgl.system.Configuration;

public final class System {
    private static boolean initialized = false;
    public static void init() {
        if (initialized)
            return;

        Configuration.STACK_SIZE.set(0x8000000);

        initialized = true;
    }
}
