import java.awt.Color;
import java.awt.geom.Point2D;


public class Zombie extends Monster {
	private static final int ZOMBIE_SCORE = 10; 
	private static final double yShiftMax = GRID_SIZE / DEF_MONST_VEL;
	
	private boolean movingRight;
	private boolean moveDown; 

	public Zombie(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.setColor(Color.MAGENTA);
		this.bounty = ZOMBIE_SCORE;
		this.movingRight = true;
		this.moveDown = false; 
		this.setVelocityY(GRID_SIZE);
	}

	@Override
	public void move() { 
		
		double nextX = this.getNextX();
		double nextY = this.getNextY();
		
		this.setTLPoint(new Point2D.Double (nextX, nextY));
	}
	
	//gets the next x value. all we need to check is if it will go 
	//off the screen either way 
	public double getNextX() {
		if (!movingRight) {
			double moveXLeft = this.getX() - this.getVelocityX();
			if (!inGameX(moveXLeft)){
				indicateMoveDown(); 
				switchXDirection();
				return this.getX(); 
			} else {
				return moveXLeft; 
			}
		} else {
			double moveXRight = this.getX() + this.getVelocityX(); 
			if (!inGameX(moveXRight)){
				indicateMoveDown();
				switchXDirection(); 
				return this.getX(); 
			} else {
				return moveXRight; 
			}
		}
	}
	
	private boolean inGameX(double position){
		return this.getGame().inGameX(position, this.gap, this.width);
	}
	
	private void indicateMoveDown () {
		this.moveDown = !this.moveDown; 
	}
	
	private void switchXDirection () {
		this.movingRight = !this.movingRight;
	}
	
	public double getNextY() {
		double currentY = this.getY(); 
		if (this.moveDown){
			indicateMoveDown(); 
			return currentY + this.getVelocityY();
		}
		return currentY; 
		
	}
	
	

}
