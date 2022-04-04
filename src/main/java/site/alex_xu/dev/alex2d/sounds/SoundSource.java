package site.alex_xu.dev.alex2d.sounds;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {
    final int sourceId;

    public SoundSource(boolean loop, boolean relative) {
        this.sourceId = alGenSources();
        if (loop) {
            alSourcei(sourceId, AL_LOOPING, AL_TRUE);
        }
        if (relative) {
            alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
        }
    }

    public void setSound(Sound sound) {
        stop();
        alSourcei(sourceId, AL_BUFFER, sound.bufferId);
    }

    public void setPosition(float x, float y, float z) {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }

    public void setVelocity(float x, float y, float z) {
        alSource3f(sourceId, AL_VELOCITY, x, y, z);
    }

    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void play() {
        alSourcePlay(sourceId);
    }

    public void pause() {
        alSourcePause(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public void free() {
        stop();
        alDeleteSources(sourceId);
    }
}
