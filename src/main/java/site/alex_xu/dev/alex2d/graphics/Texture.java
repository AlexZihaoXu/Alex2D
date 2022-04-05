package site.alex_xu.dev.alex2d.graphics;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture extends ImageType {

    private static final HashMap<String, Texture> textureCache = new HashMap<>();

    public static Texture getFromResources(String path) {
        if (!textureCache.containsKey(path)) {
            try {
                textureCache.put(path, new Texture(IOUtils.toByteArray(Objects.requireNonNull(Texture.class.getClassLoader().getResourceAsStream(path)))));
            } catch (IOException e) {
                throw new RuntimeException("Texture file not found: " + path);
            }
        }
        return textureCache.get(path);
    }

    public Texture(byte[] data, int magFilter, int minFilter) {

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer b_width = memoryStack.mallocInt(1);
            IntBuffer b_height = memoryStack.mallocInt(1);
            IntBuffer b_channels = memoryStack.mallocInt(1);
            ByteBuffer b_data = BufferUtils.createByteBuffer(data.length);
            b_data.put(data);
            b_data.flip();

            stbi_set_flip_vertically_on_load(true);
            ByteBuffer b_img = stbi_load_from_memory(b_data, b_width, b_height, b_channels, 0);
            if (b_img == null) {
                throw new IllegalStateException("Unable to load texture.");
            }

            width = b_width.get();
            height = b_height.get();
            channels = b_channels.get();

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 5);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -0.5f);

            stbi_set_flip_vertically_on_load(true);
            glTexImage2D(GL_TEXTURE_2D, 0, getFormat(), width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, b_img);
            glGenerateMipmap(GL_TEXTURE_2D);

            stbi_image_free(b_img);
            b_data.clear();
        }

    }

    public Texture(byte[] data) {
        this(data, GL_NEAREST, GL_LINEAR_MIPMAP_LINEAR);
    }

    @Override
    void onDispose() {
        Graphics.freedTextures.add(textureID);
    }
}

