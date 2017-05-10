import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Represents the mushrooms in the arcade game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
@SuppressWarnings("boxing")
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
	public Mushroom(int gridX, int gridY) {
		super(gridX, gridY);
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
			Dieable.getGame().mushroomsInPlayerArea++;
		}
	}

	/**
	 * Checks if the mushroom overlaps another one and if so removes it.
	 *
	 * @return
	 */
	private void checkForOverlap() {
		if (this.intersectsObject(Dieable.getGame().getMushrooms()) != null)
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
		this.setColor(poisonous ? Color.RED : Color.MAGENTA);
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
	 * Removes the mushroom from the frame when its health its at 0
	 * 
	 * @return
	 */
	@Override
	public void die() {
		super.die();
		if (this.gridY > 10)
			Dieable.getGame().mushroomsInPlayerArea--;
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

	private boolean healthDroppedLessThan(int drop) {
		return this.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - drop;
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
		return ImageIO.read(Main.ResourceInputStream(imagePath));
	}

}
