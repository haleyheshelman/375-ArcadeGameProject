import java.awt.geom.Point2D;
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

	private static ArcadeGame instance;

	static ArcadeGame getInstance() {
		if (instance == null) {
			System.out.println("New game created");
			instance = new ArcadeGame();
		}
		return instance;
	}

	static void resetArcadeGame() {
		instance = null;
	}

	protected boolean USE_IMAGES = true;

	public static final int DEF_AG_PIX_HGHT = 318;
	public static final int DEF_AG_PIX_WDTH = 400;

	protected final static int width = DEF_AG_PIX_WDTH;
	protected final static int height = DEF_AG_PIX_HGHT;
	protected static final int GRID_SIZE = 20;

	protected static final int TOP_PLAYER_AREA = 11;
	protected static final int BOTTOM_PLAYER_AREA = 16;

	protected static final int BOARD_GRID_WIDTH = DEF_AG_PIX_WDTH / GRID_SIZE;

	private long lastLevelChange;

	private int levelNum = 0;
	protected int score = 0;
	protected final int DEFAULT_LIVES = 2;
	protected int lives = this.DEFAULT_LIVES;

	protected int minNumMushrooms = 5;
	protected int mushroomsInPlayerArea = 0;

	protected static Random rand = new Random();

	protected long bonusMinTime = rand.nextInt(10) * 1000 + 30000;
	protected long lastBonusTime;

	protected boolean isPaused = false;
	private ArrayList<Dieable> mushrooms = new ArrayList<>();
	ArrayList<Dieable> monsters = new ArrayList<>();
	ArrayList<Dieable> projectiles = new ArrayList<>();
	ArrayList<Dieable> bonuses = new ArrayList<>();
	private Ship ship;

	public MonsterManager MM = new MonsterManager(this);

	protected ArcadeGame() {
		this(DEF_AG_PIX_HGHT, DEF_AG_PIX_WDTH);
	}

	/**
	 * 
	 * Creates an ArcadeGame at level 1, with a new ship at grid location 10,16
	 * (centered on the second to last row)
	 *
	 * @param height
	 * @param width
	 * @throws IOException
	 */
	private ArcadeGame(int height, int width) {
		if (Main.scoreboard == null)
			Main.scoreboard = new Scoreboard();

		initializeAddObjectMap();

		instance = this;
		new Ship(BOARD_GRID_WIDTH / 2, BOTTOM_PLAYER_AREA).add();
		try {
			createLevel(1);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * The numbers below may be adjusted as desired to change the time limits
	 */
	public void randomizeTimeLimits() {
		this.MM.setSpiderMinTime(rand.nextInt(3) * 1000 + 8000);
		this.MM.setScorpionMinTime(rand.nextInt(6) * 1000 + 10000);
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
		this.check_about_adding_bonus();
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

	public void createLevel(int levelNumber)
			throws FileNotFoundException, IOException {

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
		this.mushroomsInPlayerArea = 0;
		readLevelFromFile(textFile);
	}

	private static void readLevelFromFile(String textFile)
			throws FileNotFoundException {
		int gridY = 0;
		Scanner input = new Scanner(new File(textFile));
		while (input.hasNextLine()) {
			String row = input.nextLine();
			for (int gridX = 0; gridX < row.length(); gridX++) {
				if (row.charAt(gridX) == 'C') {
					new Centipede(gridX, gridY).add();
				}
				if (row.charAt(gridX) == 'M') {
					Mushroom.generateAtGrid(gridX, gridY);
				}
			}
			gridY++;
		}

		input.close();
	}

	private void setLastTimes() {
		this.lastLevelChange = System.currentTimeMillis();
		this.lastBonusTime = this.lastLevelChange;
		this.MM.resetLastTimes(this.lastLevelChange);

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
		this.updateScoreboard();
		this.MM.alreadyAddedScorpion = false;
		this.MM.newCentipede();
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
		this.ship = new Ship(10, BOTTOM_PLAYER_AREA);
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
	public void check_about_adding_bonus() {
		if (System.currentTimeMillis()
				- this.lastBonusTime > this.bonusMinTime) {
			new Bonus().add();
		}
	}

	public void add_bonus(Bonus b) {
		this.addObject(b);
		this.lastBonusTime = System.currentTimeMillis();
	}

	/**
	 * Adds a Dieable (Monster, Mushroom, or Projectile) to the appropriate list
	 * of the ArcadeGame
	 *
	 * @param objToAdd
	 */

	HashMap<Class<? extends Dieable>, ArrayList<Dieable>> dieableMap = new HashMap<>();

	private void initializeAddObjectMap() {
		this.dieableMap.put(Monster.class, this.monsters);
		this.dieableMap.put(Projectile.class, this.projectiles);
		this.dieableMap.put(Mushroom.class, this.mushrooms);
		this.dieableMap.put(Bonus.class, this.bonuses);
	}

	public void addObject(Dieable objToAdd) {
		if (objToAdd instanceof Monster) {
			this.MM.incrementMonsterCounts((Monster) objToAdd);

		}
		ArrayList<Dieable> list;
		if (this.dieableMap.containsKey(objToAdd.getClass())) {
			list = this.dieableMap.get(objToAdd.getClass());
		} else {
			list = this.dieableMap.get(objToAdd.getClass().getSuperclass());
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
		int count = 0;
		for (Dieable bomb : this.projectiles) {
			if (bomb instanceof Bomb) {
				count++;
			}
		}
		return count;
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
			String nameString = JOptionPane
					.showInputDialog("What is your name?");
			HighestScoresBoard board = new HighestScoresBoard(this, nameString,
					this.score);
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
			this.ship = new Ship(10, BOTTOM_PLAYER_AREA);
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
		this.updateScoreboard();
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
		return (0 - 1 <= X && X + 2 * gap + obWidth <= ArcadeGame.width);
	}

	public boolean inGameY(double Y) {
		return (0 <= Y && Y <= ArcadeGame.height + 4);
	}

	// ////// GETTERS AND SETTERS ////// //

	public int getHeight() {
		return ArcadeGame.height;
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

	protected void setShip(Ship ship) {
		this.ship = ship;
	}

	protected int getLevelNum() {
		return this.levelNum;
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

	long getLastLevelChange() {
		return this.lastLevelChange;
	}

	boolean at_or_above_level(int target) {
		return getLevelNum() >= target;
	}
}
