package site.alex_xu.dev.alex2d.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;

public final class Graphics {
    private static boolean initialized = false;
    static Object boundContext = null;

    public static void init() {
        if (initialized) {
            return;
        }
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        initialized = true;
    }
}
