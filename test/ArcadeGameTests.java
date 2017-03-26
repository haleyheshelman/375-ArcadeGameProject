import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

public class ArcadeGameTests {
	ArcadeGame ag;
	  Point2D.Double[] inGame = new Point2D.Double[6];
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
		
		inGame[0] = new Point2D.Double(-1, 20);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInGameX_IN() {
		d.setTLPoint(new Point2D.Double(-1, 20));
		assertTrue(ag.inGameX(d.getX(), d.gap, d.width));
	}

	@Test
	public void testInGameLeft() {
	}

}
