/**
 * The abstract Monster class is used by all Monsters.
 * 
 * @author deradaam, lub and verlaqd. Created Nov 6, 2015.
 */
public abstract class Monster extends Dieable {

	final static double DEF_MONST_VEL = 2; // should be an integer divisor of
											// 20, for the sake of graphics
	final static int DEF_MONST_HEALTH = 10;

	/**
	 * Constructs a monster with the specific location in the arcade game.
	 *
	 * @param game
	 * @param gridX
	 * @param gridY
	 */
	public Monster(ArcadeGame game, double gridX, double gridY) {
		super(game, gridX, gridY);

		// in most cases, velocity y = velocity x.
		this.setVelocityX(DEF_MONST_VEL);
		this.setVelocityY(DEF_MONST_VEL);
		this.setHealth(DEF_MONST_HEALTH);

	}

}
