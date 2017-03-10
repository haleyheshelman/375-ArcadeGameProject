import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This inner class handles keyboard events on the outer class's game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public class ArcadeGameKeyListener implements KeyListener {

	protected ArcadeGame game;
	protected ArcadeGameComponent AGC;
	protected boolean alreadyFiring = false;
	public ArcadeGameKeyListener() {
		this.game = null;
		this.AGC = null;

	}
	/**
	 * Constructs a listener can read the keyboard input from the user and
	 * do some specific behaviors in the game.
	 *
	 * @param game
	 */
	public ArcadeGameKeyListener(ArcadeGameComponent AGC, ArcadeGame game) {
		this.game = game;
		this.AGC = AGC;

	}

	/**
	 * Does some specific behaviors in the game when the user presses the
	 * specific keyboard button.
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		/*
		 * Info for debugging
		 */
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("I")) {
			System.out.println("MIPA:" + this.game.mushroomsInPlayerArea
					+ ", CC:" + this.game.MM.numCentipedes + "\n ship TLC:"
					+ this.game.getShip().getX() + ","
					+ this.game.getShip().getY());
			System.out.println(this.game.MM.numSpiders+" spiders");
			System.out.println(this.game.MM.numFleas+" fleas");
			System.out.println(this.game.MM.scorpionIsAlive+" "+this.game.MM.scorpionsAllowed);

		}
		/*
		 * Pause
		 */
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("P")) {
			this.game.pauseButtonHit();
		}

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left")) {
			this.game.getShip().leftSwitch(1);
		}

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right")) {
			this.game.getShip().rightSwitch(1);
		}

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Up")) {
			this.game.getShip().upSwitch(1);
		}

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Down")) {
			this.game.getShip().downSwitch(1);
		}

		/*
		 * go up a level
		 */
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("U")) {

			try {
				this.game.createLevel(1);
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		/*
		 * Go down a level
		 */

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
			try {
				this.game.createLevel(-1);
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		/*
		 * Shoot
		 */

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")
				&& this.alreadyFiring == false) {

			this.game.getShip().fireProjectile();

			this.alreadyFiring = true;
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("1")) {
			this.game.getShip().setProjectileType(1);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("2")) {
			this.game.getShip().setProjectileType(2);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("3")) {
			this.game.getShip().setProjectileType(3);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("4")) {
			this.game.getShip().setProjectileType(4);
		}

	}

	/**
	 * Does some specific behaviors in the game when the user releases the
	 * specific keyboard button.
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Left")) {
			this.game.getShip().leftSwitch(0);

		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Right")) {
			this.game.getShip().rightSwitch(0);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Up")) {
			this.game.getShip().upSwitch(0);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("Down")) {
			this.game.getShip().downSwitch(0);
		}
		if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
			this.alreadyFiring = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '+') {
			this.AGC.changeFPS(5);
			System.out.println("Speeding up");
		}
		if (e.getKeyChar() == '-') {
			this.AGC.changeFPS(-5);

			System.out.println("Slowing down");
		}

		if (e.getKeyChar() == 'h') {
			this.game.helpButtonHit();
//			HelpScreen hs = new HelpScreen();
//			hs.setVisible(true);
			
		}
		if (e.getKeyChar() == 'g') {
			this.game.USE_IMAGES=(!this.game.USE_IMAGES);
			System.out.println("Enabled/disabled graphics");
		}
	}
}
