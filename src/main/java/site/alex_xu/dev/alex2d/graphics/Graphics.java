package site.alex_xu.dev.alex2d.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import site.alex_xu.dev.alex2d.graphics.gl.Freeable;

import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;

public final class Graphics {
    private static boolean initialized = false;
    static Object boundContext = null;
    static LinkedList<Integer> freedTextures = new LinkedList<>();
    static LinkedList<Integer> freedFramebuffers = new LinkedList<>();
    static LinkedList<Integer> freedRenderbuffers = new LinkedList<>();

    public static void init() {
        if (initialized) {
            return;
        }
        System.setProperty("java.awt.headless", "true");
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        initialized = true;
    }

    static void gc() {
        try {
            while (!freedTextures.isEmpty()) {
                glDeleteTextures(freedTextures.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (!freedFramebuffers.isEmpty()){
                glDeleteFramebuffers(freedFramebuffers.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (!freedRenderbuffers.isEmpty()){
                glDeleteRenderbuffers(freedRenderbuffers.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Freeable.gc();
    }
}
