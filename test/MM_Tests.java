import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MM_Tests {
	ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		ag = ArcadeGame.getInstance();
		ag.isPaused = true;
	}

	@Test
	public void testAddNewMonsters() {

		testForLevel(MonsterManager.FLEAS_START_LEVEL);
		testForLevel(MonsterManager.SCORPIONS_START_LEVEL);
		testForLevel(MonsterManager.ZOMBIES_START_LEVEL);

	}

	public void testForLevel(int level) {
		int target;
		target = MonsterManager.ZOMBIES_START_LEVEL;
		ag.setLevelNum(target - 1);
		assertFalse(ag.at_or_above_level(target));
		ag.setLevelNum(target);
		assertTrue(ag.at_or_above_level(target));
	}
}
