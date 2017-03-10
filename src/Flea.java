import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Represents the Flea in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Flea extends Monster {

	/**
	 * 
	 * Fleas go down when there are less than 5 (to start, will be higher in
	 * harder levels) mushrooms in the playable area.
	 * Fleas are initialized at a random gridX with the standard downward
	 * velocity.
	 *
	 * We look at both the non-playable and playable areas, and use random
	 * numbers to find where in those sections we'll drop the mushroom (one in
	 * each section).
	 * Flea will be called by ArcadeGame; AG will determine where the mushroom
	 * is dropped.
	 * 
	 * When shot the first time, the flea increases velocity. Second shot, it
	 * dies.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */

	private final static int DEFAULT_FLEA_HEALTH = 20;
	private final static int FLEA_SCORE = 500;

	private int numDropped = 0;
	private double mush1Y;
	private double mush2Y;

	private double gridX;
	private static double terminalVelocity = DEF_MONST_VEL * 3;

	public Flea(ArcadeGame game, double gridX, double mush1Y, double mush2Y) {
		super(game, gridX, -1.0);
		this.gridX = gridX;
		this.mush1Y = mush1Y;
		this.mush2Y = mush2Y;
		// this.getGame().numFleas++;
		this.setVelocityX(0);
		this.setHealth(DEFAULT_FLEA_HEALTH);
		// System.out.println("New flea at " + gridX + " dropping " + mush1Y
		// + " & " + mush2Y);
		this.bounty = FLEA_SCORE;
	}

	/**
	 * Move method moves the flea down, kills it if it's out of the game, and
	 * calls mushroomDropper(). 
	 */
	@Override
	public void move() {

		double newY = this.getY() + this.getVelocityY();
		if (newY > this.getGame().getHeight() + this.height) {
			this.die();
		}
		double newX = this.getX();
		Point2D newTLPoint = new Point2D.Double(newX, newY);
		this.setTLPoint(newTLPoint);

		mushroomDropper(newY);
	}

	/**
	 * Drops a mushroom once it's reached the correct location. 
	 *
	 * @param yPos
	 */
	public void mushroomDropper(double yPos) {
		int curPos = (int) yPos / GRID_SIZE;
		if ((int) this.mush1Y == curPos || (int) this.mush2Y == curPos) {
			if (this.numDropped == 0) {
				/*
				 * By setting the mushroom locations to -100 after the first
				 * drop, we ensure that the flea will not drop multiple
				 * mushrooms in the same spot
				 */
				this.mush1Y = -100;
			} else {
				this.mush2Y = -100;
			}
			this.getGame().addObject(
					new Mushroom(this.getGame(), this.gridX, curPos));
			this.numDropped++;
		}
	}

	/**
	 * Remove health. If it's the first shot, it'll speed up. If it's dead,
	 * it'll call die automatically.
	 */
	@Override
	public void removeHealth(int damage) {
		super.removeHealth(damage);
		this.setVelocityY(terminalVelocity);
	}

	@Override
	public Shape getShape() {
		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
	}
}