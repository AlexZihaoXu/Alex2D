package site.alex_xu.dev.alex2d.graphics;

import org.lwjgl.opengl.GL;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractWindowI;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow implements AbstractWindowI {

    // Properties

    private String title, _actualTitle;
    private int width, height;
    private boolean isResizable = false;
    private boolean isVisible = false;
    private boolean isVSync = false;

    // Memories

    private long _lastTitleChangeTime = 0;
    private long windowHandle = 0;
    private int x, y;

    // Initializing Window

    private void initWindow() {
        Graphics.init();

        if (windowHandle != 0) {
            glfwHideWindow(windowHandle);
            glfwWindowShouldClose(windowHandle);
            glfwDestroyWindow(windowHandle);
            windowHandle = 0;
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, isResizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, isVisible ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new IllegalStateException("Unable to create window!");
        }

        bindContext();
        GL.createCapabilities();
        setVSyncEnabled(true);
    }

    public GameWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        initWindow();
    }

    public GameWindow(String title) {
        this(title, 674, 480);
    }

    public GameWindow() {
        this("Game Window");
    }

    //

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        throw new NotImplementedException();
        // TODO
    }

    @Override
    public int getY() {
        throw new NotImplementedException();
        // TODO
    }

    @Override
    public void render() {
        glfwPollEvents();
        glfwSwapBuffers(windowHandle);

        long now = System.currentTimeMillis();

        if (!title.equals(_actualTitle) && now - _lastTitleChangeTime > 32) {
            _actualTitle = title;
            _lastTitleChangeTime = now;
            glfwSetWindowTitle(windowHandle, title);
        }

        // TODO
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible != this.isVisible) {
            this.isVisible = visible;
            if (visible)
                glfwShowWindow(windowHandle);
            else
                glfwHideWindow(windowHandle);
        }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setTitle(String title) {
        // TODO: Delay Clock
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setIcon() {
        throw new NotImplementedException();
        // TODO
    }

    @Override
    public void setResizable(boolean resizable) {
        if (resizable != this.isResizable) {
            this.isResizable = resizable;
            this.initWindow();
        }
    }

    @Override
    public boolean isResizable() {
        return isResizable;
    }

    @Override
    public void setVSyncEnabled(boolean enabled) {
        if (enabled != isVSync) {
            this.isVSync = enabled;
            glfwSwapInterval(isVSync ? 1 : 0);
        }
    }

    @Override
    public boolean isVSyncEnabled() {
        return isVSync;
    }

    @Override
    public boolean isAlive() {
        return !glfwWindowShouldClose(windowHandle);
    }

    @Override
    public void close() {
        glfwSetWindowShouldClose(windowHandle, true);
    }

    @Override
    public void free() {
        if (windowHandle != 0) {
            glfwHideWindow(windowHandle);
            glfwWindowShouldClose(windowHandle);
            glfwFreeCallbacks(windowHandle);
            glfwDestroyWindow(windowHandle);
            windowHandle = 0;
        }
        // TODO: MORE
    }

    void bindContext() {
        glfwMakeContextCurrent(windowHandle);
    }
}
