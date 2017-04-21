import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represents the creatures in the arcade game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public abstract class Dieable implements Drawable {

	protected final static int GRID_SIZE = ArcadeGame.GRID_SIZE; // 20 pixel
																	// squares
																	// on the
																	// grid
	protected final static int SPRITE_SIZE = 16; // default sprite width
	protected final static double GAP_SIZE = (int) (0.5
			* (GRID_SIZE - SPRITE_SIZE)); // 2
											// by
											// default
	private Color color = Color.GREEN;
	private int health = 10;
	private double velocityX;
	private double velocityY;
	protected double height = SPRITE_SIZE;
	protected double width = SPRITE_SIZE;
	protected double gap = GAP_SIZE;
	protected double topGap = GAP_SIZE;
	private Point2D TLPoint;
	protected int bounty;
	private static ArcadeGame game;

	/**
	 * Creates a new Dieable at the specified grid location, using the grid size
	 * for pixel placement
	 *
	 * @param gridX
	 * @param gridY
	 */
	public Dieable(int gridX, int gridY) {
		// yes, it's silly
		this((double) gridX * GRID_SIZE, gridY * GRID_SIZE);
	}

	public Dieable(double pixelX, double pixelY) {
		this.setTLPoint(new Point2D.Double(pixelX, pixelY));
	}

	/**
	 * Removes the health of the Dieable, and if the health is zero, the Dieable
	 * dies.
	 *
	 * @param damage
	 */
	void removeHealth(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			this.getGame().score += this.bounty;
			this.health = 0;
			this.die();
		}
	}

	/**
	 * Remove the Dieable from the arcade game.
	 *
	 */
	void die() {
		// System.out.println("Dying");
		this.getGame().MM.removeObject(this.getGame(), this);
	}

	/**
	 * Checks if a given object intersects with other dieables
	 *
	 * @param objsToCheck
	 * @param thisObject
	 * @return
	 */

	public Dieable intersectsObject(ArrayList<Dieable> objsToCheck) {
		return this.intersectsObject(objsToCheck, null);
	}

	/**
	 * Checks if a given object intersects with other dieables but ignores a
	 * given dieable
	 * 
	 * @param objsToCheck
	 * @param objToIgnore
	 * @return
	 */
	public Dieable intersectsObject(ArrayList<Dieable> objsToCheck,
			Dieable objToIgnore) {

		for (Dieable curDie : objsToCheck) {
			if (curDie != objToIgnore) {
				if (this.getShape()
						.intersects((Rectangle2D) curDie.getShape())) {
					return curDie;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * Abstract move() method is called on every refresh.
	 *
	 */
	public abstract void move();

	/* getters and setters */

	/**
	 * 
	 * getShape returns the shape that represents the Dieable. It's used
	 * primarily for intersections.
	 */
	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(this.getX() + this.gap,
				this.getY() + this.gap, this.width, this.height);
	}

	/**
	 * 
	 * Get and return center point
	 * 
	 * @return
	 */
	public Point2D getCenterPoint() {
		// removed a plus one from getting centerX
		double centerX = this.getX() + this.gap + (this.width / 2);
		double centerY = this.getY() + this.topGap + (this.height / 2);
		return new Point2D.Double(centerX, centerY);
	}

	/**
	 * Sets the Top Left point of the object based on the center point pased.
	 *
	 * @param centerPoint
	 */

	public void setCenterPoint(Point2D centerPoint) {
		// removed a minus one from getting TLX
		double TLX = centerPoint.getX() - this.gap - (this.width / 2);
		double TLY = centerPoint.getY() - this.topGap - this.height / 2;
		this.TLPoint = new Point2D.Double(TLX, TLY);
	}

	/**
	 * 
	 * Get and return top left point
	 * 
	 * @return
	 */
	public Point2D getTLPoint() {
		return this.TLPoint;
	}

	/**
	 * Top left X
	 * 
	 * @return
	 */
	public double getX() {
		return this.TLPoint.getX();
	}

	/**
	 * top left Y
	 * 
	 * @return
	 */
	public double getY() {
		return this.TLPoint.getY();
	}

	public void setTLPoint(Point2D TLPoint) {
		this.TLPoint = TLPoint;
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	// VELOCITY GET/SET
	public double getVelocityX() {
		return this.velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return this.velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public static ArcadeGame getGame() {
		if (game == null)
			game = ArcadeGame.getInstance();
		return game;

	}

	static void generateAtPixels(double x, double y) {
		// Due to java silliness, this is how it has to be
		String n = null;
		System.out.println(n.equals("Test"));
	}
	
	abstract void generateAtPixels_override(double x, double y);

	static final void generateAtGrid(int x, int y) {
		generateAtPixels(x * GRID_SIZE, y * GRID_SIZE);
	}

}
