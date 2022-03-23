package main;

import javax.swing.JFrame;

public class Frame {
	// Create Frame
	private static JFrame frame;
	public static int WIDTH = 500;
	public static int HEIGHT = 500;

	public static void main(String[] args) {
		frame = new JFrame("Breakout Game BETA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Controller());
		frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setBounds(0, 0, 516, 516); // Fix to ball setting out of bounds
		frame.setLocationRelativeTo(null);
	}

}
