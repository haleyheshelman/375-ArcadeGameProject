import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Represents a bomb which can't move but kill the Monsters when they touch it.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
public class Bomb extends Projectile {

	private static final int BOMB_DIAMETER = 10;
	private static final int BOMB_DAMAGE = 50;
	private static final double X_ADJUST = 1.5;
	public static final int DEF_STARTING_BOMBS = 5;
	private static int bombsRemaining = 5;

	public Bomb() {
		super();
	}

	/**
	 * Constructs a Bomb in the game.
	 *
	 * @param game
	 * @param centerPoint
	 */
	public Bomb(double px, double py) {
		super(px, py);
	}

	@Override
	void setUniques() {
		this.setColor(Color.WHITE);
		this.setVelocityX(0);
		this.setVelocityY(0);
		this.setDamage(BOMB_DAMAGE);
	}

	/**
	 * Gets the shape of the bomb.
	 */
	@Override
	public Shape getShape() {
		double x = getCenterPoint().getX();
		double y = getCenterPoint().getY();

		return new Ellipse2D.Double(x - BOMB_DIAMETER / 2 - X_ADJUST,
				y - BOMB_DIAMETER, BOMB_DIAMETER, BOMB_DIAMETER);
	}

	@Override
	public void add() {
		if (bombsRemaining > 0 && getGame().countBomb() < 5) {
			getGame().addObject(this);
			bombsRemaining--;
			Main.scoreboard.changeWeapon(4, bombsRemaining);
		}
	}

	protected static int getBombsRemaining() {
		return bombsRemaining;
	}

	public static void setBombsRemaining(int i) {
		bombsRemaining = i;
	}

	public static void decrementBombsRemaining() {
		setBombsRemaining(getBombsRemaining() - 1);
		if (getBombsRemaining() < 0)
			setBombsRemaining(0);
	}
}