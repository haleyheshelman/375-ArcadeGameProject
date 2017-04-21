import java.awt.Color;
import java.awt.geom.Point2D;

public class ExplodingBullet extends Projectile {

	private static final int INITIAL_DAMAGE = 0;
	private static final int EXPLODED_DAMAGE = 5;

	public ExplodingBullet(Point2D centerPoint) {
		super(centerPoint, INITIAL_DAMAGE);
		this.setColor(Color.BLUE);
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
		Bullet b = new Bullet(this.getCenterPoint(), EXPLODED_DAMAGE);
		b.setVelocityX(xVel);
		b.setVelocityY(yVel);
		b.setColor(Color.CYAN);

		getGame().addObject(b);
	}

	static ExplodingBullet generateAtPixels(double x, double y) {
		ExplodingBullet exploding_bullet = new ExplodingBullet(new Point2D.Double(x, y));
		getGame().addObject(exploding_bullet);
		return exploding_bullet;
	}

}
