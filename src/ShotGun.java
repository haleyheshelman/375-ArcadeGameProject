import java.awt.geom.Point2D;

public class ShotGun extends Projectile {

	public ShotGun(Point2D centerPoint) {
		super(centerPoint);
	}

	private void generateBullet(double xVel) {
		Bullet b = new Bullet(this.getCenterPoint());
		b.setVelocityX(xVel);
		getGame().addObject(b);

	}

	static ShotGun generateAtPixels(double x, double y) {
		ShotGun sh = new ShotGun(new Point2D.Double(x, y));
		for (int direction : new int[] { -1, 0, 1 }) {
			sh.generateBullet(direction);
		}
		return sh;
	}
}
