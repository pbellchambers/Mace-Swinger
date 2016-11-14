
package uk.co.pbellchambers.maceswinger.utils;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.WaveData;
import uk.co.pbellchambers.maceswinger.Resources;

import java.io.BufferedInputStream;

import static org.lwjgl.openal.AL10.*;

public class Sound {

	public static final String[] sounds = {"test","music"};
	public static final int[] soundID = new int[sounds.length];
	public static boolean[] isPlaying = new boolean[sounds.length];
	public static void loadSounds() {
		for (int i = 0; i < sounds.length; i++) {
			WaveData data = null;
			data = WaveData.create(new BufferedInputStream(Resources.get("sound/" + sounds[i] + ".wav")));

			int buffer = alGenBuffers();
			alBufferData(buffer, data.format, data.data, data.samplerate);
			data.dispose();
			soundID[i] = alGenSources();
			alSourcei(soundID[i], AL_BUFFER, buffer);
			alDeleteBuffers(buffer);
		}
	}

	public static void deleteSounds() {
		for (int i = 0; i < sounds.length; i++) {
			alDeleteSources(soundID[i]);
		}
	}

	public static void play(int id, float volume) {
		AL11.alSpeedOfSound(2);
		alSourcef(soundID[id], AL_GAIN, volume);
		alSourcePlay(soundID[id]);
		isPlaying[id]=true;

	}
	public static void pause(int id, float volume) {
		alSourcef(soundID[id], AL_GAIN, volume);
		AL10.alSourcePause(soundID[id]);
		isPlaying[id]=false;
	}

}

