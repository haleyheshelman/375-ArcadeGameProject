import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

public class ProjectileBulletTests {
	static Projectile b;
	static ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		Main.main(null);
		System.out.println("SETUP");
		ag = new ArcadeGame(100, 100);
		b = new Bullet(ag, new Point2D.Double(40,40));
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}

	@Test
	public void testMove() {

		System.out.println("Testing move()");
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
		for (Dieable d:ag.getDieableParts()){
			ag.removeObject(d);
		}
		b.setVelocityX(0);
		b.setVelocityY(0);
		b.move();
		assertFalse(b.checkHit());
		ag.addObject(new Mushroom(ag, b.getX(), b.getY()));
		for (Dieable d:ag.getDieableParts()){
			System.out.println(d);
		}
		assertTrue(b.checkHit());
	}

}
