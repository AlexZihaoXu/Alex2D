package site.alex_xu.dev.alex2d.sounds;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import site.alex_xu.dev.alex2d.Main;
import site.alex_xu.dev.alex2d.graphics.gl.Freeable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Objects;

import static org.lwjgl.openal.AL10.*;

public class Sound extends Freeable {

    final int bufferId;

    private static final HashMap<String, Sound> cache = new HashMap<>();

    public static Sound getFromResources(String path) {
        if (!cache.containsKey(path)) {
            try {
                cache.put(path, new Sound(IOUtils.toByteArray(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(path)))));
            } catch (IOException | NullPointerException e) {
                cache.put(path, null);
            }
        }
        return cache.get(path);
    }

    public Sound(byte[] data) {
        AudioMaster.init();
        bufferId = alGenBuffers();
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            ByteBuffer b_data = BufferUtils.createByteBuffer(data.length);
            b_data.put(data);
            b_data.flip();
            IntBuffer b_channels = memoryStack.mallocInt(1);
            IntBuffer b_sampleRate = memoryStack.mallocInt(1);


            ShortBuffer pcm = STBVorbis.stb_vorbis_decode_memory(b_data, b_channels, b_sampleRate);

            int channels = b_channels.get();
            int sampleRate = b_sampleRate.get();

            assert pcm != null;
            alBufferData(bufferId, channels == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, sampleRate);
            b_data.clear();
            pcm.clear();
        }
    }

    @Override
    protected void onDispose() {
        freedSoundBuffers.add(bufferId);
    }
}
