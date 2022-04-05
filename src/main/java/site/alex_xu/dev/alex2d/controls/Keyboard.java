package site.alex_xu.dev.alex2d.controls;

import site.alex_xu.dev.alex2d.graphics.Window;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Keyboard {

    private final HashSet<KeyCallbackI> keyPressCallbacks = new HashSet<>();
    private final HashSet<KeyCallbackI> keyReleaseCallbacks = new HashSet<>();
    private final HashSet<KeyTypeCallbackI> keyTypeCallbacks = new HashSet<>();

    private final Keys keys = new Keys();

    public Keyboard(Window window) {
        setCallbacks(window);
    }

    public Keys keys() {
        return keys;
    }

    protected void setCallbacks(Window window) {
        glfwSetKeyCallback(window.getHandle(), (window1, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keys.updateKeys(key, true);
                for (KeyCallbackI callback : keyPressCallbacks) {
                    callback.execute(key, Keys.getName(key));
                }
            } else if (action == GLFW_RELEASE) {
                keys.updateKeys(key, false);
                for (KeyCallbackI callback : keyReleaseCallbacks) {
                    callback.execute(key, Keys.getName(key));
                }
            }
        });
        glfwSetCharCallback(window.getHandle(), (window1, codepoint) -> {
            for (KeyTypeCallbackI callback : keyTypeCallbacks) {
                callback.execute((char) codepoint);
            }
        });
    }

    // Add / Remove callbacks

    public void addKeyPressCallback(KeyCallbackI callback) {
        keyPressCallbacks.add(callback);
    }

    public void removeKeypressCallback(KeyCallbackI callback) {
        keyPressCallbacks.remove(callback);
    }

    public void addKeyReleaseCallback(KeyCallbackI callback) {
        keyReleaseCallbacks.add(callback);
    }

    public void removeKeyReleaseCallback(KeyCallbackI callback) {
        keyReleaseCallbacks.remove(callback);
    }

    public void addKeyTypeCallback(KeyTypeCallbackI callback) {
        keyTypeCallbacks.add(callback);
    }

    public void removeKeyTypeCallback(KeyTypeCallbackI callback) {
        keyTypeCallbacks.remove(callback);
    }

    // Callbacks

    public interface KeyCallbackI {
        void execute(int key, String keyName);
    }

    public interface KeyTypeCallbackI {
        void execute(char character);
    }


}
