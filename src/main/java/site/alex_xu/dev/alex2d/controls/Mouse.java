package site.alex_xu.dev.alex2d.controls;

import org.joml.Vector2f;
import site.alex_xu.dev.alex2d.graphics.Window;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Mouse {

    private final HashSet<MouseScrollCallbackI> mouseScrollCallbacks = new HashSet<>();
    private final HashSet<MouseButtonCallbackI> mouseDownCallbacks = new HashSet<>();
    private final HashSet<MouseButtonCallbackI> mouseUpCallbacks = new HashSet<>();
    private final HashSet<MouseMoveCallbackI> mouseMoveCallbacks = new HashSet<>();

    private float x;
    private float y;
    private float lastX;
    private float lastY;
    private final boolean[] buttons = new boolean[16];

    public Mouse(Window window) {
        setCallbacks(window);
    }

    public boolean getButton(int index) {
        return buttons[index];
    }

    protected void setCallbacks(Window window) {
        glfwSetCursorPosCallback(window.getHandle(), (window1, xPos, yPos) -> {
            float dtX = (float) (xPos - lastX);
            float dtY = (float) (yPos - lastY);
            x = (float) xPos;
            y = (float) yPos;
            for (MouseMoveCallbackI callback : mouseMoveCallbacks) {
                callback.execute(x, y, dtX, dtY);
            }
            lastX = x;
            lastY = y;
        });
        glfwSetMouseButtonCallback(window.getHandle(), (window1, button, action, mods) -> {
            HashSet<MouseButtonCallbackI> callbacks = action == GLFW_PRESS ? mouseDownCallbacks : mouseUpCallbacks;
            buttons[button] = action == GLFW_PRESS;
            for (MouseButtonCallbackI callback : callbacks) {
                callback.execute(button);
            }
        });
        glfwSetScrollCallback(window.getHandle(), (window1, xOffset, yOffset) -> {
            for (MouseScrollCallbackI callback : mouseScrollCallbacks) {
                callback.execute((float) xOffset, (float) yOffset);
            }
        });
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2f getPos() {
        return new Vector2f(x, y);
    }

    // Mouse Move

    public void addMouseMoveCallback(MouseMoveCallbackI callback) {
        mouseMoveCallbacks.add(callback);
    }

    public void removeMouseMoveCallback(MouseMoveCallbackI callback) {
        mouseMoveCallbacks.remove(callback);
    }

    // Mouse Button

    public void addMouseDownCallback(MouseButtonCallbackI callback) {
        mouseDownCallbacks.add(callback);
    }

    public void removeMouseDownCallback(MouseButtonCallbackI callback) {
        mouseDownCallbacks.remove(callback);
    }

    public void addMouseUpCallback(MouseButtonCallbackI callback) {
        mouseUpCallbacks.add(callback);
    }

    public void removeMouseUpCallback(MouseButtonCallbackI callback) {
        mouseUpCallbacks.remove(callback);
    }

    // Mouse Scroll

    public void addMouseScrollCallback(MouseScrollCallbackI callback) {
        mouseScrollCallbacks.add(callback);
    }

    public void removeMouseScrollCallback(MouseScrollCallbackI callback) {
        mouseScrollCallbacks.remove(callback);
    }

    // Callback Interfaces

    public interface MouseScrollCallbackI {
        void execute(float deltaX, float deltaY);
    }

    public interface MouseButtonCallbackI {
        void execute(int button);
    }

    public interface MouseMoveCallbackI {
        void execute(float x, float y, float deltaX, float deltaY);
    }
}
