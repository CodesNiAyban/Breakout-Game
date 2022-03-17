package main;

import java.awt.*;
import files.Files;
import files.Level;
import handlers.MouseHandler;

public class PickLevel {
	
	private Image arrow;
	private Image locked;
	
	Rectangle[] levels = {new Rectangle(60, 200, 75, 75), new Rectangle(160, 200, 75, 75),
						  new Rectangle(260, 200, 75, 75), new Rectangle(360, 200, 75, 75),
						  new Rectangle(60, 300, 75, 75), new Rectangle(160, 300, 75, 75),
						  new Rectangle(260, 300, 75, 75), new Rectangle(360, 300, 75, 75)};

	
	Rectangle[] options = {new Rectangle(460, 8, 30, 30),
			   			   new Rectangle(420, 8, 30, 30)};
	
	Rectangle[] arrows = {new Rectangle(40, Frame.HEIGHT-90, 30, 30)};
	
	
	private Image LevelScreenBackground;
	private Image soundoff;
	private Image soundon;
	private Image musicoff;
	private Image musicon;
	

	public PickLevel() {
		arrow = new ImageLoader(ImageLoader.arrow).getImage();
		locked = new ImageLoader(ImageLoader.locked).getImage();
		soundoff = new ImageLoader(ImageLoader.soundoff).getImage();
		soundon = new ImageLoader(ImageLoader.soundon).getImage();
		musicoff = new ImageLoader(ImageLoader.musicoff).getImage();
		musicon = new ImageLoader(ImageLoader.musicon).getImage();
		LevelScreenBackground = new ImageLoader(ImageLoader.LevelScreenBackground).getImage();
		Level.lockedLevels = Files.readFile(Files.LEVELPATH);
	}
	
	public void tick(){
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < levels.length; i++) {
				if(levels[i].contains(Controller.mousePoint)) {
					if(Level.lockedLevels[i] == false) {
						SELoader.SE(SELoader.Click);
						BgmLoader.StopBGM();
						if(i == 0) BgmLoader.BGM(BgmLoader.Lvl0_BGM);
						if(i == 1) BgmLoader.BGM(BgmLoader.Lvl1_BGM);
						if(i == 2) BgmLoader.BGM(BgmLoader.Lvl2_BGM);
						if(i == 3) BgmLoader.BGM(BgmLoader.Lvl3_BGM);
						if(i == 4) BgmLoader.BGM(BgmLoader.Lvl4_BGM);
						if(i == 5) BgmLoader.BGM(BgmLoader.Lvl5_BGM);
						if(i == 6) BgmLoader.BGM(BgmLoader.Lvl6_BGM);
						if(i == 7) BgmLoader.BGM(BgmLoader.Lvl7_BGM);
						BgmLoader.PlayBGM();
						Controller.switchStates(Controller.STATE.GAME, i);
					}else {
						SELoader.SE(SELoader.Inaccessible);
					}
				}
			}
			for(int i = 0; i < arrows.length; i++) {
				if(arrows[i].contains(Controller.mousePoint)) {
					SELoader.SE(SELoader.Click);
					BgmLoader.StopBGM();
					Controller.switchStates(Controller.STATE.MENU);	
				}
			}
			for(int i = 0; i < options.length; i++) {
				if(options[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					MouseHandler.MOUSEDOWN = false;
					SELoader.SE(SELoader.Click);
					if(i == 0) {
						if(!BgmLoader.muteBGM){
							BgmLoader.MuteBGM();
						}else{
							BgmLoader.UnmuteBGM();
							BgmLoader.BGM(BgmLoader.LevelMenuBGM);
							BgmLoader.PlayBGM();
						}
					}else{
						if(!SELoader.muteSE){
							SELoader.MuteSE();
						}else{
							SELoader.UnmuteBGM();
						}
					}
				}
			}
			MouseHandler.MOUSEDOWN = false;
		}	
	}
	
	public void render(Graphics g) {
		g.setFont(Controller.bigFont);
		g.drawImage(LevelScreenBackground, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
		g.setColor(Color.white);
		g.drawString("WORLD 1-1", Frame.WIDTH/2-g.getFontMetrics().stringWidth("WORLD 1-1")/2, 80);
		
		for(int i = 0; i < levels.length; i++) {
			g.setColor(Color.white);
			g.drawString("" + (i), levels[i].x+35, levels[i].y+45);
			if(levels[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
			}
			
			g.drawRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
		}
		
		for(int i = 0; i < levels.length; i++) {
			if(Level.lockedLevels[i] == true) {
				g.drawImage(locked, levels[i].x, levels[i].y, levels[i].width, levels[i].height, null);
			}
		}
		for(int i = 0; i < options.length; i++) { 
			
			if(i == 0 && !BgmLoader.muteBGM) {
				g.drawImage(musicon, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}else if(i == 0 && BgmLoader.muteBGM) {
				g.drawImage(musicoff, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}
			
			if(i == 1 && !SELoader.muteSE){
				g.drawImage(soundon, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}else if(i == 1 && SELoader.muteSE){
				g.drawImage(soundoff, options[i].x, options[i].y, options[i].width, options[i].height, null);
			}
	
			if(options[i].contains(Controller.mousePoint)) {
				g.drawRect(options[i].x, options[i].y, options[i].width, options[i].height);
			}
		}	
		
		for(int i = 0; i < arrows.length; i++) {
			if(arrows[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height);
			}
			g.setColor(Color.white);
			if(i == 0) {
				g.drawImage(arrow, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}
		}
	}
}
