import java.awt.geom.Point2D;

/**
 * Represents the Bullet in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Bullet extends Projectile {

	// default damage if none is specified
	public Bullet(ArcadeGame game, Point2D centerPoint) {
		this(game, centerPoint, Projectile.DEFAULT_PROJECTILE_DAMAGE);
	}

	// makes a bullet at the point with the given damage
	public Bullet(ArcadeGame game, Point2D centerPoint, int damage) {
		super(game, centerPoint, damage);
	}

	// makes a bullet in the game with given centerPoint, damage and X velocity.
	public Bullet(ArcadeGame game, Point2D centerPoint, int damage, double velX) {
		super(game, centerPoint, damage);
		this.setVelocityX(velX);
	}

}
