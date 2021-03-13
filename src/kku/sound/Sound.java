package kku.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import kku.graphics.GameEngine;

public class Sound extends GameEngine{
	
	public static AudioInputStream Music = null;
	public static AudioInputStream ingameMusic = null;
	public static AudioInputStream beamEffect = null;
	
	public static long timePausedm = 0; 
	public static int mMusicLoopStart = 1188070;
	public static int mMusicLoopEnd = 6891768;
	
	
	public static Clip m = null;
	public static Clip beam = null;
	
	public static void playBeamEffect () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		beamEffect = AudioSystem.getAudioInputStream(Sound.class.getResource("/soundResource/beameffect.wav"));
		beam = AudioSystem.getClip();
		beam.open(beamEffect);
		beam.start();
	}
	
	public static void playMusic (int play) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (play == 0) {
			Music = AudioSystem.getAudioInputStream(Sound.class.getResource("/soundResource/music.wav"));
			m = AudioSystem.getClip();
			m.open(Music);
			m.setLoopPoints(mMusicLoopStart, mMusicLoopEnd);
			m.loop(Clip.LOOP_CONTINUOUSLY);
			m.start();
		} else if (play == 1) {
			timePausedm = m.getMicrosecondPosition();
			m.stop();
		} else if (play == -1) {
			m.stop();
		} else if (play == 2) {
			m.setMicrosecondPosition(timePausedm);
			m.setLoopPoints(mMusicLoopStart, mMusicLoopEnd);
			m.loop(Clip.LOOP_CONTINUOUSLY);
			m.start();
		}
		
	}
}
