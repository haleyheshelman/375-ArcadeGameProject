import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Represents the ship in the arcade game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public class Ship extends Dieable {

	// playable area
	private static final int MAX_HEIGHT = 218;

	// movement flags
	private int rightMove = 0;
	private int leftMove = 0;
	private int upMove = 0;
	private int downMove = 0;
	private int projectileType = 1;
	protected int bombsRemaining = 5;
	private static long lastFiredTime = System.currentTimeMillis();

	/**
	 * Creates a new Ship at the specified coordinates, with velocity = 4 and
	 * color = RED
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Ship(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.setColor(Color.RED);
		this.setVelocityX(2.5);
		this.setVelocityY(4);
	}

	public int getProjectileType() {
		return this.projectileType;
	}

	public void setProjectileType(int projectileType) {
		this.projectileType = projectileType;
		if (projectileType == 4) {
			Main.scoreboard.changeWeapon(4, this.bombsRemaining);
		} else {
			Main.scoreboard.changeWeapon(projectileType);
		}
	}

	/**
	 * Called on every refresh, this method handles ship movement in a general
	 * sense. It uses other methods that determine whether or not the ship can
	 * move.
	 * 
	 */
	@Override
	public void move() {
		double curX = this.getX();
		double curY = this.getY();
		if (!this.stillAlive(curX, curY)) {
			this.die();
			return;
		}

		// this is the next X value, assuming we can actually move in the
		// desired X direction.
		double nextX = curX + this.getVelocityX()
				* (this.rightMove - this.leftMove);

		// ditto for Y
		double nextY = curY + this.getVelocityY()
				* (this.downMove - this.upMove);

		// decides if we should move to the new X/Y or stay at the current one
		double newX = this.chooseNewX(curX, nextX, curY);
		double newY = this.chooseNewY(curY, nextY, curX);

		this.setTLPoint(new Point2D.Double(newX, newY));

	}

	/**
	 * See if the ship is still alive--in other words, did it hit a monster?
	 *
	 * @param curX
	 * @param curY
	 * @return
	 */
	private boolean stillAlive(double curX, double curY) {
		// System.out.println(this.getGame().getMonsters().size());
		// See if the ship is dead
		if (this.intersectsObject(this.getGame().getMonsters()) == null) {
			return true;
		}
		// if in contact with a monster, return false
		// System.out.println("false");
		return false;
	}

	/**
	 * The ChooseNew functions take the current point as well as the next (X or
	 * Y) coordinate, and determine if the ship should be allowed to move in
	 * that direction. If we can move in that direction, it returns the
	 * new(X/Y). Otherwise, it returns the current one.
	 * 
	 * @param curY
	 * @param nextY
	 * @param curX
	 * @return
	 */

	private double chooseNewY(double curY, double nextY, double curX) {
		Point2D curTL = this.getTLPoint(); // save current top left point
		this.setTLPoint(new Point2D.Double(curX, nextY));

		Dieable intersectedObject = this.intersectsObject(this.getGame()
				.getMushrooms());

		if (this.getGame().inGameY(nextY) && nextY >= MAX_HEIGHT
				&& intersectedObject == null) {
			this.setTLPoint(curTL);
			return nextY;
		}

		this.setTLPoint(curTL);
		return curY;
	}

	private double chooseNewX(double curX, double nextX, double curY) {
		Point2D curTL = this.getTLPoint();
		this.setTLPoint(new Point2D.Double(nextX, curY));

		Dieable intersectedObject = this.intersectsObject(this.getGame()
				.getMushrooms());

		if (this.getGame().inGameX(nextX, this.gap, this.width)
				&& intersectedObject == null) {
			this.setTLPoint(curTL);
			return nextX;
		}

		this.setTLPoint(curTL);
		return curX;
	}

	/**
	 * These switch the directional movement parameters. They're called by the
	 * press and release of the arrow keys.
	 * 
	 * @param newM
	 */
	public void rightSwitch(int newM) {
		this.rightMove = newM;
	}

	public void leftSwitch(int newM) {
		this.leftMove = newM;
	}

	public void upSwitch(int newM) {
		this.upMove = newM;
	}

	public void downSwitch(int newM) {
		this.downMove = newM;
	}

	/**
	 * 
	 * Fire projectile
	 *
	 */
	public void fireProjectile() {

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime < 200)
			return;
		lastFiredTime = currentTime;
		double coordinateX = this.getCenterPoint().getX();
		double coordinateY = this.getCenterPoint().getY() - 10;

		Point2D.Double projectilePoint = new Point2D.Double(coordinateX,
				coordinateY);

		if (this.getProjectileType() == 1) {
			// first projectile type
			this.getGame().addObject(
					new Bullet(this.getGame(), projectilePoint));

		} else if (this.getProjectileType() == 2) {
			// second projectile type
			this.getGame().addObject(
					new Missile(this.getGame(), projectilePoint));

		} else if (this.getProjectileType() == 3) {
			// third projectile type

			this.getGame().addObject(
					new Bullet(this.getGame(), projectilePoint, 8));
			this.getGame().addObject(
					new Bullet(this.getGame(), projectilePoint, 8, -1));
			this.getGame().addObject(
					new Bullet(this.getGame(), projectilePoint, 8, 1));
		} else if (this.getProjectileType() == 4) {
			if (this.bombsRemaining > 0 && this.getGame().countBomb() < 5) {
				this.getGame().addObject(
						new Bomb(this.getGame(), projectilePoint));
				this.bombsRemaining--;
				Main.scoreboard.changeWeapon(4, this.bombsRemaining);
			}
		}
	}

	/**
	 * Gets the shape of this ship.
	 */
	@Override
	public Shape getShape() {

		if (this.getGame().USE_IMAGES) {

			return super.getShape();
		}

		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
		// return new Arc2D.Double(x + this.gap, y + this.gap, this.width, 2
		// * this.height + this.gap, 0, 180, Arc2D.CHORD);
	}
}
