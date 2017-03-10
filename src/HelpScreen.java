import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * This is probably the most self-explainatory class in existence. It's a help
 * screen. For helping.
 *
 * @author deradaam, lub and verlaqd. Created Nov 11, 2015.
 */
@SuppressWarnings("serial")
public class HelpScreen extends JFrame {

	/**
	 * Constructs the default HelpScreen.
	 *
	 * @throws HeadlessException
	 */
	public HelpScreen() throws HeadlessException {
		this.setSize(280, 180);

		String str = "  Use arrow keys to move the ship.\n  Hit 1/2/3/4 to select a weapon. S to fire.\n  P to pause.\n\n  Debugging: \n  U/D to go up or down a level \n  +/- to adjust refresh rate\n  g to enable or disable graphics";
		JTextArea helpText = new JTextArea(str);
		helpText.setBackground(Color.BLACK);
		helpText.setFont(new Font("Times", Font.BOLD, 12));
		helpText.setOpaque(true);
		helpText.setForeground(Color.WHITE);
		helpText.setLineWrap(true);
		helpText.setWrapStyleWord(true);
		helpText.setAlignmentX(Component.CENTER_ALIGNMENT);
		helpText.setEditable(false);
		this.add(helpText);
		this.setTitle("Instructions");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Constructs the HelpScreen with given GraphicsConfiguration.
	 *
	 * @param gc
	 */
	public HelpScreen(GraphicsConfiguration gc) {
		super(gc);
	}

	/**
	 * Constructs the HelpScreen with given String.
	 *
	 * @param title
	 * @throws HeadlessException
	 */
	public HelpScreen(String title) throws HeadlessException {
		super(title);

	}

	/**
	 * Constructs the HelpScreen with given GraphicsConfiguration and String.
	 *
	 * @param title
	 * @param gc
	 */
	public HelpScreen(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

}
