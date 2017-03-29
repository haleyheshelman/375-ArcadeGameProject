/**
 * 
 * MonsterManager is a class for, well, managing monsters. It handles which
 * monsters should be added and when.
 *
 * @author deradaam, lub and verlaqd Created Nov 12, 2015.
 */
public class MonsterManager {

	private static final int ZOMBIES_START_LEVEL = 1;
	private static final int SCORPIONS_START_LEVEL = 4;
	private static final int FLEAS_START_LEVEL = 3;

	private ArcadeGame game;

	protected static final int CENTIPEDE_BASE_NUM = 4;

	protected long lastSpiderTime;
	protected long spiderMinTime;

	protected boolean alreadyAddedScorpion = false;
	protected boolean scorpionIsAlive = false;
	protected long scorpionMinTime;
	protected long lastScorpionTime;
	protected long zombieMinTime;
	protected long lastZombieTime;


	protected int numFleas = 0;
	protected int numCentipedes = 0;
	protected int numSpiders = 0;

	public MonsterManager(ArcadeGame arcadeGame) {
		this.game = arcadeGame;
		this.lastSpiderTime = this.game.lastLevelChange;
		this.lastScorpionTime = this.game.lastLevelChange;
		this.lastZombieTime = this.game.lastLevelChange;
		this.randomizeMonsterMinTimes();
	}

	private boolean zombies_allowed() {
		return this.game.levelNum >= ZOMBIES_START_LEVEL;
	}

	protected boolean scorpions_allowed() {
		return this.game.levelNum >= SCORPIONS_START_LEVEL;
	}

	private boolean fleas_allowed() {
		return this.game.levelNum >= FLEAS_START_LEVEL;
	}

	/**
	 * Adds allowed monsters. Called by the onEveryRefresh method.
	 * 
	 */
	public void addNewMonsters() {
		this.addSpiders();

		if (fleas_allowed())
			this.addFleas();

		if (scorpions_allowed())
			this.addScorpions();

		if (zombies_allowed())
			this.addZombies();
	}

	/**
	 * Creates a new Centipede starting at 0 and going to -i+1, heading right,
	 * with length i.
	 *
	 */
	public void newCentipede() {
		for (int i = 0; i < this.game.levelNum + CENTIPEDE_BASE_NUM; i++) {
			this.game.addObject(new Centipede(this.game, -i, 0));
		}
	}

	/**
	 * Figures out where we can put the flea and its mushrooms
	 *
	 * @return
	 */
	public void addFleas() {
		// To add a flea, there must be less than (a certain minimum number)
		// mushrooms in the player area. There also should not be any other
		// fleas.
		if (!(this.game.mushroomsInPlayerArea < this.game.minNumMushrooms
				&& this.numFleas == 0)) {
			return;
		}
		boolean bothGood = false;
		while (!bothGood) {

			int initialX = randomGridX();
			int mush1Y = ArcadeGame.rand.nextInt(ArcadeGame.TOP_PLAYER_AREA);
			int mush2Y = ArcadeGame.rand.nextInt(ArcadeGame.BOTTOM_PLAYER_AREA
					- ArcadeGame.TOP_PLAYER_AREA + 1)
					+ ArcadeGame.TOP_PLAYER_AREA - 1;

			Mushroom testMush1 = new Mushroom(this.game, initialX, mush1Y);
			Mushroom testMush2 = new Mushroom(this.game, initialX, mush1Y);
			if (testMush1.intersectsObject(this.game.getMushrooms()) == null
					&& testMush2.intersectsObject(
							this.game.getMushrooms()) == null) {
				testMush1.die();
				testMush2.die();
				this.game.addObject(
						new Flea(this.game, initialX, mush1Y, mush2Y));
				break;
			}
		}
	}

	private int randomGridX() {
		return ArcadeGame.rand.nextInt(ArcadeGame.GRID_SIZE);
	}

	/**
	 * Adds a Spider to the game.
	 */
	public void addSpiders() {
		if (this.numSpiders == 0) {
			if (System.currentTimeMillis()
					- this.lastSpiderTime > this.spiderMinTime) {
				this.game.addObject(new Spider(this.game));
				this.lastSpiderTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * Adds a scorpion to the game if there aren't already any and we've reached
	 * the time limit.
	 * 
	 * @return
	 */
	public void addScorpions() {
		if ((!this.alreadyAddedScorpion) && (!this.scorpionIsAlive)) {
			if (System.currentTimeMillis()
					- this.lastScorpionTime > this.scorpionMinTime) {

				int yGrid = ArcadeGame.rand
						.nextInt(ArcadeGame.TOP_PLAYER_AREA - 1) + 1;

				this.game.addObject(new Scorpion(this.game, 0, yGrid));
				this.lastScorpionTime = System.currentTimeMillis();
			}
		}
	}

	public void addZombies() {
		if (System.currentTimeMillis() > this.lastZombieTime
				+ this.zombieMinTime) {
			this.game.addObject(new Zombie(game, randomGridX(), -1));
			this.lastZombieTime = System.currentTimeMillis();
		}
	}

	/**
	 * 
	 * Reset monster counts to zero. Used on player death.
	 * 
	 * @param arcadeGame
	 *
	 */
	public void resetMonsterCounts() {
		numCentipedes = 0;
		numSpiders = 0;
		alreadyAddedScorpion = false;
		scorpionIsAlive = false;
		numFleas = 0;
	}

	protected void randomizeMonsterMinTimes() {
		spiderMinTime = ArcadeGame.rand.nextInt(3) * 1000 + 8000;
		scorpionMinTime = ArcadeGame.rand.nextInt(6) * 1000 + 10000;
		zombieMinTime = 5000;//ArcadeGame.rand.nextInt(10) * 2000 + 10000;
	}

	public void incrementMonsterCounts(Monster objToAdd) {
		if (objToAdd instanceof Centipede) {
			numCentipedes++;
		} else if (objToAdd instanceof Flea) {
			numFleas++;
		} else if (objToAdd instanceof Scorpion) {
			alreadyAddedScorpion = true;
			scorpionIsAlive = true;
		} else if (objToAdd instanceof Spider) {
			numSpiders++;
		}
	}
}
