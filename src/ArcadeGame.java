import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Represents the arcade game.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
public class ArcadeGame {

	protected boolean USE_IMAGES = true;

	protected Scoreboard scoreboard;
	protected int width;
	protected int height;
	protected static final int GRID_SIZE = 20;

	protected static final int TOP_PLAYER_AREA = 11;
	protected static final int BOTTOM_PLAYER_AREA = 16;

	long lastLevelChange = System.currentTimeMillis();

	protected int levelNum = 0;
	protected int score = 0;
	protected final int DEFAULT_LIVES = 2;
	protected int lives = this.DEFAULT_LIVES;

	protected int minNumMushrooms = 5;
	protected int mushroomsInPlayerArea = 0;

	protected static Random rand = new Random();

	protected long bonusMinTime = rand.nextInt(10) * 1000 + 30000;
	protected long lastBonusTime = this.lastLevelChange;

	protected boolean isPaused = false;
	private ArrayList<Dieable> mushrooms = new ArrayList<>();
	private ArrayList<Dieable> monsters = new ArrayList<>();
	private ArrayList<Dieable> projectiles = new ArrayList<>();
	private ArrayList<Dieable> bonuses = new ArrayList<>();
	private Ship ship;

	MonsterManager MM = new MonsterManager(this);

	/**
	 * 
	 * Creates an ArcadeGame at level 1, with a new ship at grid location 10,16
	 * (centered on the second to last row)
	 *
	 * @param height
	 * @param width
	 * @throws IOException
	 */
	public ArcadeGame(int height, int width) throws IOException {
		this.height = height;
		this.width = width;
		initializeAddObjectMap();
		this.ship = new Ship(this, 10, 16);
		createLevel(1);
	}

	public void randomizeTimeLimits() {
		this.MM.spiderMinTime = rand.nextInt(3) * 1000 + 8000;
		this.MM.scorpionMinTime = rand.nextInt(6) * 1000 + 10000;
		this.bonusMinTime = rand.nextInt(10) * 1000 + 30000;
	}

	/**
	 * 
	 * This function is called every refresh by the similarly named method in
	 * AGC.
	 *
	 */
	public void onEveryRefresh() {

		this.MM.addNewMonsters();
		this.moveDieables();
		this.addBonuses();
	}

	/**
	 * Switches Pause state, changes frame title accordingly.
	 */

	public void pauseButtonHit() {
		if (this.isPaused) {
			this.unpause();
		} else {
			this.pause();
		}
	}

	/**
	 * 
	 * Pause
	 *
	 */
	public void pause() {
		this.isPaused = true;
		Main.changeTitle("PAUSED");
	}

	/**
	 * 
	 * Unpause
	 *
	 */
	public void unpause() {
		this.isPaused = false;
		Main.changeTitle("Arcade Game!!");
	}

	/**
	 * 
	 * Paused/unpauses and displays/undisplays help screen
	 *
	 */
	public void helpButtonHit() {
		this.pause();
		new HelpScreen();
	}

	/**
	 * Create level based on a file. This should ONLY be used for the first
	 * level, or when manually changing levels with the U/D keys.
	 *
	 * @param levelNumber
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public void createLevel(int levelNumber) throws FileNotFoundException, IOException {

		int nextLevel = this.levelNum + levelNumber;

		if (nextLevel < 1 || nextLevel >= 7) {
			return;
		}
		String textFile = "l" + nextLevel + ".txt";
		this.levelNum = nextLevel;

		this.updateScoreboard();

		setLastTimes();
		randomizeTimeLimits();

		clearBoard();

		this.MM.resetMonsterCounts();
		this.MM.scorpionIsAlive = false;
		this.mushroomsInPlayerArea = 0;
		this.MM.chooseMonsters();
		readLevelFromFile(textFile);
	}

	private void readLevelFromFile(String textFile) throws FileNotFoundException {
		int gridY = 0;
		Scanner input = new Scanner(new File(textFile));
		while (input.hasNextLine()) {
			String row = input.nextLine();
			for (int gridX = 0; gridX < row.length(); gridX++) {
				if (row.charAt(gridX) == 'C') {
					this.addObject(new Centipede(this, gridX, gridY));
				}
				if (row.charAt(gridX) == 'M') {
					this.addObject(new Mushroom(this, gridX, gridY));
				}
			}
			gridY++;
		}

		input.close();
	}

	private void setLastTimes() {
		this.lastLevelChange = System.currentTimeMillis();
		this.lastBonusTime = this.lastLevelChange;
		this.MM.lastScorpionTime = this.lastLevelChange;
		this.MM.lastSpiderTime = this.lastLevelChange;
	}

	private void clearBoard() {
		this.mushrooms.clear();
		this.monsters.clear();
		this.projectiles.clear();
		this.bonuses.clear();
	}

	/**
	 * Performs the appropriate actions when the player beats a level
	 */

	public void nextLevel() {
		this.levelNum++;
		Main.scoreboard.changeLevel(this.levelNum);
		this.MM.numCentipedes = 0;
		this.MM.alreadyAddedScorpion = false;
		this.MM.newCentipede();
		this.MM.chooseMonsters();
		this.lastLevelChange = System.currentTimeMillis();

		this.randomizeTimeLimits();
	}

	/**
	 * Restart the game.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void restart() throws FileNotFoundException, IOException {
		this.setLevelNum(1);
		this.createLevel(0);
		this.score = 0;
		this.isPaused = false;
		this.lives = this.DEFAULT_LIVES;
		this.ship = new Ship(this, 10, BOTTOM_PLAYER_AREA);
		this.updateScoreboard();
	}

	/**
	 * Updates the scoreboard
	 */
	public void updateScoreboard() {
		Main.scoreboard.changeLevel(this.levelNum);
		Main.scoreboard.changeLives(this.lives);
		Main.scoreboard.changeScore(this.score);
	}

	/**
	 * Moves the Dieables by calling their move() methods
	 */
	protected void moveDieables() {
		List<Dieable> dieableParts = this.getDieableParts();
		for (Dieable curDie : dieableParts) {
			curDie.move();
		}
	}

	/**
	 * Adds a Bonus to the game.
	 *
	 */
	public void addBonuses() {
		if (System.currentTimeMillis() - this.lastBonusTime > this.bonusMinTime) {
			this.addObject(new Bonus(this));
			this.lastBonusTime = System.currentTimeMillis();
		}
	}

	/**
	 * Adds a Dieable (Monster, Mushroom, or Projectile) to the appropriate list
	 * of the ArcadeGame
	 *
	 * @param objToAdd
	 */

	HashMap<Class<? extends Dieable>, ArrayList<Dieable>> dieableMap = new HashMap<>();

	private void initializeAddObjectMap() {
		dieableMap.put(Monster.class, monsters);
		dieableMap.put(Projectile.class, projectiles);
		dieableMap.put(Mushroom.class, mushrooms);
		dieableMap.put(Bonus.class, bonuses);
	}

	public void addObject(Dieable objToAdd) {
		if (objToAdd instanceof Monster) {
			this.MM.incrementMonsterCounts((Monster) objToAdd);

		}
		ArrayList<Dieable> list;
		if (dieableMap.containsKey(objToAdd.getClass())) {
			list = dieableMap.get(objToAdd.getClass());
		} else {
			list = dieableMap.get(objToAdd.getClass().getSuperclass());
		}
		list.add(objToAdd);
	}

	/**
	 * 
	 * returns the number of bombs in the game
	 *
	 * @return
	 */
	public int countBomb() {
		int number = 0;
		for (Dieable bomb : this.projectiles) {
			if (bomb instanceof Bomb) {
				number++;
			}
		}
		return number;
	}

	/**
	 * Removes the specified mushroom/monster/projectile.
	 *
	 * @param objToRemove
	 */
	public void removeObject(Dieable objToRemove) {
		if (objToRemove instanceof Monster) {
			this.monsters.remove(objToRemove);
			// System.out.println("CC: " + this.numCentipedes);
			if (objToRemove instanceof Centipede) {
				this.MM.numCentipedes--;
			} else if (objToRemove instanceof Flea) {
				this.MM.numFleas--;
			} else if (objToRemove instanceof Spider) {
				this.MM.numSpiders--;
				this.MM.lastSpiderTime = System.currentTimeMillis();
			} else if (objToRemove instanceof Scorpion) {
				this.MM.scorpionIsAlive = false;
				this.MM.lastScorpionTime = System.currentTimeMillis();
			}

			if (this.MM.numCentipedes <= 0) {
				this.nextLevel();
			}
		}
		if (objToRemove instanceof Projectile) {
			this.projectiles.remove(objToRemove);
		}
		if (objToRemove instanceof Mushroom) {
			this.mushrooms.remove(objToRemove);
		}
		if (objToRemove instanceof Ship) {
			this.playerDied();

		}
		if (objToRemove instanceof Bonus) {
			this.bonuses.remove(objToRemove);
			// this.lastBonusTime = System.currentTimeMillis();
		}
		Main.scoreboard.changeScore(this.score);
	}

	/**
	 * Handles what should happen if the player is killed.
	 *
	 */
	public void playerDied() {

		// if there are no more lives left, game over
		if (this.lives < 0) {
			System.out.println("game over");
			System.out.println("You Scores are: " + this.score);
			String nameString = JOptionPane.showInputDialog("What is your name?");
			HighestScoresBoard board = new HighestScoresBoard(this, nameString, this.score);
			try {
				board.showResult();
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			}

			this.isPaused = true;
			this.ship.setTLPoint(new Point2D.Double(-100, -100));
		} else {
			// decrease lives remaining
			this.lives--;
			// reset ship to center
			this.ship = new Ship(this, 10, BOTTOM_PLAYER_AREA);
			// clear monsters
			this.monsters.clear();
			this.projectiles.clear();
			// reset centipede count
			this.MM.resetMonsterCounts();
			// this.timeBonus = 0;

			// Mushrooms should be restored to full health if damaged and made
			// not poisonous
			for (Dieable currentMushroom : this.mushrooms) {
				currentMushroom.setHealth(Mushroom.DEFAULT_MUSHROOM_HEALTH);
				((Mushroom) currentMushroom).setPoisonous(false);
				currentMushroom.bounty = 1;
			}
			// initialize new Centipede
			this.MM.newCentipede();
		}
		Main.scoreboard.changeLives(this.lives);
	}

	/**
	 * Checks if the specified point is in the game (one for x, one for y)
	 *
	 * @param X:
	 *            the top left x
	 * @param obWidth
	 * @param gap
	 * @return
	 */
	public boolean inGameX(double X, double gap, double obWidth) {
		return (0 - 1 <= X && X + 2 * gap + obWidth <= this.width);
	}

	public boolean inGameY(double Y) {
		return (0 <= Y && Y <= this.height + 4);
	}

	// ////// GETTERS AND SETTERS ////// //

	public int getHeight() {
		return this.height;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	public boolean isPaused() {
		return this.isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public ArrayList<Dieable> getMushrooms() {
		return this.mushrooms;
	}

	public ArrayList<Dieable> getMonsters() {
		return this.monsters;
	}

	public ArrayList<Dieable> getProjectiles() {
		return this.projectiles;
	}

	public Ship getShip() {
		return this.ship;
	}

	/**
	 * Returns a list of all Drawables (monsters, mushrooms, projectiles, ship)
	 *
	 * @return
	 */
	public ArrayList<Drawable> getDrawableParts() {

		ArrayList<Drawable> drawables = new ArrayList<>();
		drawables.addAll(this.bonuses);
		drawables.addAll(this.mushrooms);
		drawables.addAll(this.monsters);

		drawables.addAll(this.projectiles);

		drawables.add(this.ship);

		return drawables;
	}

	/**
	 * Returns a list of all Dieables (monsters, mushrooms, projectiles, ship)
	 *
	 * @return
	 */
	public ArrayList<Dieable> getDieableParts() {

		ArrayList<Dieable> dieables = new ArrayList<>();
		dieables.addAll(this.monsters);
		dieables.addAll(this.mushrooms);
		dieables.addAll(this.projectiles);
		dieables.addAll(this.bonuses);
		dieables.add(this.ship);

		return dieables;
	}

	public void addNewBullet(Double projectilePoint) {
		addObject(new Bullet(this, projectilePoint));
	}

	public void addNewMissile(Double projectilePoint) {
		addObject(new Missile(this, projectilePoint));
	}

	public void addNewShotgunShot(Double projectilePoint) {
		this.addObject(new Bullet(this, projectilePoint, 8));
		this.addObject(new Bullet(this, projectilePoint, 8, -1));
		this.addObject(new Bullet(this, projectilePoint, 8, 1));
	}

	public void addNewBomb(Double projectilePoint) {
		addObject(new Bomb(this, projectilePoint));
	}
}
