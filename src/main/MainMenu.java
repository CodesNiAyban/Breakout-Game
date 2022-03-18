package main;

import java.awt.*;

import javax.swing.JOptionPane;

import files.Files;
import files.Level;
import handlers.MouseHandler;

public class MainMenu {

	private Rectangle[] bounds = { new Rectangle(140, 235, 210, 45), new Rectangle(140, 310, 210, 45) };

	private Rectangle[] options = { new Rectangle(460, 8, 30, 30), new Rectangle(420, 8, 30, 30) };
	
	private Image titleScreenBackground;
	private Image soundoff;
	private Image soundon;
	private Image musicoff;
	private Image musicon;
	private static String filePath = Files.getDefaultDirectory() + "/BrickBreaker/";
	public static String LEVELPATH = Files.getDefaultDirectory() + "/BrickBreaker/Levels.txt";

	public MainMenu() {
		titleScreenBackground = Toolkit.getDefaultToolkit().createImage("images/BrickBreakerBackground.gif");
		soundoff = new ImageLoader(ImageLoader.soundoff).getImage();
		soundon = new ImageLoader(ImageLoader.soundon).getImage();
		musicoff = new ImageLoader(ImageLoader.musicoff).getImage();
		musicon = new ImageLoader(ImageLoader.musicon).getImage();
		BgmLoader.BGM(BgmLoader.MainMenuBGM);
		BgmLoader.PlayBGM();
	}

	public void tick() {
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if (i == 0) {
					SELoader.SE(SELoader.Click);
					int result = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to start a new game? All progress will be lost.", "New Game",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						BgmLoader.BGM(BgmLoader.LevelMenuBGM);
						BgmLoader.PlayBGM();
						Controller.switchStates(Controller.STATE.PICKLEVEL);
						Files.deleteFile(LEVELPATH);
						Files.createFile(filePath + "Levels.txt");
						for (i = 1; i < Level.MAX_LEVEL; i++) {
							Level.lockedLevels[i] = true;
						}
						Controller.switchStates(Controller.STATE.PICKLEVEL);
					} else if (result == JOptionPane.NO_OPTION) {
						SELoader.SE(SELoader.Click);
					} else {
						SELoader.SE(SELoader.Click);
					}
				} else {
					SELoader.SE(SELoader.Click);
					BgmLoader.StopBGM();
					BgmLoader.BGM(BgmLoader.LevelMenuBGM);
					BgmLoader.PlayBGM();
					Controller.switchStates(Controller.STATE.PICKLEVEL);
				}
			}
		}
		for (int i = 0; i < options.length; i++) {
			if (options[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				SELoader.SE(SELoader.Click);
				MouseHandler.MOUSEDOWN = false;
				if (i == 0) {
					SELoader.SE(SELoader.Click);
					if (!BgmLoader.muteBGM) {
						BgmLoader.MuteBGM();
					} else {
						BgmLoader.UnmuteBGM();
						BgmLoader.BGM(BgmLoader.MainMenuBGM);
						BgmLoader.PlayBGM();
					}
				} else {
					if (!SELoader.muteSE) {
						SELoader.MuteSE();
					} else {
						SELoader.UnmuteBGM();
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(titleScreenBackground, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
		g.setColor(Color.white);
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i].contains(Controller.mousePoint)) {
				g.drawRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
			}
		}
		for (int i = 0; i < options.length; i++) {

			if (i == 0 && !BgmLoader.muteBGM) {
				g.drawImage(musicon, options[i].x, options[i].y, options[i].width, options[i].height, null);
			} else if (i == 0 && BgmLoader.muteBGM) {
				g.drawImage(musicoff, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}

			if (i == 1 && !SELoader.muteSE) {
				g.drawImage(soundon, options[i].x, options[i].y, options[i].width, options[i].height, null);
			} else if (i == 1 && SELoader.muteSE) {
				g.drawImage(soundoff, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}

			if (options[i].contains(Controller.mousePoint)) {
				g.drawRect(options[i].x, options[i].y, options[i].width, options[i].height);
			}
		}
	}
}
