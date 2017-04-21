import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represents a projectile in the game to hit the monsters and mushrooms.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public abstract class Projectile extends Dieable {

	private static final int PROJECTILE_WIDTH = 4;
	private static final int PROJECTILE_HEIGHT = 9;
	private static final int DEFAULT_PROJECTILE_VELOCITY = -8;
	protected static final int DEFAULT_PROJECTILE_DAMAGE = 10;
	protected static final int PROJECTILE_Y_ADJUST = 4;
	private int damage = DEFAULT_PROJECTILE_DAMAGE;

	public Projectile() {
		this(-100, -100);
	}

	/**
	 * Creates a new projectile at the given coordinates, then calls setUniques
	 *
	 * @param px
	 * @param py
	 */
	public Projectile(double px, double py) {
		super(px, py);
		this.setVelocityY(DEFAULT_PROJECTILE_VELOCITY);
		this.setVelocityX(0);
		this.setCenterPoint(new Point2D.Double(px, py));
		this.setColor(Color.YELLOW);
		this.height = PROJECTILE_HEIGHT;
		this.width = PROJECTILE_WIDTH;
		this.gap = (ArcadeGame.GRID_SIZE - this.width) * 0.5;
		this.topGap = (ArcadeGame.GRID_SIZE - this.height) * 0.5;
		setUniques();
	}

	abstract void setUniques();

	/**
	 * Moves in the game at specific velocity.
	 */
	@Override
	public void move() {

		// double nextX = this.getCenterPoint().getX() + this.getVelocityX();
		// double nextY = this.getCenterPoint().getY() + this.getVelocityY();
		// this.setCenterPoint(new Point2D.Double(nextX, nextY));

		double nextX = this.getX() + this.getVelocityX();
		double nextY = this.getY() + this.getVelocityY();
		this.setTLPoint(new Point2D.Double(nextX, nextY));

		if (this.checkHit()) {
			this.die();
		}
		if (!this.getGame().inGameY(this.getY() + this.height)) {
			this.die();
		}
	}

	/**
	 * Checks whether the projectile hits the object in the game and returns
	 * true if it hits the object.
	 *
	 * @param objsToCheck
	 * @return
	 */
	public boolean checkHit() {
		ArrayList<Dieable> objsToCheck = new ArrayList<>();
		objsToCheck.addAll(getGame().getMonsters());
		objsToCheck.addAll(getGame().getMushrooms());
		boolean hit = false;
		Dieable intersectObject = this.intersectsObject(objsToCheck);
		if (intersectObject != null) {
			hit = true;
			intersectObject.removeHealth(this.damage);
		}

		return hit;
	}

	protected void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Returns the shape of the projectile. Constructed a little differently
	 * than the others so it's more intuitive.
	 * 
	 * @return
	 */
	@Override
	public Shape getShape() {

		double x = getCenterPoint().getX();
		double y = getCenterPoint().getY();

		return new Rectangle2D.Double(x - PROJECTILE_WIDTH / 2,
				y - PROJECTILE_HEIGHT / 2 + PROJECTILE_Y_ADJUST,
				PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
	}

	/**
	 * Used to create a projectile from a Class
	 *
	 * @param projectile
	 * @param center_x
	 * @param center_y
	 */
	static void spawn(Class<? extends Projectile> projectile, double center_x,
			double center_y) {
		try {
			Projectile p = projectile.newInstance();
			p.setCenterPoint(new Point2D.Double(center_x, center_y));
			p.add();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
