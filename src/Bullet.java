import java.awt.geom.Point2D;

/**
 * Represents the Bullet in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Bullet extends Projectile {

	// default damage if none is specified
	public Bullet(Point2D centerPoint) {
		this(centerPoint, Projectile.DEFAULT_PROJECTILE_DAMAGE);
	}

	// makes a bullet at the point with the given damage
	public Bullet(Point2D centerPoint, int damage) {
		super(centerPoint, damage);
	}

	// makes a bullet in the game with given centerPoint, damage and X velocity.
	public Bullet(Point2D centerPoint, int damage, double velX) {
		super(centerPoint, damage);
		this.setVelocityX(velX);
	}

	@Override
	void generateAtPixels_override(double x, double y) {
		getGame().addObject(new Bullet(new Point2D.Double(x,y)));
		
	}

}
