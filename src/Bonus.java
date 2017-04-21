import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Bonus in the game.
 *
 * @author deradaam, lub and verlaqd. Created Nov 10, 2015.
 */
public class Bonus extends Dieable {

	protected static Random rand = new Random();
	protected int BonusType = 0;
	private int BonusTimeLimit = 5000;

	/**
	 * Constructs a Bonus in the given ArcadeGame.
	 *
	 * @param game
	 */
	public Bonus(ArcadeGame game) {
		super(game, 0, 0);
		this.setVelocityX(0);
		this.setVelocityY(0);
		int gridY = getRandY();
		int gridX = getRandX();
		this.setTLPoint(new Point2D.Double(gridX * Dieable.GRID_SIZE, gridY
				* Dieable.GRID_SIZE));
		setBonusType(); 
		setColor(this.BonusType);
	}

	protected void setColor(int type) {
		if (type == 1) {
			this.setColor(Color.GRAY);
		} else if (type == 2) {
			this.setColor(Color.YELLOW);
		} else {
			this.setColor(Color.ORANGE);
		}
	}
	

	
	/*
	 * This might seem like needless indirection
	 * but it's for the purpose of testing 
	 */
	protected void setBonusType() {
		this.BonusType = getRandBonusType();
	}

	protected int getRandBonusType() {
		return rand.nextInt(3) + 1;
	}

	protected int getRandY() {
		int gridY = rand.nextInt(6) + 11;
		return gridY;
	}

	protected int getRandX() {
		int gridX = rand.nextInt(20);
		return gridX;
	}
	
	protected int getBonusTyp(){
		return this.BonusType;
	}

	/**
	 * When player touches the Bonus, he can get extra scores, life or bomb.
	 */
	@Override
	public void move() {
		if (System.currentTimeMillis() - this.getGame().lastBonusTime > this.BonusTimeLimit) {
			this.die();
		}
		if (this.checkObtain()) {
			if (this.BonusType == 1) {
				this.getGame().getShip().bombsRemaining = 5;
			} else if (this.BonusType == 2) {
				this.getGame().score += 1000;
				Main.scoreboard.changeScore(this.getGame().score);
			} else {
				this.getGame().lives++;
				Main.scoreboard.changeLives(this.getGame().lives);
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
		objsToCheck.add(this.getGame().getShip());
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
}
