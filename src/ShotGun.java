public class ShotGun extends Projectile {

	public static final int DEF_SHOTGUN_DMG = Bullet.DEFAULT_PROJECTILE_DAMAGE
			/ 3;

	public ShotGun() {
		super();
	}

	public ShotGun(double px, double py) {
		super(px, py);
	}

	private void generateBullet(double xVel) {
		Bullet b = new Bullet(this.getCenterPoint().getX(),
				this.getCenterPoint().getY());
		b.setVelocityX(xVel);
		getGame().addObject(b);

	}

	@Override
	void setUniques() {
		setDamage(DEF_SHOTGUN_DMG);
	}

	@Override
	public void add() {
		for (int direction : new int[] { -1, 0, 1 }) {
			this.generateBullet(direction);
		}
	}
}
