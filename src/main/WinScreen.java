package main;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import files.Files;
import files.Level;
import handlers.MouseHandler;

public class WinScreen {
	private Rectangle[] bounds = { new Rectangle(140, 235, 210, 45), new Rectangle(140, 310, 210, 45),
			new Rectangle(140, 385, 210, 45) };

	private Rectangle[] options = { new Rectangle(460, 8, 30, 30), new Rectangle(420, 8, 30, 30) };

	private Rectangle mainMenu;
	private Image background;
	private Image soundoff;
	private Image soundon;
	private Image musicoff;
	private Image musicon;

	private boolean unlockedNewLevel = false;

	public WinScreen() {
		mainMenu = new Rectangle(Frame.WIDTH / 2 - 60, 380, 150, 55);
		background = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		soundoff = new ImageLoader(ImageLoader.soundoff).getImage();
		soundon = new ImageLoader(ImageLoader.soundon).getImage();
		musicoff = new ImageLoader(ImageLoader.musicoff).getImage();
		musicon = new ImageLoader(ImageLoader.musicon).getImage();

		if (Controller.level + 1 < Level.MAX_LEVEL && Level.lockedLevels[Controller.level + 1]) {
			unlockedNewLevel = true;
		}
		if (Controller.level < Level.MAX_LEVEL - 1) {
			if (Level.lockedLevels[Controller.level + 1] == true) {
				BgmLoader.StopBGM();
				BgmLoader.BGM(BgmLoader.WinBGM);
				BgmLoader.PlayBGM();
				Level.lockedLevels[Controller.level + 1] = false;
				Files.SaveProgress(Level.lockedLevels);
			}
		}
	}

	public void tick() {
		if (Controller.level < Level.MAX_LEVEL - 1) {
			for (int i = 0; i < bounds.length; i++) {
				if (bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					MouseHandler.MOUSEDOWN = false;
					if (i == 0) {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						if (Controller.level + 1 == 1)
							BgmLoader.BGM(BgmLoader.Lvl1_BGM);
						if (Controller.level + 1 == 2)
							BgmLoader.BGM(BgmLoader.Lvl2_BGM);
						if (Controller.level + 1 == 3)
							BgmLoader.BGM(BgmLoader.Lvl3_BGM);
						if (Controller.level + 1 == 4)
							BgmLoader.BGM(BgmLoader.Lvl4_BGM);
						if (Controller.level + 1 == 5)
							BgmLoader.BGM(BgmLoader.Lvl5_BGM);
						if (Controller.level + 1 == 6)
							BgmLoader.BGM(BgmLoader.Lvl6_BGM);
						if (Controller.level + 1 == 7)
							BgmLoader.BGM(BgmLoader.Lvl7_BGM);
						BgmLoader.PlayBGM();
						Controller.switchStates(Controller.STATE.GAME, Controller.level + 1);
					} else if (i == 1) {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						if (Controller.level == 0)
							BgmLoader.BGM(BgmLoader.Lvl0_BGM);
						if (Controller.level == 1)
							BgmLoader.BGM(BgmLoader.Lvl1_BGM);
						if (Controller.level == 2)
							BgmLoader.BGM(BgmLoader.Lvl2_BGM);
						if (Controller.level == 3)
							BgmLoader.BGM(BgmLoader.Lvl3_BGM);
						if (Controller.level == 4)
							BgmLoader.BGM(BgmLoader.Lvl4_BGM);
						if (Controller.level == 5)
							BgmLoader.BGM(BgmLoader.Lvl5_BGM);
						if (Controller.level == 6)
							BgmLoader.BGM(BgmLoader.Lvl6_BGM);
						if (Controller.level == 7)
							BgmLoader.BGM(BgmLoader.Lvl7_BGM);
						BgmLoader.PlayBGM();
						Controller.switchStates(Controller.STATE.GAME, Controller.level);
					} else {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						Controller.switchStates(Controller.STATE.MENU);
					}
				}
			}
		} else {
			for (int i = 0; i < bounds.length; i++) {
				if (bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					MouseHandler.MOUSEDOWN = false;
					if (i == 0) {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						Controller.switchStates(Controller.STATE.MENU);
					} else {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						if (Controller.level == 0)
							BgmLoader.BGM(BgmLoader.Lvl0_BGM);
						if (Controller.level == 1)
							BgmLoader.BGM(BgmLoader.Lvl1_BGM);
						if (Controller.level == 2)
							BgmLoader.BGM(BgmLoader.Lvl2_BGM);
						if (Controller.level == 3)
							BgmLoader.BGM(BgmLoader.Lvl3_BGM);
						if (Controller.level == 4)
							BgmLoader.BGM(BgmLoader.Lvl4_BGM);
						if (Controller.level == 5)
							BgmLoader.BGM(BgmLoader.Lvl5_BGM);
						if (Controller.level == 6)
							BgmLoader.BGM(BgmLoader.Lvl6_BGM);
						if (Controller.level == 7)
							BgmLoader.BGM(BgmLoader.Lvl7_BGM);
						BgmLoader.PlayBGM();
						Controller.switchStates(Controller.STATE.GAME, Controller.level);
					}
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
						BgmLoader.BGM(BgmLoader.WinBGM);
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
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		if (Controller.level < Level.MAX_LEVEL - 1) {
			g.setColor(Color.WHITE);
			g.setFont(Controller.bigFont);
			g.drawString("LEVEL " + Controller.level + " COMPLETE",
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("LEVEL COMPLETE") / 2, 150);
			g.drawString("Score: " + Controller.score,
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Score: " + Controller.score) / 2, 190);
			g.drawRect(140, 235, 210, 45);
			g.drawRect(140, 310, 210, 45);
			g.drawRect(140, 385, 210, 45);
			g.setColor(Color.white);
			if (unlockedNewLevel) {
				g.setColor(Color.orange);
				g.drawString("NEW LEVEL UNLOCKED!",
						Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("NEW LEVEL UNLOCKED!") / 2, 100);
				g.setColor(Color.white);
			}
			for (int i = 0; i < bounds.length; i++) {
				if (bounds[i].contains(Controller.mousePoint)) {
					g.drawRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
					g.setColor(new Color(255, 255, 255, 150));
					g.fillRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
					g.setColor(Color.white);
				}
			}
			g.drawString("Next Level", (Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Next Level") / 2) - 3,
					mainMenu.y - 110);
			g.drawString("Main Menu", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Main Menu") / 2,
					mainMenu.y + 40);
			g.drawString("Retry", (Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Retry") / 2) - 3, mainMenu.y - 35);
		} else {
			g.setColor(Color.orange);
			g.drawString("CONGRATULATIONS YOU BEAT THE GAME!",
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("CONGRATULATIONS YOU BEAT THE GAME!") / 2, 100);
			g.setFont(Controller.bigFont);
			g.drawString("Thank you for playing!",
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Thank you for playing!") / 2, 150);
			g.setFont(Controller.smallFont);
			g.drawString("Please look forward for our next updates.",
					Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Please look forward for our next updates.") / 2,
					190);
			g.setColor(Color.white);
			g.drawRect(140, 235, 210, 45);
			g.drawRect(140, 310, 210, 45);
			for (int i = 0; i < 2; i++) {
				if (bounds[i].contains(Controller.mousePoint)) {
					g.drawRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
					g.setColor(new Color(255, 255, 255, 150));
					g.fillRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
					g.setColor(Color.white);
				}
			}
			g.drawString("Main Menu", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Main Menu") / 2,
					mainMenu.y - 110);
			g.drawString("Retry", (Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Retry") / 2) - 3, mainMenu.y - 35);
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
