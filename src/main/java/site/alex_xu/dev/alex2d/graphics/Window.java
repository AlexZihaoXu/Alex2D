package site.alex_xu.dev.alex2d.graphics;

import org.apache.commons.io.IOUtils;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import site.alex_xu.dev.alex2d.controls.Keyboard;
import site.alex_xu.dev.alex2d.controls.Mouse;
import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractWindowI;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * A window class that can render some stuffs
 */
public class Window implements AbstractWindowI {

    private static class WindowMouse extends Mouse {
        public WindowMouse(Window window) {
            super(window);
        }
        public void resetCallbacks(Window window) {
            setCallbacks(window);
        }
    }

    private static class WindowKeyboard extends Keyboard {
        public WindowKeyboard(Window window) {
            super(window);
        }

        void resetCallbacks(Window window) {
            setCallbacks(window);
        }
    }

    // Properties

    private String title, _actualTitle;
    private int width, height;
    private boolean isResizable = false;
    private boolean isVisible = false;
    private boolean isVSync = false;
    private boolean isIconified = false;
    private boolean isFocused = false;

    // Memories

    private Mouse mouse;
    private Keyboard keyboard;
    private long _lastTitleChangeTime = 0;
    private long windowHandle = 0;
    private int x, y;

    // Cache

    // Initializing Window

    /**
     * Used to create window or change window status.
     */
    private void initWindow() {
        Graphics.init();

        free();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, isResizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, isVisible ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 1);
        glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new IllegalStateException("Unable to create window!");
        }

        bindContext();
        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        setVSyncEnabled(isVSync);
        glfwSwapInterval(isVSync ? 1 : 0);

        glfwSetWindowSizeLimits(windowHandle, 250, 250, GLFW_DONT_CARE, GLFW_DONT_CARE);
        glfwSetFramebufferSizeCallback(windowHandle, (window, x, y) -> {
            this.x = x;
            this.y = y;
        });
        glfwSetWindowSizeCallback(windowHandle, (window, w, h) -> {
            this.width = w;
            this.height = h;
        });
        glfwSetWindowIconifyCallback(windowHandle, (window, iconified) -> {
            this.isIconified = iconified;
        });
        glfwSetWindowFocusCallback(windowHandle, (window, focused) -> {
            isFocused = focused;
        });

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (mouse == null) {
            mouse = new WindowMouse(this);
        } else {
            ((WindowMouse) mouse).resetCallbacks(this);
        }

        if (keyboard == null) {
            keyboard = new WindowKeyboard(this);
        } else {
            ((WindowKeyboard) keyboard).resetCallbacks(this);
        }
    }


    public void dispose() {
        if (disposed)
            return;

        glfwFreeCallbacks(windowHandle);
        if (windowHandle != 0)
            glfwDestroyWindow(windowHandle);
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        glfwTerminate();

        disposed= true;
    }

    /**
     * @param title  title for the window.
     * @param width  width of the window.
     * @param height height of the window.
     */
    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        initWindow();
    }

    /**
     * @param title title for the window.
     *              Create a Game Window with default size of 647x480.
     */
    public Window(String title) {
        this(title, 674, 480);
    }

    /**
     * Create a Game Window with default settings:
     * Title: Game Window
     * Size:  647x480
     */
    public Window() {
        this("Game Window");
    }

    //

    /**
     * @return the window's width.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * @return the window's height.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * @return the window's top left x-position.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @return the window's top left y-position.
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Update the content of the window.
     */
    @Override
    public void render() {
        Graphics.gc();

        glfwSwapBuffers(windowHandle);
        glfwPollEvents();

        long now = System.currentTimeMillis();

        if (!title.equals(_actualTitle) && now - _lastTitleChangeTime > 32) {
            _actualTitle = title;
            _lastTitleChangeTime = now;
            glfwSetWindowTitle(windowHandle, title);
        }

    }

    /**
     * @return true if the window is focused otherwise false
     */
    @Override
    public boolean isFocused() {
        return isFocused;
    }

    /**
     * @param visible visible.
     *                Set window's visibility.
     */
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

    /**
     * @return true when the window is visible otherwise false.
     */
    @Override
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * @param title new title for the window.
     *              Set a new title for the window.
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the window's title.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Set an icon for this window.
     */
    @Override
    public void setIcon(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            byte[] bytes = IOUtils.toByteArray(Objects.requireNonNull(Window.class.getClassLoader().getResourceAsStream(path)));
            ByteBuffer data = stack.malloc(bytes.length);
            data.put(bytes);
            data.flip();


            stbi_set_flip_vertically_on_load(false);
            ByteBuffer image = stbi_load_from_memory(data, w, h, comp, 4);
            GLFWImage img = GLFWImage.malloc();
            assert image != null;
            img.set(w.get(), h.get(), image);
            GLFWImage.Buffer imagesBuf = GLFWImage.malloc(1);
            imagesBuf.put(0, img);
            glfwSetWindowIcon(windowHandle, imagesBuf);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find icon file: " + path);
        }
    }

    /**
     * @param resizable resizable
     *                  Set whether this window should be resizable or not.
     */
    @Override
    public void setResizable(boolean resizable) {
        if (resizable != this.isResizable) {
            this.isResizable = resizable;
            this.initWindow();
        }
    }

    /**
     * @return true if this window is resizable otherwise false.
     */
    @Override
    public boolean isResizable() {
        return isResizable;
    }

    /**
     * @param enabled VSync Enabled
     *                Set whether enable VSync or not.
     *                Used to limit the framerate to monitor's fresh rate and prevent tearing.
     */
    @Override
    public void setVSyncEnabled(boolean enabled) {
        if (enabled != isVSync) {
            this.isVSync = enabled;
            glfwSwapInterval(isVSync ? 1 : 0);
        }
    }

    /**
     * @return true if VSync is enabled otherwise false.
     */
    @Override
    public boolean isVSyncEnabled() {
        return isVSync;
    }

    /**
     * @return true if this window is still alive (not closed) otherwise false.
     */
    @Override
    public boolean isAlive() {
        return !glfwWindowShouldClose(windowHandle);
    }

    /**
     * Close this window.
     */
    @Override
    public void close() {
        glfwSetWindowShouldClose(windowHandle, true);
    }

    /**
     * Clean up window's data
     */
    @Override
    @Deprecated
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

    /**
     * Bind the OpenGL Context of this window
     */
    void bindContext() {
        if (Graphics.boundContext != this) {
            if (Graphics.boundContext instanceof BufferedTexture) {
                ((BufferedTexture) Graphics.boundContext).unbindContext();
            }
            glfwMakeContextCurrent(windowHandle);
            Graphics.boundContext = this;
        }
    }

    public long getHandle() {
        return windowHandle;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    private boolean disposed = false;
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        dispose();
    }
}
