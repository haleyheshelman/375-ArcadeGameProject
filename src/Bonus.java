import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Bonus in the game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
public class Bonus extends Dieable {
	protected static Random rand = new Random();
	private int BonusType;
	private int BonusTimeLimit = 5000;

	public Bonus() {
		this(random_x_coord(), random_y_coord());
	}

	/**
	 * Constructs a Bonus in the given ArcadeGame.
	 * 
	 * @param game
	 */
	public Bonus(int x, int y) {
		super(x, y);
		this.setVelocityX(0);
		this.setVelocityY(0);
		this.BonusType = random_bonus_type();
		switch (this.BonusType) {
		case (1):
			this.setColor(Color.GRAY);
			break;
		case (2):
			this.setColor(Color.YELLOW);
			break;
		case (3):
			this.setColor(Color.ORANGE);
			break;
		default:
			break;
		}
	}

	private static int random_bonus_type() {
		return rand.nextInt(3) + 1;
	}

	static int random_x_coord() {
		return rand.nextInt(20);
	}

	static int random_y_coord() {
		return rand.nextInt(6) + 11;
	}

	/**
	 * When player touches the Bonus, he can get extra scores, life or bomb.
	 */
	@Override
	public void move() {
		if (System.currentTimeMillis()
				- Dieable.getGame().lastBonusTime > this.BonusTimeLimit) {
			this.die();
		}
		if (this.checkObtain()) {

			switch (this.BonusType) {
			case (1):
				Bomb.bombsRemaining = 5;
				break;
			case (2):
				Dieable.getGame().score += 1000;
				Main.scoreboard.changeScore(Dieable.getGame().score);
				break;
			case (3):
				Dieable.getGame().lives++;
				Main.scoreboard.changeLives(Dieable.getGame().lives);
				break;
			default:
				break;
			}
			this.die();
		}
		this.BonusTimeLimit--;
	}

	/**
	 * Checks whether the player touches the Bonus.
	 *
	 * @return
	 */
	public boolean checkObtain() {
		ArrayList<Dieable> objsToCheck = new ArrayList<>();
		objsToCheck.add(Dieable.getGame().getShip());
		boolean hit = false;
		Dieable intersectObject = this.intersectsObject(objsToCheck);
		if (intersectObject != null) {
			hit = true;
		}
		return hit;
	}

	/**
	 * Gets the shape of the Bonus.
	 */
	@Override
	public Shape getShape() {
		double x = getTLPoint().getX();
		double y = getTLPoint().getY();
		return new Ellipse2D.Double(x + this.gap, y + this.gap, this.width,
				this.height);
	}

	@Override
	public void add() {
		getGame().add_bonus(this);
	}
}
