import java.awt.Color;
import java.awt.geom.Point2D;

public class ExplodingBullet extends Projectile {

	private static final int INITIAL_DAMAGE = 0;
	private static final int EXPLODED_DAMAGE = 5;

	public ExplodingBullet(ArcadeGame game, Point2D centerPoint) {
		super(game, centerPoint, INITIAL_DAMAGE);
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
		Bullet b = new Bullet(this.getGame(), this.getCenterPoint(),
				EXPLODED_DAMAGE);
		b.setVelocityX(xVel);
		b.setVelocityY(yVel);
		b.setColor(Color.CYAN);

		this.getGame().addObject(b);
	}

}
