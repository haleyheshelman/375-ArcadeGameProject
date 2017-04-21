import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectileBulletTests {
	static Projectile b;
	static ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = new ArcadeGame(318, 400);
		b = new Bullet(new Point2D.Double(40, 40));
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
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
		for (Dieable d : ag.getDieableParts()) {
			ag.MM.removeObject(ag, d);
		}
		b.setVelocityX(0);
		b.setVelocityY(0);
		b.move();
		assertFalse(b.checkHit());

		int gridX = 3;
		int gridY = 3;
		b.setTLPoint(new Point2D.Double(gridX * Dieable.GRID_SIZE, gridY * Dieable.GRID_SIZE));
		Mushroom hitMushroom = new Mushroom(ag, gridX, gridY);
		ag.addObject(hitMushroom);
		assertTrue(b.checkHit());
		ag.MM.removeObject(ag, hitMushroom);
		assertFalse(b.checkHit());
	}

}
