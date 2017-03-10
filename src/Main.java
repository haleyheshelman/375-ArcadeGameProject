import java.io.IOException;

import javax.swing.JFrame;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start by running
 * main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * 
 * (student note--I'm leaving his description in on purpose)
 * 
 * CYCLE 1 COMPLETE
 * 
 * @author Buffalo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	private static final int IMAGE_HEIGHT = 107;
	private static final int FRAME_HEIGHT = 360 + 45 + IMAGE_HEIGHT;
	private static final int FRAME_WIDTH = 400 + 18;
	protected static JFrame frame;
	protected static Scoreboard scoreboard;
	protected static StartScreen startScreen;

	public static void main(String[] args) throws IOException {
		System.out.println("Write your cool arcade game here!");
		// make the frame, set the size
		frame = new JFrame();
		frame.setSize(getFrameWidth(), FRAME_HEIGHT);
		
		// Make the scoreboard
		scoreboard = new Scoreboard();

		// Create the game
		ArcadeGame game = new ArcadeGame(FRAME_HEIGHT - 87 - IMAGE_HEIGHT,
				getFrameWidth() - 18);
		
		// Makes the start screen
		startScreen = new StartScreen(game, frame, scoreboard);
		
		frame.setTitle("Arcade Game!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void changeTitle(String newTitle) {
		frame.setTitle(newTitle);
	}

	public static int getFrameWidth() {
		return FRAME_WIDTH;
	}

}
