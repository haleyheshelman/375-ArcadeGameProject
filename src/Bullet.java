/**
 * Represents the Bullet in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Bullet extends Projectile {

	// default projectile damage if none is specified
	public Bullet() {
		super();
	}

	public Bullet(double px, double py) {
		this(px, py, Projectile.DEFAULT_PROJECTILE_DAMAGE);
	}

	// makes a bullet at the point with the given damage
	public Bullet(double px, double py, int damage) {
		this(px, py, damage, 0);
	}

	// makes a bullet in the game with given centerPoint, damage and X velocity.
	public Bullet(double px, double py, int damage, double velX) {
		super(px, py);
		this.setVelocityX(velX);
		this.setDamage(damage);
	}

	@Override
	void setUniques() {
		// nothing special for bullets, really
	}

}
