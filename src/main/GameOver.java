package main;

import java.awt.*;

import javax.swing.JOptionPane;

import files.Files;
import files.Level;
import handlers.MouseHandler;

public class GameOver { // Game over menu
	// Retry and Main menu bounds
	private Rectangle[] bounds = { new Rectangle(140, 325, 210, 45), new Rectangle(140, 400, 210, 45) };  
	// Sound handler
	private Rectangle[] options = { new Rectangle(460, 8, 30, 30), new Rectangle(420, 8, 30, 30) };
	
	private Rectangle mainMenu;
	private Image background;
	private Image soundoff;
	private Image soundon;
	private Image musicoff;
	private Image musicon;

	public GameOver() { // Call game over state
		mainMenu = new Rectangle(Frame.WIDTH / 2 - 60, 380, 150, 55);
		background = Toolkit.getDefaultToolkit().createImage("images/GameOverBackground.gif");
		soundoff = new ImageLoader(ImageLoader.soundoff).getImage();
		soundon = new ImageLoader(ImageLoader.soundon).getImage();
		musicoff = new ImageLoader(ImageLoader.musicoff).getImage();
		musicon = new ImageLoader(ImageLoader.musicon).getImage();
	}

	public void tick() { //GameOver screen handler
		for (int i = 0; i < bounds.length; i++) { // Main menu and retry
			if (bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) { // Retry
				MouseHandler.MOUSEDOWN = false;
				if (i == 0) { // background music caller
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
				} else { // Main menu
					SELoader.SE(SELoader.Click);
					BgmLoader.StopBGM();
					Controller.switchStates(Controller.STATE.MENU);
				}
			}
		}
		for (int i = 0; i < options.length; i++) { // Sound manipulator
			if (options[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				SELoader.SE(SELoader.Click);
				MouseHandler.MOUSEDOWN = false;
				if (i == 0) {
					SELoader.SE(SELoader.Click);
					if (!BgmLoader.muteBGM) {
						BgmLoader.MuteBGM();
					} else {
						BgmLoader.UnmuteBGM();
						BgmLoader.BGM(BgmLoader.GameOverBGM);
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
		//Background image
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		//Declare font color
		g.setColor(Color.white);
		//Game over info
		g.setFont(Controller.bigFont);
		g.drawString("LEVEL " + Controller.level + " FAILED",
				Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("LEVEL " + Controller.level + " FAILED") / 2, 110);
		g.drawString("Score: " + Controller.score,
				(Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Score" + Controller.score) / 2) - 5, 150);
		g.drawString("Main Menu", Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Main Menu") / 2, mainMenu.y + 55);
		g.drawString("Retry", (Frame.WIDTH / 2 - g.getFontMetrics().stringWidth("Retry") / 2) - 3, mainMenu.y - 20);
		g.drawRect(140, 325, 210, 45);
		g.drawRect(140, 400, 210, 45);
		g.setColor(Color.white);
		// User feedback
		for (int i = 0; i < bounds.length; i++) {
			if (bounds[i].contains(Controller.mousePoint)) {
				g.drawRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
				g.setColor(Color.white);
			}
		}
		// Sound manipulator user feedback
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
