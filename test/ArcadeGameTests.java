import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArcadeGameTests implements ArcadeGameWiper {
	static ArcadeGame ag;
	static Point2D.Double[] inGame = new Point2D.Double[2];
	static Point2D.Double[] outOfGame = new Point2D.Double[2];

	static Dieable d;

	@Before
	public void setUp() throws Exception {
		ag = new ArcadeGame() {
			@Override
			public void updateScoreboard() {
				// nothing
			}
		};

		d = new Dieable(0, 0) {
			@Override
			public void move() {
				// do nothing
			}
		};

		inGame[0] = new Point2D.Double(-1, 0);
		inGame[1] = new Point2D.Double(ArcadeGame.width - d.width - 2 * d.gap,
				ArcadeGame.height + 4);

		outOfGame[0] = new Point2D.Double(-2, -1);
		outOfGame[1] = new Point2D.Double(
				ArcadeGame.width - d.width - 2 * d.gap + 1,
				ArcadeGame.height + 5);

		ag.isPaused = true;
	}

	@After
	public void tearDown() throws Exception {
		ArcadeGame.resetArcadeGame();
	}

	@Test
	public void test_addObject() {
		Mushroom m = new Mushroom(5, 5);
		assertFalse(ag.getMushrooms().contains(m));
		ag.addObject(m);
		assertTrue(ag.getMushrooms().contains(m));
	}

	@Test
	public void test_createLevel() {
		assertEquals(1, ag.getLevelNum());
		assertEquals(ag.lastBonusTime, ag.MM.getLastScorpionTime());
	}

	@Test
	public void testInGame_IN() {

		for (Point2D.Double point : inGame) {
			d.setTLPoint(point);
			assertTrue(ag.inGameX(d.getX(), d.gap, d.width));
			assertTrue(ag.inGameY(d.getY()));
		}
	}

	@Test
	public void testInGame_OUT() {

		for (Point2D.Double point : outOfGame) {
			d.setTLPoint(point);
			assertFalse(ag.inGameX(d.getX(), d.gap, d.width));
			assertFalse(ag.inGameY(d.getY()));
		}
	}

	@Test
	public void test_resetMonsterCounts() {
		ag.MM.resetMonsterCounts();
		assertEquals(0, ag.MM.getNumCentipedes());
		assertEquals(0, ag.MM.getNumSpiders());
		assertEquals(0, ag.MM.getNumFleas());
		assertFalse(ag.MM.alreadyAddedScorpion);
	}

	@Test
	public void testShipSetup() {
		Ship ship = ag.getShip();
	}
}
