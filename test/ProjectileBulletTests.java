import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ProjectileBulletTests {
	static Projectile b;
	static ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = ArcadeGame.getInstance();
		b = new Bullet(new Point2D.Double(40, 40));
	}

	@Test
	public void testMove() {
		Random r = new Random();
		int dX = r.nextInt(15);
		int dY = r.nextInt(15);
		double x0 = b.getCenterPoint().getX();
		double y0 = b.getCenterPoint().getY();
		b.setVelocityX(dX);
		b.setVelocityY(dY);
		b.move();
		assertEquals(dX + x0, b.getCenterPoint().getX(), 0.1);
		assertEquals(dY + y0, b.getCenterPoint().getY(), 0.1);
	}

	@Test
	public void testGetShape() {
		Shape s = b.getShape();
		assertTrue(s.contains(b.getCenterPoint()));
	}

	@Test
	public void testCheckHit() {
		/* completely clear the board */
		for (Dieable d : ag.getDieableParts()) {
			ag.MM.removeObject(ag, d);
		}
		/* give bullet zero velocity */
		b.setVelocityX(0);
		b.setVelocityY(0);
		b.move();
		assertFalse(b.checkHit());

		int gridX = 3;
		int gridY = 3;
		b.setTLPoint(new Point2D.Double(gridX * ArcadeGame.GRID_SIZE,
				gridY * ArcadeGame.GRID_SIZE));
		Mushroom hitMushroom = Mushroom.generateAtGrid(gridX, gridY);
		/* assert that bullet hit mushroom */
		assertTrue(b.checkHit());
		ag.MM.removeObject(ag, hitMushroom);
		/* assert that bullet no longer hits the (now-removed) mushroom */
		assertFalse(b.checkHit());
	}

}
