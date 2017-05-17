import java.awt.Color;
import java.awt.geom.Point2D;

public class Zombie extends Monster {
	private static final int ZOMBIE_SCORE = 10;
	private static final double yShiftMax = ArcadeGame.GRID_SIZE
			/ DEF_MONST_VEL;

	private boolean movingRight;
	private boolean moveDown;

	public Zombie(int gridX, int gridY) {
		super(gridX, gridY);
		this.setColor(Color.MAGENTA);
		this.bounty = ZOMBIE_SCORE;
		this.movingRight = true;
		this.moveDown = false;
		this.setVelocityY(ArcadeGame.GRID_SIZE);
		this.setVelocityX(Monster.DEF_MONST_VEL * 2);
	}

	@Override
	public void move() {

		double nextX = this.getNextX();
		double nextY = this.getNextY();

		this.setTLPoint(new Point2D.Double(nextX, nextY));
	}

	// gets the next x value. all we need to check is if it will go
	// off the screen either way
	public double getNextX() {
		double nextX = getMoveX();
		if (!inGameX(nextX)) {
			indicateMoveDown();
			switchXDirection();
			return this.getX();
		} else {
			return nextX;
		}
	}

	protected double getMoveX() {
		double currX = this.getX();
		double velX = this.getVelocityX();
		return movingRight ? currX + velX : currX - velX;
	}

	private boolean inGameX(double position) {
		return Dieable.getGame().inGameX(position, this.gap, this.width);
	}

	protected void indicateMoveDown() {
		this.moveDown = !this.moveDown;
	}

	protected void switchXDirection() {
		this.movingRight = !this.movingRight;
	}

	public double getNextY() {
		double currentY = this.getY();
		if (this.moveDown) {
			indicateMoveDown();
			return currentY + this.getVelocityY();
		}
		return currentY;

	}

	// for testing
	protected boolean checkMovingRight() {
		return this.movingRight;
	}

	protected boolean checkMoveDown() {
		return this.moveDown;
	}
}
