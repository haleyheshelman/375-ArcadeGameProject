import java.awt.Color;


public class Zombie extends Monster {
	private static final int ZOMBIE_SCORE = 10; 
	private static final double yShiftMax = GRID_SIZE / DEF_MONST_VEL;
	
	private int xDirection = 1; //1 is right, -1 is left
	private int yDirection = 1; //1 is down 

	public Zombie(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);
		this.setColor(Color.MAGENTA);
		this.bounty = ZOMBIE_SCORE;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
