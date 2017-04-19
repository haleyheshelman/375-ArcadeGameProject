import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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
	// @Override
	// public BufferedImage getImage() {
	// if ((MUSHROOM_IMAGE_SIZE * this.getHealth() / DEFAULT_MUSHROOM_HEALTH) <
	// 0) {
	// System.out.println("HELLO FROM THE GREAT BEYOND");
	//
	// System.out.println(MUSHROOM_IMAGE_SIZE * this.getHealth() /
	// DEFAULT_MUSHROOM_HEALTH);
	// System.out.println(this.getHealth());
	// }
	// if (this.getHealth() != 0) {
	// return this.image.getSubimage(0, 0, MUSHROOM_IMAGE_SIZE,
	// MUSHROOM_IMAGE_SIZE * this.getHealth() / DEFAULT_MUSHROOM_HEALTH);
	// }
	// return this.image;
	// }

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

	private boolean healthDroppedLessThan(int drop) {
		return this.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - drop;
	}

	private static Map<Integer, String[]> imagesMap = new HashMap<>();
	static int health_level_0 = 0;
	static int health_level_1 = 10;
	static int health_level_2 = 20;
	static int health_level_3 = 30;
	static int health_level_4 = 39;
	static int[] health_level_arr = { health_level_0, health_level_1,
			health_level_2, health_level_3, health_level_4 };

	private static void initImagesMap() {
		addImageMapping(health_level_0, "mushroomFinal.png",
				"poisonedMushroomFinal.png");
		addImageMapping(health_level_1, "mushroomFinalDamage1.png",
				"poisonedMushroomFinalDamage1.png");
		addImageMapping(health_level_2, "mushroomFinalDamage2.png",
				"poisonedMushroomFinalDamage2.png");
		addImageMapping(health_level_3, "mushroomFinalDamage3.png",
				"poisonedMushroomFinalDamage3.png");
		addImageMapping(health_level_4, "mushroomFinalDamage4.png",
				"poisonedMushroomFinalDamage4.png");
	}

	public static void addImageMapping(int drop, String normalImage,
			String poisonImage) {
		imagesMap.put(new Integer(drop),
				new String[] { normalImage, poisonImage });
	}

	public String getImageForDropLevel(int drop) {
		int ind = (this.isPoisonous() ? 1 : 0);
		if (imagesMap.isEmpty()) {
			initImagesMap();
		}
		return imagesMap.get(drop)[ind];
	}

	@Override
	public BufferedImage getImage() throws IOException {

		String imagePath = null;
		for (int level : health_level_arr) {
			if (healthDroppedLessThan(level)) {
				imagePath = getImageForDropLevel(level);
				break;
			}
		}
		return ImageIO.read(new File(imagePath));

	}
}
