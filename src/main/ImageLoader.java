package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader { //used to load image 
	private BufferedImage img;
	public static String titleScreenBackground = "/BrickBreakerBackground.png";
	public static String LevelScreenBackground = "/LevelScreenBackground.png";
	public static String GameOverBackground = "/GameOverBackground.gif";
	public static String paddle = "/Paddle.png";
	public static String brick = "/Bricks.png";
	public static String ball = "/Ball.png";
	public static String fireBall = "/FireBall.png";
	public static String arrow = "/Arrow.png";
	public static String arrow2 = "/Arrow2.png";
	public static String locked = "/locked.png";
	public static String PUMultiBall = "/PUMulitBall.png";
	public static String PUGrowth = "/PUGrowth.png";
	public static String PUFireball = "/PUFireball.png";
	public static String soundoff = "/soundoff.png";
	public static String soundon = "/soundon.png";
	public static String musicoff = "/musicoff.png";
	public static String musicon = "/musicon.png";
	public static String lvl0 = "images/BG_Lvl0.gif";
	public static String lvl1 = "images/BG_Lvl1.gif";
	public static String lvl2 = "images/BG_Lvl2.gif";
	public static String lvl3 = "images/BG_Lvl3.gif";
	public static String lvl4 = "images/BG_Lvl4.gif";
	public static String lvl5 = "images/BG_Lvl5.gif";
	public static String lvl6 = "images/BG_Lvl6.gif";
	public static String lvl7 = "images/BG_Lvl7.gif";
	public static String logo = "images/logo.png";

	public ImageLoader(String path) { 
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path)); //Read image

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return img;
	}

	public BufferedImage getSubImage(int section) {
		return img.getSubimage(0, section * 25, 50, 25);
	}
}
