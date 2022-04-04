package site.alex_xu.dev.alex2d.sounds;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioMaster {


    private static boolean initialized = false;
    static long device;
    static long context;

    public static void init() {
        if (initialized)
            return;
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device!");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context!");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        initialized = true;
    }
}
