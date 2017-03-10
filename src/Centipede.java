import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Represents a specific kind of monster in the arcade game.
 *
 * @author deradaam,lub and verlaqd. Created Nov 6, 2015.
 */
public class Centipede extends Monster {
	private static final double yShiftMax = GRID_SIZE / DEF_MONST_VEL;
	private static final int CENTIPEDE_SCORE = 400;

	protected int xDirection = 1; // 1 is right, -1 is left
	private int yDirection = 1; // 1 is down, -1 is up

	private double yShiftCount = 0;
	private boolean yShifting = false;

	private static final double POISON_SHIFT_MAX = GRID_SIZE / DEF_MONST_VEL;
	private final static double POISON_SHIFT_OFFSET = (Dieable.GAP_SIZE * 2);
	private double firstPoisonMovement = 1;

	private double poisonShiftCount = 0;

	protected boolean poisoned = false;

	protected Mushroom ignoredMushroom;
	private boolean markedForDeath = false;
	private double markedX;
	private double markedY;
	private int poisonDir = 0;
	private int poisonXDirOriginal = 0;

	/**
	 * Constructs the Centipede at given position in the arcade game.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Centipede(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.setColor(Color.BLACK);
		// System.outprintln("Making new centipede");
		// game.numCentipedes++;
		this.bounty = CENTIPEDE_SCORE;

	}

	/**
	 * Movement method. Basic movement overview:
	 * 1. If !yShifting, check next X. If it's clear, then move to that
	 * position. If not, make yShifting true.
	 * 2. If yShifting, then go in the specified direction (up or down) enough
	 * times to correctly travel one grid height.
	 * 3. Once done moving up or down, make yShifting false again.
	 * 
	 */

	@Override
	public void move() {
		if (this.markedForDeath)
			tryMushroomDrop();
		if (this.poisoned) {
			this.poisonMove();
			return;
		}
		double curX = this.getX();
		double curY = this.getY();
		double newX = curX;
		double newY = curY;

		// We want to move in the x direction IFF we're not going up or down

		if (!this.yShifting) {
			// Horizontal movement
			double nextX = curX + this.getVelocityX() * this.xDirection;
			newX = newX(curX, curY, nextX);

		} else {
			// Vertical movement
			double nextY = curY + this.getVelocityY() * this.yDirection;
			newY = newY(curX, curY, nextY);
		}

		this.setTLPoint(new Point2D.Double(newX, newY));

	}

	/**
	 * 
	 * Handles centipede movement while poisoned. Switches the poisonDir
	 * variable (which determines if we're moving down, right, or left).
	 *
	 */
	public void poisonMove() {
		// left, down, right, down, left...
		double xOffset = 0;
		if (this.poisonShiftCount < POISON_SHIFT_MAX - 1) {
			this.poisonShiftCount++;
		} else {
			this.poisonShiftCount = 0;
			this.poisonDir = (this.poisonDir + 1) % 4;

			if (this.poisonDir == 0) {
				this.yDirection = 1;
				this.xDirection = 0;
			} else if (this.poisonDir == 1) {
				this.yDirection = 0;
				this.xDirection = this.poisonXDirOriginal;
				if (this.firstPoisonMovement == 1) {
					xOffset = this.firstPoisonMovement
							* Centipede.POISON_SHIFT_OFFSET;
					this.firstPoisonMovement = 0;
				}
			} else if (this.poisonDir == 2) {
				this.yDirection = 1;
				this.xDirection = 0;
			} else if (this.poisonDir == 3) {
				this.yDirection = 0;
				this.xDirection = this.poisonXDirOriginal * -1;
			}
		}
//		System.out.println(xOffset);
		this.setTLPoint(new Point2D.Double(this.getX()
				+ (this.getVelocityX() * this.xDirection - xOffset), this
				.getTLPoint().getY() + this.getVelocityY() * this.yDirection));

		if ((int) (this.getY() / GRID_SIZE) == ArcadeGame.BOTTOM_PLAYER_AREA) {
			this.poisoned = false;
			this.yShifting = false;
			this.yDirection = -1;
			this.xDirection = this.poisonXDirOriginal;
			this.firstPoisonMovement = 1;
		}
	}

	/**
	 * 
	 * Called if the centipede hits a poison mushroom. Prepares the centipede
	 * for poisonMove
	 *
	 */

	public void makePoisoned() {
		this.poisoned = true;
		this.yDirection = 1;
		this.poisonXDirOriginal = this.xDirection;
		this.xDirection = 0;
		this.poisonDir = 0;
		this.poisonShiftCount = -1;
	}

	/**
	 * 
	 * Tries to drop a mushroom. Called only if markedForDeath.
	 * If it's gone more than 20 pixels since being shot, die without dropping.
	 * If it's going to drop on top of another mushroom, die without dropping.
	 * Otherwise, drop when it's on a grid exactly.
	 */
	private void tryMushroomDrop() {
		double curX = this.getX();
		double curY = this.getY();
		if (Math.abs(curX - this.markedX) > ArcadeGame.GRID_SIZE
				|| Math.abs(curY - this.markedY) > ArcadeGame.GRID_SIZE) {
			this.getGame().removeObject(this);
			return;
		}

		double curXGrid = curX / ArcadeGame.GRID_SIZE;
		double curYGrid = curY / ArcadeGame.GRID_SIZE;

		if (curXGrid % 1 == 0 && curYGrid % 1 == 0) {

			Mushroom droppedMushroom = new Mushroom(this.getGame(),
					(int) curXGrid, (int) curYGrid);
			if (droppedMushroom.intersectsObject(droppedMushroom.getGame()
					.getMushrooms()) == null)
				this.getGame().addObject(droppedMushroom);
			this.getGame().removeObject(this);
		}
	}

	/**
	 * Chooses and returns new x. Flips x direction if it hits something or goes
	 * out of bounds, and initiates y shift.
	 * 
	 * @param curX
	 * @param curY
	 * @param nextX
	 * @return
	 */
	public double newX(double curX, double curY, double nextX) {

		Point2D curTL = this.getTLPoint();
		this.setTLPoint(new Point2D.Double(nextX, curY));

		Dieable intersectedObject = this.intersectsObject(this.getGame()
				.getMushrooms(), this.ignoredMushroom);

		if (this.getGame().inGameX(nextX, this.gap, this.width)
				&& intersectedObject == null) {
			this.setTLPoint(curTL);

			return nextX;
		}
		// if we start offscreen, keep going
		if (curX < 0)
			return nextX;
		// only need to check for poison mushrooms if scorpions are allowed
		if (intersectedObject != null && MonsterManager.scorpionsAllowed) {
			Mushroom intersectedMushroom = (Mushroom) intersectedObject;
			if (intersectedMushroom.isPoisonous()) {
				this.makePoisoned();
				return curX;
			}
		}

		// flip x direction
		this.xDirection *= -1;
		// initiate y shift
		this.yShifting = true;
		this.setTLPoint(curTL);
		return curX;

	}

	/**
	 * Chooses and returns new y. Uses yShiftCount to ensure that we always end
	 * up on grid lines.
	 * 
	 * @param curX
	 * @param curY
	 * @param nextY
	 * @return
	 */

	public double newY(double curX, double curY, double nextY) {

		// if we're NOT done yShifting, keep going
		if (this.yShiftCount < yShiftMax) {
			this.yShiftCount++;
			return nextY;
		}

		// If we ARE done yShifting...

		// reset yShiftCount
		this.yShiftCount = 0;
		// stop yShifting
		this.yShifting = false;

		// if we landed on top of a mushroom, we need to ignore it:
		this.ignoredMushroom = (Mushroom) this.intersectsObject(this.getGame()
				.getMushrooms());

		if (this.ignoredMushroom != null && this.ignoredMushroom.isPoisonous())
			this.ignoredMushroom = null;

		// If we're at the top or bottom, set direction accordingly and round
		// the y value to take care of boundary/pixel issues.
		if ((int) curY / GRID_SIZE == ArcadeGame.BOTTOM_PLAYER_AREA) {
			this.yDirection = -1;
			return ArcadeGame.BOTTOM_PLAYER_AREA * GRID_SIZE;
		} else if ((int) curY / GRID_SIZE == 0) {
			this.yDirection = 1;
			return 0;
		}

		return curY;
	}

	/**
	 * mark for death, record current coordinates
	 */
	@Override
	public void die() {
		this.markedForDeath = true;
		this.markedX = this.getX();
		this.markedY = this.getY();
	}

	/**
	 * Gets the shape of the Centipede.
	 * 
	 * @return
	 */
	@Override
	public Shape getShape() {
		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
	}

}
