package site.alex_xu.dev.alex2d.graphics;

import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;

import static org.lwjgl.opengl.GL11.*;

abstract class ImageType implements AbstractFrameI {
    private boolean freed = false;
    protected int textureID;
    protected int width, height, channels;

    int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public int getFormat() {
        return channels == 3 ? GL_RGB8 : GL_RGBA8;
    }

    abstract void onDispose();

    void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    boolean isFreed() {
        return freed;
    }

    final void free() {
        if (freed)
            return;
        onDispose();
        freed = true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        free();
    }
}