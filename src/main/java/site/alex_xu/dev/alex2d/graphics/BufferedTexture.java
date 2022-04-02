package site.alex_xu.dev.alex2d.graphics;

import site.alex_xu.dev.alex2d.graphics.abstracting.AbstractFrameI;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL30.*;

public class BufferedTexture extends ImageType {
    int frameBufferID;

    public BufferedTexture(int width, int height) {
        this.width = width;
        this.height = height;
        this.channels = 4;

        frameBufferID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);

        textureID = glGenTextures();
        if (textureID == 0) {
            throw new IllegalStateException("Unable to create texture for framebuffer!");
        }
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException("Unable to create framebuffer!");
        }

        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    void bindContext() {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
        glViewport(0, 0, width, height);
        Graphics.boundContext = this;
    }

    void unbindContext() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void onDispose() {
        Graphics.freedTextures.add(textureID);
        Graphics.freedFramebuffers.add(frameBufferID);
    }
}
