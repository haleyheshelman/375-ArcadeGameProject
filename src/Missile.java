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
	public Missile(ArcadeGame game, Point2D centerPoint) {
		super(game, centerPoint, MISSILE_DAMAGE);
		this.setVelocityY(PROJECTILE_2_VELOCITY);
		this.setColor(Color.RED);
	}

	/**
	 * It can follow the ship with same X coordinate.
	 */
	@Override 
	public void move(){
		double nextX = this.getGame().getShip().getCenterPoint().getX();
		double nextY = this.getCenterPoint().getY() + this.getVelocityY();
		this.setCenterPoint(new Point2D.Double(nextX, nextY));
		if (this.checkHit()) {
			this.die();
		}
	}
}
