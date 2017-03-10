import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Represents the Scorpion in the Arcade Game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 8, 2015.
 */
public class Scorpion extends Monster {

	/**
	 * Scorpions move horizontally and poison all mushrooms on contact. They
	 * only appear on the
	 * non-playable area, in higher levels.
	 * 
	 * Scorpions are spawned once per level. They are spawned after a random
	 * amount of time, between 10 and 20 seconds.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Scorpion(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.setColor(Color.CYAN);
//		System.out.println("new Scorpion");
		this.bounty = 900;
		// System.out.println(gridX+" "+gridY);
	}

	/**
	 * Movement method. Basic movement overview:
	 * 
	 * Move horizontally across screen until it reaches the end
	 * 
	 */
	@Override
	public void move() {
		double curX = this.getX();
		double curY = this.getY();
		double newX = curX + this.getVelocityX();

		Dieable intersectedObject = this.intersectsObject(this.getGame()
				.getMushrooms());

		if (intersectedObject != null
				&& intersectedObject.getClass().getName().equals("Mushroom")) {

			Mushroom poisonedMushroom = (Mushroom) intersectedObject;
			poisonedMushroom.setPoisonous(true);
			poisonedMushroom.bounty++;
		}

		this.setTLPoint(new Point2D.Double(newX, curY));

		if (!this.getGame().inGameX(newX, this.gap, this.width)) {
			this.die();
		}
	}

	/**
	 * Gets the shape of the Scorpion.
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
