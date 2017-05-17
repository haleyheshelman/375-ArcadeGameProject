import java.awt.Color;

public class ExplodingBullet extends Projectile {

	private static final int INITIAL_DAMAGE = 0;
	private static final int EXPLODED_DAMAGE = 5;

	public ExplodingBullet() {
		super();
	}

	@Override
	void setUniques() {
		this.setDamage(INITIAL_DAMAGE);
		this.setColor(Color.CYAN);
	}

	public ExplodingBullet(double px, double py) {
		super(px, py);
	}

	@Override
	protected void die() {
		super.die();
		this.setVelocityX(0);
		this.setVelocityY(0);
		this.explode();
	}

	private void explode() {
		generateBullet(0, -8);
		generateBullet(-6, -6);
		generateBullet(-8, 0);
		generateBullet(-6, 6);
		generateBullet(0, 8);
		generateBullet(6, 6);
		generateBullet(8, 0);
		generateBullet(6, -6);

	}

	private void generateBullet(double xVel, double yVel) {
		Bullet b = new Bullet(this.getCenterPoint().getX(),
				this.getCenterPoint().getY(), EXPLODED_DAMAGE, xVel);
		b.setVelocityY(yVel);
		b.setColor(Color.CYAN);
		getGame().addObject(b);
	}

}
