import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Represents the screen at the start of the game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 11, 2015.
 */
public class StartScreen {

	private JFrame mainFrame;
	protected JPanel gameDescription;
	protected JPanel startGameButton;
	protected JLabel titleImage;
	protected Scoreboard scoreBoard;
	private ArcadeGame game;
	private ArcadeGameComponent agc;

	/**
	 * 
	 * Creates a start screen with a title image, instructions
	 * and a button to launch the game.
	 *
	 * @param game
	 * @param frame
	 * @param board
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public StartScreen(ArcadeGame game, JFrame frame, Scoreboard board) throws IOException {

		this.game = game;

		this.mainFrame = frame;

		this.scoreBoard = board;
		
		// Loads and plays the music in the game
		InputStream in = new FileInputStream("gameSound.wav");
		AudioStream audioStream = new AudioStream(in);
		AudioPlayer.player.start(audioStream);
		
		// Builds the image/instructions/button of the game.
		initImage();
		initDescription();
		initButton();

		// Adds the three panels containing the image/instructions/button to the frame.
		this.mainFrame.add(this.titleImage, BorderLayout.NORTH);
		this.mainFrame.add(this.gameDescription, BorderLayout.CENTER);
		this.mainFrame.add(this.startGameButton, BorderLayout.SOUTH);
	}

	/**
	 * Loads the image in the title screen and saves it to a panel
	 * 
	 */
	public void initImage() {

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("centipedeMainImage.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		this.titleImage = new JLabel(new ImageIcon(image));
	}

	/**
	 * 
	 * Loads the text file with the instructions of the game and saves
	 * it to a panel
	 *
	 */
	public void initDescription() {

		this.gameDescription = new JPanel();
		
		JTextArea textDescription = new JTextArea(20, 37);
		
		textDescription.setText(
				  "                                          Welcome to our Game !! \n"
				+ "            Created by Anthony De Rada, Bochuan Lu and Daniel Verlaque \n"
				+		  " \n "
				+		  " \n "
				+ "     Centipede is a vertical shooter developed by Atari Inc. that was \n"
				+ "     originally released in 1980 as an arcade game. In our version of \n"
				+ "     the game you control a space craft, shooting bugs that come down \n "
				+ "     from the top of the playing field. A centipede will snake back and \n"
				+ "     forth down the screen until the player successfully destroys it thus \n"
				+ "     completing the round."
				+ 	"\n"
				+	"\n"
				+	"\n"
				+ "                                      Press H in-game for instructions \n"
				+ "                        Press the 'Start Game' button to start playing"
				);
		textDescription.setBackground(Color.BLACK);
		textDescription.setFont(new Font("Times", Font.BOLD, 12));
		textDescription.setOpaque(true);
		textDescription.setForeground(Color.WHITE);
		textDescription.setLineWrap(true);
		textDescription.setWrapStyleWord(true);
		textDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
		textDescription.setEditable(false);
		
		this.gameDescription.add(textDescription);
	}

	/**
	 * 
	 * Creates a button and adds a listener to it to start the game.
	 * Puts the button on a panel
	 *
	 */
	public void initButton() {

		this.startGameButton = new JPanel();
		this.startGameButton.setLayout(new GridLayout(3, 3));
		this.startGameButton.setBackground(Color.BLACK);

		JButton startButton = new JButton("Start Game");
		startButton.setFocusable(false);
		startButton.setSize(10, 10);

		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(startButton);
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());
		this.startGameButton.add(new JLabel());

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent y) {

				startGame();
			}
		});
	}

	/**
	 * 
	 * Removes panels with the instructions and button from the main frame.
	 * Creates a game component and attaches it to the main frame.
	 * Launches the game.
	 *
	 */
	public void startGame() {

		this.gameDescription.remove(0);
		this.gameDescription.setVisible(false);
		this.startGameButton.setVisible(false);

		this.agc = new ArcadeGameComponent(this.game);
		
		this.mainFrame.add(this.scoreBoard, BorderLayout.SOUTH);
		this.mainFrame.add(this.agc);
		
		this.agc.requestFocusInWindow();
	}
}
