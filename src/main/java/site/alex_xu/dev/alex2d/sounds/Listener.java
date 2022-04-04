package site.alex_xu.dev.alex2d.sounds;

import static org.lwjgl.openal.AL10.*;

public class Listener {
    float x, y, z;
    private static Listener instance = null;

    public static Listener get() {
        if (instance == null)
            instance = new Listener(0, 0, 0);
        return instance;
    }
    private Listener(float x, float y, float z) {
        AudioMaster.init();
        this.x = x;
        this.y = y;
        this.z = z;
        alListener3f(AL_POSITION, x, y, z);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public void setVelocity(float x, float y, float z) {
        alListener3f(AL_VELOCITY, x, y, z);
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        alListener3f(AL_POSITION, x, y, z);
    }

    public void setOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ) {
        alListenerfv(AL_ORIENTATION, new float[]{
                atX, atY, atZ,
                upX, upY, upZ
        });
    }
}
