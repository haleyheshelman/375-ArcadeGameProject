import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Represents the HighestScoresBoard of the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 11, 2015.
 */
public class HighestScoresBoard {
	private static final int FRAME_WIDTH = 410;
	private static final int FRAME_HEIGHT = 550;
	private static final int FONT_SIZE = 12;
	private static final int LABEL_WIDTH = 370;
	private static final int LABEL_HEIGHT = 40;

	private ArcadeGame game;
	protected JLabel titleImage;
	private JFrame frame;
	private JLabel label;
	private JLabel nameLabel;
	private JLabel scoresLabel;
	private JPanel panel;
	private JButton button;
	private int[] topScores = new int[5];
	private String[] playerNames = new String[5];
	private int newScore;
	private String newName;
	private int index = -1;

	/**
	 * Constructs a HighestScoresBoard with the given name and score.
	 *
	 * @param name
	 * @param score
	 */
	public HighestScoresBoard(ArcadeGame game, String inputName, int score) {
		String name = inputName;
		if (name == null) {
			name = "";
		}
		if (name.isEmpty() || name.startsWith(" ")) {
			name = "*";
		}
		this.game = game;
		this.newName = name;
		this.newScore = score;
		this.frame = new JFrame("HighestScores!!!");
		this.panel = new JPanel();
		this.button = new JButton("Restart");

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("centipedeMainImage.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		this.titleImage = new JLabel(new ImageIcon(image));
	}

	/**
	 * Scans the file which stores the highest scores.
	 *
	 * @throws FileNotFoundException
	 */
	public void scanFile() throws FileNotFoundException {
		Scanner input = new Scanner(new File("HighestScores.txt"));
		for (int i = 0; i < 5; i++) {
			this.playerNames[i] = input.next();
			this.topScores[i] = input.nextInt();
		}
		input.close();
	}

	/**
	 * Sorts and indexs the new player's scores in the right place.
	 *
	 */
	public void sort() {
		for (int i = 0; i < 5; i++) {
			if (this.newScore > this.topScores[i]) {
				this.index = i;
				break;
			}
		}
		if (this.index != -1) {
			for (int i = 4; i > this.index; i--) {
				this.topScores[i] = this.topScores[i - 1];
				this.playerNames[i] = this.playerNames[i - 1];
			}
			this.topScores[this.index] = this.newScore;
			this.playerNames[this.index] = this.newName;
		}

	}

	/**
	 * Prints out the result and changes the highest scores file.
	 *
	 * @throws FileNotFoundException
	 */
	public void outPut() throws FileNotFoundException {
		java.io.PrintStream output = new java.io.PrintStream("HighestScores.txt");

		// Adds the title of the highest scores board.
		this.label = new JLabel("Top Board", SwingConstants.CENTER);
		this.label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		this.label.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE * 2));
		this.panel.add(this.label);

		// Adds the names and scores on the top.
		for (int i = 0; i < 5; i++) {
			//System.out.println(this.playerNames[i] + " " + this.topScores[i]);

			this.nameLabel = new JLabel((i + 1) + ". " + this.playerNames[i]);
			this.scoresLabel = new JLabel(this.topScores[i] + "");

			JLabel leftLabel = new JLabel();
			leftLabel.setPreferredSize(new Dimension(LABEL_WIDTH / 4, LABEL_HEIGHT));
			leftLabel.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
			this.panel.add(leftLabel);

			this.nameLabel.setPreferredSize(new Dimension(LABEL_WIDTH / 4, LABEL_HEIGHT));
			this.nameLabel.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
			this.panel.add(this.nameLabel);

			this.scoresLabel.setPreferredSize(new Dimension(LABEL_WIDTH / 4, LABEL_HEIGHT));
			this.scoresLabel.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
			this.panel.add(this.scoresLabel);

			JLabel rightLabel = new JLabel();
			rightLabel.setPreferredSize(new Dimension(LABEL_WIDTH / 4, LABEL_HEIGHT));
			rightLabel.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
			this.panel.add(rightLabel);

			// Covers the old file with the new top list.
			output.println(this.playerNames[i] + " " + this.topScores[i]);
		}
		output.flush();

		output.close();

		this.label = new JLabel("Your scores: " + this.game.score);
		this.label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		this.label.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
		this.panel.add(this.label);

		if (this.index == -1) {
			//System.out.println("Sorry, you are not on the board");
			this.label = new JLabel("Sorry, you are not on the board");
		} else {
			//System.out.println("Good Job!");
			this.label = new JLabel("Good Job!");
		}

		this.label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		this.label.setFont(new Font("Helvetica", Font.BOLD, FONT_SIZE));
		this.panel.add(this.label);

		// Adds the button with restart game function.
		ActionListener restart = new ActionListener() {

			@SuppressWarnings("synthetic-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					HighestScoresBoard.this.game.restart();
					HighestScoresBoard.this.frame.dispose();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}

		};
		this.button.addActionListener(restart);
		this.panel.add(this.button);

		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.add(this.titleImage, BorderLayout.NORTH);
		this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frame.setVisible(true);
	}

	/**
	 * Does everything to make HighScoresBoard work.
	 *
	 * @throws FileNotFoundException
	 */
	public void showResult() throws FileNotFoundException {
		this.scanFile();
		this.sort();
		this.outPut();
	}
}