import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Scoreboard is used by main to display the score, lives remaining, and the
 * level number. Scoreboard extends JPanel, and has JLabels for each field.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
@SuppressWarnings("serial")
public class Scoreboard extends JPanel {
	private JLabel livesField = new JLabel("Lives: 3");
	private JLabel scoreField = new JLabel("Score: 0");
	private JLabel levelField = new JLabel("Level: 1");
	private JLabel weaponField = new JLabel("Cannon");

	/**
	 * Constructs a default Scoreboard.
	 *
	 */
	public Scoreboard() {
		this.setPreferredSize(new Dimension(Main.getFrameWidth() - 18, 20));
this.setLayout(new GridLayout(1,4));
		this.livesField.setPreferredSize(new Dimension(
				Main.getFrameWidth() / 4, 19));
		this.scoreField.setPreferredSize(new Dimension(
				Main.getFrameWidth() / 4, 19));
		this.levelField.setPreferredSize(new Dimension(
				Main.getFrameWidth() / 4, 19));
		this.weaponField.setPreferredSize(new Dimension(
				Main.getFrameWidth() / 4, 19));

		this.add(this.livesField);
		this.add(this.scoreField);
		this.add(this.levelField);
		this.add(this.weaponField);
	}

	/**
	 * Constructs a Scoreboard with given LayoutManager.
	 *
	 * @param arg0
	 */
	public Scoreboard(LayoutManager arg0) {
		super(arg0);
	}

	/**
	 * Changes the displayed number of lives
	 * 
	 * @param lives
	 */
	public void changeLives(int lives) {
		this.livesField.setText("Lives: " + (lives + 1));
	}

	/**
	 * Changes the displayed score
	 * 
	 * @param score
	 */
	public void changeScore(int score) {
		this.scoreField.setText("Score: " + (score));
	}

	/**
	 * Changes the displayed level
	 * 
	 * @param level
	 */
	public void changeLevel(int level) {
		this.levelField.setText("Level: " + (level));
	}
	
	/**
	 * Changes the type of weapon.
	 *
	 * @param weaponNum
	 */
	public void changeWeapon(int weaponNum){
		String str = "";
		if(weaponNum==1) str="Cannon";
		else if(weaponNum==2) str = "Guided Missile";
		else if(weaponNum==3) str = "Shotgun";
		else if(weaponNum==4) str = "Mines (?)";
		this.weaponField.setText(str);
	}
	public void changeWeapon(@SuppressWarnings("unused") int weaponNum, int bombsRemaining){
		this.weaponField.setText("Mines ("+bombsRemaining+")");
	}

	/**
	 * Prints for test.
	 */
	public void testPrint() {
		System.out.println("TEST");
	}

}
