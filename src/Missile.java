import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Represents a missile in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Missile extends Projectile {
	private final static int MISSILE_DAMAGE = 20;

	private static final int PROJECTILE_2_VELOCITY = -3;

	public Missile() {
		super();
	}

	public Missile(double px, double py) {
		super(px, py);
	}

	/**
	 * It follows the ship with same X coordinate.
	 */
	@Override
	public void move() {
		double nextX = getGame().getShip().getCenterPoint().getX();
		double nextY = this.getCenterPoint().getY() + this.getVelocityY();
		this.setCenterPoint(new Point2D.Double(nextX, nextY));
		if (this.checkHit()) {
			this.die();
		}
	}


	@Override
	void setUniques() {
		this.setVelocityY(PROJECTILE_2_VELOCITY);
		this.setColor(Color.RED);
		this.setDamage(MISSILE_DAMAGE);
	}
}
