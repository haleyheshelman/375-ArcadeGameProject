import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Represents a bomb which can't move but kill the Monsters when they touch it.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
public class Bomb extends Projectile {

	private static final int BOMB_DIAMETER = 10;
	private static final int BOMB_DAMAGE = 50;
	private static final double X_ADJUST = 1.5;

	/**
	 * Constructs a Bomb in the game.
	 *
	 * @param game
	 * @param centerPoint
	 */
	public Bomb(ArcadeGame game, Point2D centerPoint) {
		super(game, centerPoint, BOMB_DAMAGE);
		this.setColor(Color.WHITE);
		this.setVelocityX(0);
		this.setVelocityY(0);
	}

	/**
	 * Gets the shape of the bomb.
	 */
	@Override
	public Shape getShape() {
		double x = getCenterPoint().getX();
		double y = getCenterPoint().getY();

		return new Ellipse2D.Double(x - BOMB_DIAMETER / 2 - X_ADJUST, y - BOMB_DIAMETER, BOMB_DIAMETER, BOMB_DIAMETER);
	}
}