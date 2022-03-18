package handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Controller;

public class MouseHandler implements MouseMotionListener, MouseListener {  // Mouse Handler

	public static boolean MOUSEDOWN = false;
	public static boolean hasPressed = false;

	public MouseHandler() {

	}

	public void mouseClicked(MouseEvent e) {
	}
	// Mouse
	public void mousePressed(MouseEvent e) {
		if (!hasPressed) {
			MOUSEDOWN = true;
			hasPressed = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		MOUSEDOWN = false;
		hasPressed = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	// Mouse move detection
	public void mouseDragged(MouseEvent e) {
		MOUSEDOWN = false;
	}

	public void mouseMoved(MouseEvent e) {
		Controller.mousePoint = e.getPoint();
	}
}
