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
public class Projectile extends Dieable {

	private static final int PROJECTILE_WIDTH = 4;
	private static final int PROJECTILE_HEIGHT = 9;
	private static final int DEFAULT_PROJECTILE_VELOCITY = -8;
	protected static final int DEFAULT_PROJECTILE_DAMAGE = 10;
	protected static final int PROJECTILE_Y_ADJUST=4;
	private int damage;

	/**
	 * Constructs a projectile with given damage at the specified centerpoint.
	 * If unspecified, default damage is 10.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 * @param centerPoint
	 * @param damage
	 */
	public Projectile(ArcadeGame game, Point2D centerPoint) {
		this(game, centerPoint, DEFAULT_PROJECTILE_DAMAGE);
	}

	public Projectile(ArcadeGame game, Point2D centerPoint, int damage) {

		super(game, 10, 16);
		this.setVelocityY(DEFAULT_PROJECTILE_VELOCITY);
		this.setVelocityX(0);
		this.setCenterPoint(centerPoint);
		this.setColor(Color.YELLOW);
		this.damage = damage;
		this.height = PROJECTILE_HEIGHT;
		this.width = PROJECTILE_WIDTH;
		this.gap = (GRID_SIZE - this.width) * 0.5;
		this.topGap=(GRID_SIZE-this.height)*0.5;

	}

	/**
	 * Moves in the game at specific velocity.
	 */
	@Override
	public void move() {

		double nextX = this.getCenterPoint().getX() + this.getVelocityX();
		double nextY = this.getCenterPoint().getY() + this.getVelocityY();
		this.setCenterPoint(new Point2D.Double(nextX, nextY));
		
		if (this.checkHit()) {
			this.die();
		}
		if (!this.getGame().inGameY(this.getY()+this.height)) {
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
		objsToCheck.addAll(this.getGame().getMonsters());
		objsToCheck.addAll(this.getGame().getMushrooms());
		boolean hit = false;
		Dieable intersectObject = this.intersectsObject(objsToCheck);
		if (intersectObject != null) {
			hit = true;
			intersectObject.removeHealth(this.damage);
			
		}
		
		return hit;
	}

	/**
	 * Returns the shape of the projectile.
	 * Constructed a little differently than the others so it's more intuitive.
	 * 
	 * @return
	 */
	@Override
	public Shape getShape() {

		double x = getCenterPoint().getX();
		double y = getCenterPoint().getY();

		return new Rectangle2D.Double(x - PROJECTILE_WIDTH / 2, y
				- PROJECTILE_HEIGHT / 2+PROJECTILE_Y_ADJUST, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
	}

}
