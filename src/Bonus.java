import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Represents the Bonus in the game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
public class Bonus extends Dieable {

	public static final int BOMB_TYPE = 1;
	public static final int SCORE_TYPE = 2;
	public static final int LIFE_TYPE = 3;

	protected static Random rand = new Random();
	protected int BonusType = 0;
	private int BonusTimeLimit = 5000;

	public Bonus() {
		this(0, 0);
	}

	/**
	 * Constructs a Bonus in the given ArcadeGame.
	 *
	 * @param game
	 */
	private Bonus(int x, int y) {
		super(0, 0);
		this.setVelocityX(0);
		this.setVelocityY(0);
		this.setTLPoint(new Point2D.Double(x * ArcadeGame.GRID_SIZE,
				y * ArcadeGame.GRID_SIZE));
		setBonusType(getRandBonusType());
		setColor(getBonusType());
	}

	protected void setColor(int type) {
		switch (type) {
		case BOMB_TYPE:
			this.setColor(Color.GRAY);
			break;
		case SCORE_TYPE:
			this.setColor(Color.YELLOW);
			break;

		case LIFE_TYPE:
			this.setColor(Color.ORANGE);
			break;
		default:
			System.err.println("invalid bonus type");
			break;
		}
	}

	protected void setBonusType(int type) {
		this.BonusType = type;
	}

	protected int getRandBonusType() {
		return rand.nextInt(3) + 1;
	}

	protected int getRandY() {
		return rand.nextInt(6) + 11;
	}

	protected int getRandX() {
		return rand.nextInt(20);
	}

	protected int getBonusType() {
		return this.BonusType;
	}

	/**
	 * When player touches the Bonus, he can get extra scores, life or bomb.
	 * While the bonus does not actually 'move', this method is used because it
	 * is already called on every refresh.
	 */
	@Override
	public void move() {
		if (expired()) {
			this.die();
		}
		if (this.checkObtain()) {
			activateBonus(this.getBonusType());
		}
		this.BonusTimeLimit--;
	}

	private static void activateBonus(int bonusNum) {
		switch (bonusNum) {
		case 1:
			Bomb.setBombsRemaining(5);
			break;
		case 2:
			getGame().score += 1000;
			Main.scoreboard.changeScore(getGame().score);
			break;

		default:
			getGame().lives++;
			Main.scoreboard.changeLives(getGame().lives);
			break;
		}
	}

	private boolean expired() {
		return System.currentTimeMillis()
				- getGame().lastBonusTime > this.BonusTimeLimit;
	}

	/**
	 * Checks whether the player touches the Bonus.
	 *
	 * @return
	 */
	public boolean checkObtain() {
		return this.getShape().intersects(
				Dieable.getGame().getShip().getShape().getBounds2D());
	}

	/**
	 * Gets the shape of the Bonus.
	 */
	@Override
	public Shape getShape() {
		return new Ellipse2D.Double(getX() + this.gap, getY() + this.gap,
				this.width, this.height);
	}

	@Override
	public void add() {
		getGame().add_bonus(this);
	}
}
