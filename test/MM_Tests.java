import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MM_Tests {
	ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = new ArcadeGame(318, 400);
		ag.isPaused = true;
	}

	@Test
	public void testAddNewMonsters() {
		int target;

		target = MonsterManager.FLEAS_START_LEVEL;
		ag.setLevelNum(target - 1);
		assertFalse(ag.at_or_above_level(target));
		ag.setLevelNum(target);
		assertTrue(ag.at_or_above_level(target));
		
		target = MonsterManager.SCORPIONS_START_LEVEL;
		ag.setLevelNum(target - 1);
		assertFalse(ag.at_or_above_level(target));
		ag.setLevelNum(target);
		assertTrue(ag.at_or_above_level(target));

		
		target = MonsterManager.ZOMBIES_START_LEVEL;
		ag.setLevelNum(target - 1);
		assertFalse(ag.at_or_above_level(target));
		ag.setLevelNum(target);
		assertTrue(ag.at_or_above_level(target));


	}
}
