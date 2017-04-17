/**
 * 
 * MonsterManager is a class for, well, managing monsters. It handles which
 * monsters should be added and when.
 *
 * @author deradaam, lub and verlaqd Created Nov 12, 2015.
 */
public class MonsterManager {

	protected static final int ZOMBIES_START_LEVEL = 1;
	protected static final int SCORPIONS_START_LEVEL = 4;
	protected static final int FLEAS_START_LEVEL = 3;

	private ArcadeGame game;

	private static final int CENTIPEDE_BASE_NUM = 4;

	private long lastSpiderTime;
	private long spiderMinTime;

	protected boolean alreadyAddedScorpion = false;
	protected boolean scorpionIsAlive = false;
	private long scorpionMinTime;
	private long lastScorpionTime;

	private long zombieMinTime;
	private long lastZombieTime;

	private int numFleas = 0;
	private int numCentipedes = 0;
	private int numSpiders = 0;

	public MonsterManager(ArcadeGame arcadeGame) {
		this.game = arcadeGame;
		this.resetLastTimes();
		this.randomizeMonsterMinTimes();
	}

	protected void resetLastTimes() {
		this.resetLastTimes(this.game.getLastLevelChange());
	}

	protected void resetLastTimes(long time) {
		this.setLastSpiderTime(time);
		this.setLastScorpionTime(time);
		this.lastZombieTime = time;
	}

	/**
	 * Adds allowed monsters. Called by the onEveryRefresh method.
	 * 
	 */
	public void addNewMonsters() {
		this.addSpiders();

		if (game.at_or_above_level(FLEAS_START_LEVEL))
			this.addFleas();

		if (game.at_or_above_level(SCORPIONS_START_LEVEL))
			this.addScorpions();

		if (game.at_or_above_level(ZOMBIES_START_LEVEL))
			this.addZombies();
	}

	/**
	 * Creates a new Centipede starting at 0 and going to -i+1, heading right,
	 * with length i.
	 *
	 */
	public void newCentipede() {
		this.numCentipedes = 0;
		for (int i = 0; i < this.game.getLevelNum() + CENTIPEDE_BASE_NUM; i++) {
			this.game.addObject(new Centipede(this.game, -i, 0));
		}
	}

	protected int getNumFleas() {
		return this.numFleas;
	}

	protected int getNumSpiders() {
		return this.numSpiders;
	}

	protected int getNumCentipedes() {
		return this.numCentipedes;
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

	private static int randomGridX() {
		return ArcadeGame.rand.nextInt(ArcadeGame.GRID_SIZE);
	}

	/**
	 * Adds a Spider to the game.
	 */
	public void addSpiders() {
		if (this.numSpiders == 0) {
			if (System.currentTimeMillis()
					- this.getLastSpiderTime() > this.getSpiderMinTime()) {
				this.game.addObject(new Spider(this.game));
				this.setLastSpiderTime(System.currentTimeMillis());
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
					- this.getLastScorpionTime() > this.getScorpionMinTime()) {

				int yGrid = ArcadeGame.rand
						.nextInt(ArcadeGame.TOP_PLAYER_AREA - 1) + 1;

				this.game.addObject(new Scorpion(this.game, 0, yGrid));
				this.setLastScorpionTime(System.currentTimeMillis());
			}
		}
	}

	public void addZombies() {
		if (System.currentTimeMillis() > this.lastZombieTime
				+ this.zombieMinTime) {
			this.game.addObject(new Zombie(this.game, randomGridX(), -1));
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
		this.numCentipedes = 0;
		this.numSpiders = 0;
		this.alreadyAddedScorpion = false;
		this.scorpionIsAlive = false;
		this.numFleas = 0;
	}

	protected void randomizeMonsterMinTimes() {
		setSpiderMinTime(ArcadeGame.rand.nextInt(3) * 1000 + 8000);
		setScorpionMinTime(ArcadeGame.rand.nextInt(6) * 1000 + 10000);
		this.zombieMinTime =  ArcadeGame.rand.nextInt(10) * 2000 + 2000;
	}

	public void incrementMonsterCounts(Monster objToAdd) {
		if (objToAdd instanceof Centipede) {
			this.numCentipedes++;
		} else if (objToAdd instanceof Flea) {
			this.numFleas++;
		} else if (objToAdd instanceof Scorpion) {
			this.alreadyAddedScorpion = true;
			this.scorpionIsAlive = true;
		} else if (objToAdd instanceof Spider) {
			this.numSpiders++;
		}
	}
	
	long getLastScorpionTime() {
		return this.lastScorpionTime;
	}

	public long getSpiderMinTime() {
		return this.spiderMinTime;
	}

	public void setSpiderMinTime(long spiderMinTime) {
		this.spiderMinTime = spiderMinTime;
	}

	public long getScorpionMinTime() {
		return this.scorpionMinTime;
	}

	public void setScorpionMinTime(long scorpionMinTime) {
		this.scorpionMinTime = scorpionMinTime;
	}

	public long getLastSpiderTime() {
		return this.lastSpiderTime;
	}

	public void setLastSpiderTime(long lastSpiderTime) {
		this.lastSpiderTime = lastSpiderTime;
	}

	public void setLastScorpionTime(long lastScorpionTime) {
		this.lastScorpionTime = lastScorpionTime;
	}

	/**
	 * Removes the specified mushroom/monster/projectile.
	 *
	 * @param arcadeGame TODO
	 * @param objToRemove
	 */
	public void removeObject(ArcadeGame arcadeGame, Dieable objToRemove) {
		if (objToRemove instanceof Monster) {
			arcadeGame.monsters.remove(objToRemove);
			// System.out.println("CC: " + this.numCentipedes);
			if (objToRemove instanceof Centipede) {
				this.numCentipedes--;
			} else if (objToRemove instanceof Flea) {
				this.numFleas--;
			} else if (objToRemove instanceof Spider) {
				this.numSpiders--;
				setLastSpiderTime(System.currentTimeMillis());
			} else if (objToRemove instanceof Scorpion) {
				this.scorpionIsAlive = false;
				setLastScorpionTime(System.currentTimeMillis());
			}
	
			if (this.numCentipedes <= 0) {
				arcadeGame.nextLevel();
			}
		}
		if (objToRemove instanceof Projectile) {
			arcadeGame.projectiles.remove(objToRemove);
		}
		if (objToRemove instanceof Mushroom) {
			arcadeGame.getMushrooms().remove(objToRemove);
		}
		if (objToRemove instanceof Ship) {
			arcadeGame.playerDied();
	
		}
		if (objToRemove instanceof Bonus) {
			arcadeGame.bonuses.remove(objToRemove);
			// this.lastBonusTime = System.currentTimeMillis();
		}
		Main.scoreboard.changeScore(arcadeGame.score);
	}
}
