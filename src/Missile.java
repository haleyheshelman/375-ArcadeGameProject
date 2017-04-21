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

	public Missile(Point2D centerPoint) {
		super(centerPoint, MISSILE_DAMAGE);
		this.setVelocityY(PROJECTILE_2_VELOCITY);
		this.setColor(Color.RED);
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
	
	static Missile generateAtPixels(double x, double y){
		Missile missile = new Missile(new Point2D.Double(x,y));
		getGame().addObject(missile);
		return missile;
	}
}
