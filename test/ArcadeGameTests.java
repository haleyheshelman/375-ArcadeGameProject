import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArcadeGameTests {
	ArcadeGame ag;
	Point2D.Double[] inGame = new Point2D.Double[2];
	Point2D.Double[] outOfGame = new Point2D.Double[2];

	Dieable d;

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = new ArcadeGame(318, 400);
		d = new Dieable(ag, 0, 0) {
			@Override
			public void move() {
				// do nothing
			}
		};

		inGame[0] = new Point2D.Double(-1, 0);
		inGame[1] = new Point2D.Double(ag.width - d.width - 2 * d.gap,
				ag.height + 4);

		outOfGame[0] = new Point2D.Double(-2, -1);
		outOfGame[1] = new Point2D.Double(ag.width - d.width - 2 * d.gap + 1,
				ag.height + 5);

		ag.isPaused = true;
	}

	@Test
	public void test_createLevel() {
		assertEquals(1, ag.getLevelNum());
		assertEquals(ag.lastBonusTime, ag.MM.getLastScorpionTime());
	}

	@After
	public void tearDown() throws Exception {
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

}
