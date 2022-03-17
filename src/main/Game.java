package main;

import handlers.MouseHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import files.Files;
import files.Level;
import handlers.KeyHandler;
import parts.Ball;
import parts.Brick;
import parts.Paddle;
import parts.Powerup;

public class Game extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Rectangle[] bounds = {new Rectangle(430, 430, 150, 55)};
	
	Rectangle[] options = {new Rectangle(370, 440, 30, 30),
			   			   new Rectangle(320, 440, 30, 30)};
	private Rectangle mainMenu;
	private Rectangle sound;
	private Rectangle music;
	private Paddle paddle;
	private Ball[] balls;
	private Brick[] bricks;
	private Image lives;
	private Image titleScreenBackground;
	private Image LevelScreenBackground;
	private Image soundoff;
	private Image soundon;
	private Image musicoff;
	private Image musicon;
	private Image lvl0;
	private Image lvl1;
	private Image lvl2;
	private Image lvl3;
	private Image lvl4;
	private Image lvl5;
	private Image lvl6;
	private Image lvl7;
	
	
	private Powerup[] powerups;
	private int[][] gridPos = new int[8][10];
 	private int score = 0;
 	private boolean hasStarted = false;
 	private int livesLeft = 2;
 	private int totalBallCount = 1;
 	private int totalBricks = 0;
 	
	public Game(int level) {
		titleScreenBackground = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		mainMenu = new Rectangle(430, 430, 150, 55); 
		sound = new Rectangle(380, 440, 30, 30); 
		music = new Rectangle(340, 440, 30, 30); 
		lvl0 = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		lvl1 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl1);
		lvl2 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl2);
		lvl3 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl3);
		lvl4 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl4);
		lvl5 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl5);
		lvl6 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl6);
		lvl7 = Toolkit.getDefaultToolkit().createImage(ImageLoader.lvl7);
		soundoff = new ImageLoader(ImageLoader.soundoff).getImage();
		soundon = new ImageLoader(ImageLoader.soundon).getImage();
		musicoff = new ImageLoader(ImageLoader.musicoff).getImage();
		musicon = new ImageLoader(ImageLoader.musicon).getImage();
		paddle = new Paddle(Frame.WIDTH/2-50, 400);
		balls = new Ball[3];
		balls[0] = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
		totalBallCount = 1;
		gridPos = Level.getLevel(level);
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				if(gridPos[i][j] != 0) {
					totalBricks++;
				}
			}
		}
		init();
	}
	public void init() {
		int count = 0;
		lives = new ImageLoader(ImageLoader.ball).getImage();
		bricks = new Brick[80];//60 is max
		powerups = new Powerup[5];
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				bricks[count] = new Brick(j*50, i*25, gridPos[i][j]);
				count++;
			}
		}
	}
	public void tick() {
		if(hasStarted) {
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].tick();
					balls[i].checkBrickCollision(bricks);
					balls[i].checkPaddleCollision(paddle);
					if(balls[i].getY() > Frame.HEIGHT-50) {
						if(totalBallCount <= 1) {
						hasStarted = false;
						balls[i] = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
						livesLeft -= 1;
						SELoader.SE(SELoader.Fall);
							if(livesLeft < 0) { //game over
								Controller.score = score;
								BgmLoader.StopBGM();
								SELoader.SE(SELoader.GameOverSE);
								BgmLoader.BGM(BgmLoader.GameOverBGM);
								BgmLoader.PlayBGM();
								Controller.switchStates(Controller.STATE.GAMEOVER);
							}
						}else {
							balls[i] = null;
							totalBallCount--;
						}
					}
				}
			}
			for(int i = 0; i < bricks.length; i++) {
				if(bricks[i].hasDied) {
					totalBricks--;
					score += bricks[i].originalLevel;
					if(totalBricks == 0) {
						BgmLoader.StopBGM();
						SELoader.SE(SELoader.Win);
						BgmLoader.BGM(BgmLoader.WinBGM);
						BgmLoader.PlayBGM();
						Controller.score = score;
						Controller.switchStates(Controller.STATE.WINSCREEN);
					}
					bricks[i].hasDied = false;
				}
				if(bricks[i].dropPowerup) {
					for(int j = 0; j < powerups.length; j++) {
						if(powerups[j] == null) {
							powerups[j] = new Powerup(bricks[i].getX(), bricks[i].getY(), bricks[i].hasPowerup());
							bricks[i].dropPowerup = false;
							break;
						}
					}
				}
				bricks[i].dropPowerup = false;
			}
			
			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i].tick();
					if(powerups[i].remove) {
						powerups[i] = null;
					}
					if(paddle.isColliding(powerups[i])) {	
						if(powerups[i].powerup == 1) { //Add another ball
							SELoader.SE(SELoader.Double);
							for(int j = 0; j < balls.length; j++) {
								if(balls[j] == null) {
									balls[j] = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
									totalBallCount++;
									break;
								}
							}
						}
						if(powerups[i].powerup == 2) { //grow paddle
							SELoader.SE(SELoader.Long);
							paddle.width += 15;
						}
						if(powerups[i].powerup == 3) { //fire ball
							SELoader.SE(SELoader.Fire);
							for(int j = 0; j < balls.length; j++) {
								if(balls[j] != null) {
									balls[j].setOnFire(4);
									break;
								}
							}
						}
						powerups[i] = null;
					}
				}
			}
		}else if(!hasStarted) {
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].setX(paddle.getX()+paddle.width/2-balls[i].width/2);
				}
			}
			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i] = null;
				}
			}
			if(KeyHandler.UP) {
				hasStarted = true;
				SELoader.SE(SELoader.Bounce);
			}
		}
		if(KeyHandler.LEFT) {
			paddle.moveLeft();
		}
		if(KeyHandler.RIGHT) {
			paddle.moveRight();
		}

		for(int i = 0; i < bounds.length; i++) {
			if(bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) {
					SELoader.SE(SELoader.GameOverSE);
					 int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to go back?", "Back",
				               JOptionPane.YES_NO_OPTION,
				               JOptionPane.QUESTION_MESSAGE);
				            if(result == JOptionPane.YES_OPTION) {
				            	SELoader.SE(SELoader.Click);
				            	BgmLoader.StopBGM();
								BgmLoader.BGM(BgmLoader.LevelMenuBGM);
								BgmLoader.PlayBGM();
								Controller.switchStates(Controller.STATE.PICKLEVEL);    
				            }else {
				            	SELoader.SE(SELoader.Click);
				            }
				}
			}
		}
		
		for(int i = 0; i < options.length; i++) {
			if(options[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				SELoader.SE(SELoader.Click);
				MouseHandler.MOUSEDOWN = false;
				SELoader.SE(SELoader.Click);
				if(i == 0) {
					if(!BgmLoader.muteBGM){
						BgmLoader.MuteBGM();
					}else{
						BgmLoader.UnmuteBGM();
						if(Controller.level == 0) BgmLoader.BGM(BgmLoader.Lvl0_BGM);
						if(Controller.level == 1) BgmLoader.BGM(BgmLoader.Lvl1_BGM);
						if(Controller.level == 2) BgmLoader.BGM(BgmLoader.Lvl2_BGM);
						if(Controller.level == 3) BgmLoader.BGM(BgmLoader.Lvl3_BGM);
						if(Controller.level == 4) BgmLoader.BGM(BgmLoader.Lvl4_BGM);
						if(Controller.level == 5) BgmLoader.BGM(BgmLoader.Lvl5_BGM);
						if(Controller.level == 6) BgmLoader.BGM(BgmLoader.Lvl6_BGM);
						if(Controller.level == 7) BgmLoader.BGM(BgmLoader.Lvl7_BGM);
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
	}
	
	public void render(Graphics g) {
		if(Controller.level == 0) g.drawImage(lvl0, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
		if(Controller.level == 1) g.drawImage(lvl1, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		if(Controller.level == 2) g.drawImage(lvl2, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		if(Controller.level == 3) g.drawImage(lvl3, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		if(Controller.level == 4) g.drawImage(lvl4, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		if(Controller.level == 5) g.drawImage(lvl5, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		if(Controller.level == 6) g.drawImage(lvl6, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
		if(Controller.level == 7) g.drawImage(lvl7, 0, 0, Frame.WIDTH, Frame.WIDTH, null);	
		
		int count = 0;
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				if(gridPos[i][j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(), bricks[count].getY(), null);
				}	
				count++;
			}
		}
		for(int i = 0; i < powerups.length; i++) {
			if(powerups[i] != null) {
					g.drawImage(powerups[i].getImage(), powerups[i].getX(), powerups[i].getY(), powerups[i].getWidth(), powerups[i].getWidth(), null);
			}
		}
		
		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), null);
		for(int i = 0; i < balls.length; i++) {
			if(balls[i] != null) {
				if(!balls[i].onFire) {		
					g.drawImage(balls[i].getImage(), balls[i].getX(), balls[i].getY(), null);
				}else {
					g.drawImage(balls[i].getImage(), balls[i].getX(), balls[i].getY(), null);
				}
			}
		}
		
		g.setColor(Color.black);
		g.fillRect(0, Frame.HEIGHT-70, Frame.WIDTH, 100);
		
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
		
		g.setColor(Color.white);
		g.setFont(Controller.smallFont);
		g.drawString("Score: " + score, 20, Frame.HEIGHT-40);
		g.drawImage(lives, 100, Frame.HEIGHT-52, 15, 15, null);
		g.drawString("Level " + Controller.level, 230, Frame.HEIGHT-40);
		g.drawString(": " + livesLeft, 120, Frame.HEIGHT-40);
		if(mainMenu.contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
			g.setColor(Color.white);
		}
		if(options[0].contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(options[0].x, options[0].y, options[0].width, options[0].height);
			g.setColor(Color.white);
		}
		if(options[1].contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(options[1].x, options[1].y, options[1].width, options[1].height);
			g.setColor(Color.white);
		}
		g.drawString("Back", mainMenu.x + 15, mainMenu.y + 30);
	}
}
