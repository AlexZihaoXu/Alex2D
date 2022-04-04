package site.alex_xu.dev.alex2d.sounds;

import site.alex_xu.dev.alex2d.graphics.gl.Freeable;

import static org.lwjgl.openal.AL10.*;

public class SoundSource extends Freeable {
    final int sourceId;
    Sound sound;

    public SoundSource(boolean loop, boolean relative) {
        AudioMaster.init();
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
        if (this.sound != null) {
            this.sound.sources.remove(this);
        }
        sound.sources.add(this);
        alSourcei(sourceId, AL_BUFFER, sound.bufferId);
    }

    public void setPosition(float x, float y, float z) {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }

    public void setVelocity(float x, float y, float z) {
        alSource3f(sourceId, AL_VELOCITY, x, y, z);
    }

    public void setVolume(float volume) {
        alSourcef(sourceId, AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
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

    @Override
    protected void onDispose() {
        freedSoundSources.add(sourceId);
    }

}
