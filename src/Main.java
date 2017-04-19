import java.io.IOException;

import javax.swing.JFrame;

/**
 * The main class for your arcade game. 
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
		frameSetup();
		scoreboard = new Scoreboard();
		ArcadeGame game = new ArcadeGame(FRAME_HEIGHT - 87 - IMAGE_HEIGHT,
				getFrameWidth() - 18);
		startScreen = new StartScreen(game, frame, scoreboard);
		displayFrame();
	}

	private static void displayFrame() {
		frame.setTitle("Arcade Game!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static void frameSetup() {
		frame = new JFrame();
		frame.setSize(getFrameWidth(), FRAME_HEIGHT);
	}

	public static void changeTitle(String newTitle) {
		frame.setTitle(newTitle);
	}

	public static int getFrameWidth() {
		return FRAME_WIDTH;
	}

}
