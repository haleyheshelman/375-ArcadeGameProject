import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the mushrooms in the arcade game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public class Mushroom extends Dieable {

	protected static final int DEFAULT_MUSHROOM_HEALTH = 40;
	protected static final int MUSHROOM_IMAGE_SIZE = 20;
	protected static final int MUSHROOM_SCORES = 1;

	private boolean poisonous;
	private double gridY;

	/**
	 * Constructs a mushroom at given position in the arcade game.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 * @throws IOException
	 */
	public Mushroom(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.gridY = gridY;
		this.setColor(Color.MAGENTA);
		this.setHealth(DEFAULT_MUSHROOM_HEALTH);
		this.bounty = MUSHROOM_SCORES;

		this.checkInPlayerArea();
		this.checkForOverlap();
	}

	/**
	 * Checks the mushroom whether it is in the player area and adds one to
	 * mushroomsInPlayerArea in the game if it is.
	 *
	 */
	private void checkInPlayerArea() {
		if (this.gridY > 10) {
			this.getGame().mushroomsInPlayerArea++;
		}
	}

	/**
	 * Checks if the mushroom overlaps another one and if so removes it.
	 *
	 * @return
	 */
	private void checkForOverlap() {
		if (this.intersectsObject(this.getGame().getMushrooms()) != null)
			this.die();

	}

	/**
	 * Return true if the mushroom is poisonous.
	 *
	 * @return
	 */
	public boolean isPoisonous() {
		return this.poisonous;
	}

	/**
	 * Sets the mushroom poisonous status.
	 *
	 * @param poisonous
	 */
	public void setPoisonous(boolean poisonous) {
		this.poisonous = poisonous;
		if (poisonous) {
			this.setColor(Color.RED);
		} else {
			this.setColor(Color.MAGENTA);
		}
	}

	/**
	 * Gets the shape of the mushroom.
	 * 
	 * @return
	 */

	@Override
	public void move() {
		// mushrooms don't move
	}

	/**
	 * Gets the image of Mushroom.
	 * 
	 * @return
	 */
	@Override
	public BufferedImage getImage() {
		if ((MUSHROOM_IMAGE_SIZE * this.getHealth() / DEFAULT_MUSHROOM_HEALTH) < 0) {

			System.out.println(MUSHROOM_IMAGE_SIZE * this.getHealth() / DEFAULT_MUSHROOM_HEALTH);
			System.out.println(this.getHealth());
		}
		if (this.getHealth() != 0) {
			return this.image.getSubimage(0, 0, MUSHROOM_IMAGE_SIZE,
					MUSHROOM_IMAGE_SIZE * this.getHealth() / DEFAULT_MUSHROOM_HEALTH);
		}
		return this.image;
	}

	/**
	 * Removes the mushroom from the frame when its health its at 0
	 * 
	 * @return
	 */
	@Override
	public void die() {
		super.die();
		if (this.gridY > 10)
			this.getGame().mushroomsInPlayerArea--;
	}

	/**
	 * Removes health and adjusts height
	 */
	@Override
	public void removeHealth(int damage) {
		super.removeHealth(damage);
	}

	/**
	 * Sets health and adjusts height
	 */
	@Override
	public void setHealth(int health) {
		super.setHealth(health);
	}

}
