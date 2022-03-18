package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // Keys handler

	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean UP = false;

	public KeyHandler() {

	}

	public void keyTyped(KeyEvent e) {

	}
	// Arrow keys pressed
	public void keyPressed(KeyEvent e) { 
		if (e.getKeyCode() == 87 || e.getKeyCode() == 38) { // Up
			UP = true;
		}
		if (e.getKeyCode() == 65 || e.getKeyCode() == 37) { // Left
			LEFT = true;
		}
		if (e.getKeyCode() == 68 || e.getKeyCode() == 39) { // Right
			RIGHT = true;
		}
	}
	// Arrow keys release
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 87 || e.getKeyCode() == 38) { // Up
			UP = false;
		}
		if (e.getKeyCode() == 65 || e.getKeyCode() == 37) { // Left
			LEFT = false;
		}
		if (e.getKeyCode() == 68 || e.getKeyCode() == 39) { // Right
			RIGHT = false;
		}
	}

}
