package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SELoader {
	public static String Bounce = "soundeffects/Bounce.wav";
	public static String Fall = "soundeffects/Fall.wav";
	public static String Fire = "soundeffects/Fire.wav";
	public static String Click = "soundeffects/Click.wav";
	public static String Inaccessible = "soundeffects/Inaccessible.wav";
	public static String Collision = "soundeffects/Collision.wav";
	public static String FireCollision = "soundeffects/FireCollision.wav";
	public static String GameOverSE = "soundeffects/GameOverSE.wav";
	public static String Long = "soundeffects/Long.wav";
	public static String Destroyed = "soundeffects/Destroyed.wav";
	public static String Double = "soundeffects/Double.wav";
	public static String Win = "soundeffects/Win.wav";
	static Boolean muteSE = false;
	static Clip fx;

	public SELoader() {

	}

	public static void SE(String BgmLocation) {
		if (!muteSE) {
			try {
				File musicPath = new File(BgmLocation);

				if (musicPath.exists()) {
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					fx = AudioSystem.getClip();
					fx.open(audioInput);
					fx.start();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void MuteSE() {
		muteSE = true;
	}

	public static void UnmuteBGM() {
		muteSE = false;
	}
}
