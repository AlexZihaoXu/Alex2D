package site.alex_xu.dev.alex2d.system;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Clock {

    private static final long startTime = java.lang.System.nanoTime();

    /**
     * @return Get time elapsed in seconds since program starts
     */
    public static float now() {
        return (java.lang.System.nanoTime() - startTime) / 10e8f;
    }

    private float record = now();

    /**
     * @return time elapsed (in seconds) of this clock since last reset or the creat of the clock instance
     */
    public float elapsedTime() {
        return now() - record;
    }

    /**
     * @return delta time since last reset
     * Reset this clock
     */
    public float reset() {
        float now = now();
        float dt = now - record;
        record = now;
        return dt;
    }

}
