package main;

import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Create background music 
public class BgmLoader {
	public static String MainMenuBGM = "bgm/MainMenuBGM.wav";
	public static String LevelMenuBGM = "bgm/LevelMenuBGM.wav";
	public static String GameBGM = "bgm/GameBGM.wav";
	public static String GameOverBGM = "bgm/GameOverBGM.wav";
	public static String WinBGM = "bgm/WinBGM.wav";
	public static String Lvl0_BGM = "bgm/Lvl0_BGM.wav";
	public static String Lvl1_BGM = "bgm/Lvl1_BGM.wav";
	public static String Lvl2_BGM = "bgm/Lvl2_BGM.wav";
	public static String Lvl3_BGM = "bgm/Lvl3_BGM.wav";
	public static String Lvl4_BGM = "bgm/Lvl4_BGM.wav";
	public static String Lvl5_BGM = "bgm/Lvl5_BGM.wav";
	public static String Lvl6_BGM = "bgm/Lvl6_BGM.wav";
	public static String Lvl7_BGM = "bgm/Lvl7_BGM.wav";
	static Clip bgm;
	static long pause;
	static Boolean muteBGM = false;

	public BgmLoader() {

	}

	public static void BGM(String BgmLocation) { // Create BGM
		try {
			File musicPath = new File(BgmLocation);

			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				bgm = AudioSystem.getClip();
				bgm.open(audioInput);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void PlayBGM() { // Play
		if (!muteBGM) {
			bgm.setMicrosecondPosition(pause);
			bgm.start();
			bgm.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public static void StopBGM() { //Stop
		pause = 0;
		bgm.stop();
	}

	public static void MuteBGM() { //Mute
		pause = bgm.getMicrosecondPosition();
		muteBGM = true;
		bgm.stop();
	}

	public static void UnmuteBGM() { //Unmute
		muteBGM = false;
	}

}
