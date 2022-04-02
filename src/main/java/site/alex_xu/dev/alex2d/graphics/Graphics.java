package site.alex_xu.dev.alex2d.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import site.alex_xu.dev.alex2d.graphics.gl.Freeable;

import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;

public final class Graphics {
    private static boolean initialized = false;
    static Object boundContext = null;
    static LinkedList<Integer> freedTextures = new LinkedList<>();
    static LinkedList<Integer> freedFramebuffers = new LinkedList<>();

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

    static void gc() {
        while (!freedTextures.isEmpty()) {
            glDeleteTextures(freedTextures.pop());
        }
        while (!freedFramebuffers.isEmpty()){
            glDeleteFramebuffers(freedFramebuffers.pop());
        }
        Freeable.gc();
    }
}
