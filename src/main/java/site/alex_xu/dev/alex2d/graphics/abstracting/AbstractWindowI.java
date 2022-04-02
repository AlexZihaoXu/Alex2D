package site.alex_xu.dev.alex2d.graphics.abstracting;

import site.alex_xu.dev.alex2d.system.abstracting.AbstractBufferI;

public interface AbstractWindowI extends AbstractBufferI, AbstractPositionI, AbstractFrameI {
    void render();

    // Status

    boolean isFocused();

    // Visibility

    void setVisible(boolean visible);
    boolean isVisible();

    // Title

    void setTitle(String title);
    String getTitle();

    // Icon

    void setIcon(); // TODO: setIcon parameter type undefined

    // Resizable

    void setResizable(boolean resizable);
    boolean isResizable();

    // VSync

    void setVSyncEnabled(boolean enabled);
    boolean isVSyncEnabled();

    // Life

    boolean isAlive();

    void close();
}
