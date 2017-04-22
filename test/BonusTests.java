import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class BonusTests implements ArcadeGameWiper {
	static ArcadeGame ag;
	static Bonus b;
	static int offset = ArcadeGame.GRID_SIZE;
	public static int X;
	public static int Y;
	public static int BONUS_TYPE;

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = new ArcadeGame();
		b = new Bonus() {
			@Override
			protected int getRandX() {
				X = super.getRandX();
				return X;
			}

			@Override
			protected int getRandY() {
				Y = super.getRandY();
				return Y;
			}

			@Override
			public int getRandBonusType() {
				BONUS_TYPE = super.getRandBonusType();
				return BONUS_TYPE;
			}
		};
	}

	@Test
	public void testAdd() {
		long timeBefore = System.currentTimeMillis();
		b.add();
		long timeAfter = System.currentTimeMillis();
		assertTrue(timeBefore <= ag.lastBonusTime);
		assertTrue(timeAfter >= ag.lastBonusTime);
	}

	@Test
	public void testCreateBonus() {
		assertFalse(ag.getDieableParts().contains(b));
		ag.addObject(b);
		assertTrue(ag.getDieableParts().contains(b));
		assertEquals(X * offset, b.getX(), 0);
		assertEquals(Y * offset, b.getY(), 0);
	}

	@Test
	public void testSetColor() {
		b.setColor(Bonus.BOMB_TYPE);
		assertEquals(Color.GRAY, b.getColor());
		b.setColor(Bonus.SCORE_TYPE);
		assertEquals(Color.YELLOW, b.getColor());
		b.setColor(Bonus.LIFE_TYPE);
		assertEquals(Color.ORANGE, b.getColor());
	}

	@Test
	public void testMove() {
		b.setBonusType(1);
		int startVal = Bomb.getBombsRemaining();
		while (Bomb.getBombsRemaining() > 1) {
			Bomb.decrementBombsRemaining();
			assertEquals(--startVal, Bomb.getBombsRemaining());
		}
		assertEquals(1, Bomb.getBombsRemaining());
		new Ship(X + 3, Y + 3).add();
		b.move();
		assertEquals(1, Bomb.getBombsRemaining());
		new Ship(X, Y).add();
		b.move();

		assertEquals(5, Bomb.getBombsRemaining());

	}

	@Test
	public void testCheckObtain() {
		new Ship(X + 2, Y + 2).add();
		boolean res = b.checkObtain();

		assertFalse(res);
		new Ship(X, Y).add();
		assertTrue(b.checkObtain());
	}

}
