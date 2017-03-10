import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
	protected final static double GAP_SIZE = (int) (0.5 * (GRID_SIZE - SPRITE_SIZE)); // 2
																						// by
																						// default
	private Color color;
	private int health;
	private double velocityX;
	private double velocityY;
	protected BufferedImage image;
	protected double height;
	protected double width;
	protected double gap;
	protected double topGap;
	private Point2D TLPoint;
	private ArcadeGame game;
	protected int bounty;

	/**
	 * Creates a new Dieable at the specified grid location, using the grid size
	 * for pixel placement
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Dieable(ArcadeGame game, double gridX, double gridY) {
		this.setTLPoint(new Point2D.Double(gridX * (GRID_SIZE), gridY
				* (GRID_SIZE)));
		this.health = 10;
		this.game = game;
		this.height = SPRITE_SIZE;
		this.width = SPRITE_SIZE;
		this.gap = GAP_SIZE;
		this.topGap = this.gap;
		this.color = Color.GREEN;
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
			this.game.score += this.bounty;
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
		this.game.removeObject(this);
	}

	/**
	 * 
	 * The intersectsObject function takes a list of Dieables and a 'check'
	 * Dieable. It goes through the list and sees if the 'check' object
	 * intersects any of the list objects. If it does, then it returns said list
	 * object.
	 * 
	 * It has an optional parameter, objToIgnore. This is a Dieable that the
	 * intersectsObject function will ignore. In other words, the function will
	 * not return the ignored object even if it intersects with the objToCheck.
	 * At the moment, objToIgnore is used only by Centipede, for verti
	 * movements.
	 *
	 * @param objsToCheck
	 * @param thisObject
	 * @return
	 */

	public Dieable intersectsObject(ArrayList<Dieable> objsToCheck) {
		return this.intersectsObject(objsToCheck, null);
	}

	public Dieable intersectsObject(ArrayList<Dieable> objsToCheck,
			Dieable objToIgnore) {

		for (Dieable curDie : objsToCheck) {
			if (curDie != objToIgnore) {
				if (this.getShape().intersects((Rectangle2D) curDie.getShape())) {
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
		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Rectangle2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
	}

	/**
	 * 
	 * Get and return center point
	 * 
	 * @return
	 */
	public Point2D getCenterPoint() {
		double centerX = this.getTLPoint().getX() + this.gap + this.width / 2
				+ 1;
		double centerY = this.getTLPoint().getY() + this.topGap + this.height
				/ 2;
		return new Point2D.Double(centerX, centerY);
	}

	/**
	 * Sets the Top Left point of the object based on the center point pased.
	 *
	 * @param centerPoint
	 */

	public void setCenterPoint(Point2D centerPoint) {
		// this.centerPoint = centerPoint;
		double TLX = centerPoint.getX() - this.gap - this.width / 2 - 1;
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
	
	public double getX(){
		return this.TLPoint.getX();
	}
	public double getY(){
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

	@Override
	public BufferedImage getImage() {
		return this.image;
	}

	@Override
	public void setImage(BufferedImage image) {

		this.image = image;
	}

	public ArcadeGame getGame() {
		return this.game;
	}

}
