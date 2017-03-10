import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Represents the spider in the Arcade Game.
 *
 * @author @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Spider extends Monster {

	/**
	 * Creates a Spider. Spiders move in a zig-zag pattern (constant x velocity,
	 * flipping y velocity). Stays in playable area. Eats mushrooms. Spiders are
	 * called a few seconds after the previous spider dies. (Use something based
	 * on StopWatch.java from the SortingAndSearching package)
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */

	private static final double SPIDER_VELOCITY_X = 1;
	private static final double SPIDER_VELOCITY_Y = 1;
	private static final int MAX_HEIGHT = ArcadeGame.TOP_PLAYER_AREA*ArcadeGame.GRID_SIZE;
	private static final double INITIAL_X = 9.5;
	private static final int SPIDER_SCORES = 600;
	private  int yDirection = -1;
	private  int xDirection = 1;
	private int xReverseTimes = 0;
	protected static Random rand = new Random();

	/**
	 * Constructs a Spider in the given game.
	 *
	 * @param game
	 */
	public Spider(ArcadeGame game) {
		super(game, 0, 0);
		this.setVelocityX(SPIDER_VELOCITY_X * this.xDirection);
		this.setVelocityY(SPIDER_VELOCITY_Y * this.yDirection);
		int initialY = rand.nextInt(ArcadeGame.BOTTOM_PLAYER_AREA-ArcadeGame.TOP_PLAYER_AREA+1) + ArcadeGame.TOP_PLAYER_AREA;
		this.setTLPoint(new Point2D.Double(INITIAL_X * (1 - this.xDirection) * Dieable.GRID_SIZE,
				initialY * Dieable.GRID_SIZE));
		this.bounty = SPIDER_SCORES;
	}

	/**
	 * Moves and sometimes eats the mushroom when touches it.
	 */
	@Override
	public void move() {
		double curY = this.getY();
		double curX = this.getX();
		double nextY = this.getY() + SPIDER_VELOCITY_Y * this.yDirection;
		double nextX = curX + SPIDER_VELOCITY_X * this.xDirection;
		if(this.getGame().inGameX(curX, nextY, this.width)&&!this.getGame().inGameX(nextX, nextY, this.width)) this.xDirection*=-1;
		
		this.moveOrDie(curY, nextX, nextY);
		this.eatMushroom();
	}

	/**
	 * Checks whether spider can move to the next point. If it is out of Y
	 * boundary, its Y Velocity reverse. If it is out of X boundary, it dies.
	 *
	 * @param curY
	 * @param nextX
	 * @param nextY
	 */
	public void moveOrDie(double curY, double nextX, double nextY) {
		this.setTLPoint(new Point2D.Double(nextX, curY));
		int randomSwitch = rand.nextInt(1000);
		if (randomSwitch <= 2) {
			this.xDirection *= -1;
			this.xReverseTimes++;
		} else if (randomSwitch >= 998) {
			this.yDirection *= -1;
		}
		if (this.getGame().inGameY(nextY) && nextY >= MAX_HEIGHT) {
			this.setTLPoint(new Point2D.Double(nextX, nextY));
		} else {
			this.yDirection *= -1;
		}
		if (!this.getGame().inGameX(nextX, this.gap, this.width)) {
			this.die();
		}

	}

	/**
	 * Spider can eat the mushrooms when it touches the mushrooms.
	 *
	 */
	public void eatMushroom() {
		Mushroom mushroom = (Mushroom) this.intersectsObject(this.getGame().getMushrooms());
		if (mushroom != null) {
			if (ArcadeGame.rand.nextInt(50) < 2)
				mushroom.die();
		}
	}

	/**
	 * Dies and show up in the other side of screen next time.
	 */
	@Override
	public void die() {
		if (this.xReverseTimes % 2 == 0) {
			this.xDirection *= -1;
		}
		super.die();
	}

	/**
	 * Gets the shape of Spider.
	 */
	@Override
	public Shape getShape() {
		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width, this.height);
	}

}
