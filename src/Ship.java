import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	private Class<? extends Projectile> projectileType = Bullet.class;
	private static long lastFiredTime = System.currentTimeMillis();

	/**
	 * Creates a new Ship at the specified coordinates, with velocity = 4 and
	 * color = RED
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Ship(int gridX, int gridY) {
		super(gridX, gridY);
		this.setColor(Color.RED);
		this.setVelocityX(2.5);
		this.setVelocityY(4);
	}

	public Class<? extends Projectile> getProjectileType() {
		return this.projectileType;
	}

	public void setProjectileType(Class<? extends Projectile> type) {
		this.projectileType = type;
		if (this.projectileType.getClass().equals(Bomb.class)) {
			Main.scoreboard.changeWeapon(4, Bomb.bombsRemaining);
		} else {
			Main.scoreboard.changeWeapon(type);
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
		double nextX = curX
				+ this.getVelocityX() * (this.rightMove - this.leftMove);

		// ditto for Y
		double nextY = curY
				+ this.getVelocityY() * (this.downMove - this.upMove);

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
		// See if the ship is dead
		if (this.intersectsObject(getGame().getMonsters()) == null) {
			return true;
		}
		// if in contact with a monster, return false
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

		Dieable intersectedObject = this
				.intersectsObject(getGame().getMushrooms());

		if (getGame().inGameY(nextY) && nextY >= MAX_HEIGHT
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

		Dieable intersectedObject = this
				.intersectsObject(getGame().getMushrooms());

		if (getGame().inGameX(nextX, this.gap, this.width)
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

	public boolean firedTooRecently(long currentTime) {
		return currentTime - lastFiredTime < 200;
	}

	public void fireProjectile() {
		long currentTime = System.currentTimeMillis();

		if (firedTooRecently(currentTime)) {
			return;
		}
		lastFiredTime = currentTime;
		double coordinateX = this.getCenterPoint().getX();
		double coordinateY = this.getCenterPoint().getY() - 10;

		Projectile.spawn(getProjectileType(), coordinateX, coordinateY);
	}

	/**
	 * Gets the shape of this ship.
	 */
	@Override
	public Shape getShape() {

		if (getGame().USE_IMAGES) {

			return super.getShape();
		}

		double x = getX();
		double y = getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
	}

	@Override
	public BufferedImage getImage() throws IOException {
		return ImageIO.read(new File("shipFinal.png"));
	}

	static Ship generateAtGrid(int x, int y) {
		Ship ship = new Ship(x, y);
		getGame().setShip(ship);
		return ship;
	}

	@Override
	void add() {
		// TODO Auto-generated method stub.

	}
}
