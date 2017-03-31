import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DieableTests {

	static Dieable d;
	static ArcadeGame game;

	@Before
	public void setUp() throws Exception {
		Main.main(null);
		System.out.println("SETUP");
		game = new ArcadeGame(100, 100);
		d = game.getShip();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}

	@Test
	public void testSetupForShip() {
		assertEquals(d.getHealth(), 10);
		assertEquals(d.getGame(), game);
		assertEquals(d.getColor(), Color.RED);

	}

	@Test
	public void testDie() {
		ArrayList<Dieable> inGame = game.getDieableParts();
		System.out.println(inGame);
		System.out.println(d);
		assertTrue(inGame.contains(d));
		d.die();
		inGame = game.getDieableParts();
		assertFalse(d.equals(game.getShip()));

	}

	@Test
	public void testRemoveHealth() {
		assertEquals(d.getHealth(), 10);
		for (int i = 1; i < d.getHealth(); i++) {
			d.removeHealth(1);
			assertEquals(d.getHealth(), 10 - i);
		}
	}

	@Test
	public void getGame() throws IOException {
		ArcadeGame g = d.getGame();
		assertTrue(g.equals(game));

		ArcadeGame newGame = new ArcadeGame(100, 100);
		assertFalse(g.equals(newGame));

	}

	@Test
	public void testSetImage() {
		BufferedImage i = d.getImage();
		System.out.println(i);
		assertEquals(null, i);

		// Is never used in project

	}

	@Test
	public void testGetImage() {
		BufferedImage i = d.getImage();
		System.out.println(i);
		assertEquals(null, i);

		// Is never used in project

	}

	@Test
	public void testSetVelocityY() {
		assertEquals(d.getVelocityY(), 4, 0);
		d.setVelocityY(25);
		assertEquals(d.getVelocityY(), 25, 0);
	}

	@Test
	public void testGetVelocityY() {
		assertEquals(d.getVelocityY(), 4, 0);
		d.setVelocityY(25);
		assertEquals(d.getVelocityY(), 25, 0);
	}

	@Test
	public void testSetVelocityX() {
		assertEquals(d.getVelocityX(), 2.5, 0);
		d.setVelocityX(25);
		assertEquals(d.getVelocityX(), 25, 0);
	}

	@Test
	public void testGetVelocityX() {
		assertEquals(d.getVelocityX(), 2.5, 0);
		d.setVelocityX(25);
		assertEquals(d.getVelocityX(), 25, 0);
	}

	@Test
	public void testGetColor() {
		assertEquals(Color.RED, d.getColor());
	}

	@Test
	public void testSetColor() {
		assertEquals(Color.RED, d.getColor());
		d.setColor(Color.CYAN);
		assertEquals(Color.CYAN, d.getColor());
	}

	@Test
	public void testGetHealth() {
		assertEquals(d.getHealth(), 10);
	}

	@Test
	public void testSetHealth() {
		assertEquals(d.getHealth(), 10);
		d.setHealth(50);
		assertEquals(d.getHealth(), 50);
	}

	@Test
	public void testGetTLPoint() {
		Point2D p = new Point();
		p.setLocation(200.0, 320.0);
		System.out.println(this.d.getTLPoint());
		assertTrue(p.equals(d.getTLPoint()));

		Point2D t = new Point();
		p.setLocation(250.0, 300.0);

		d.setTLPoint(t);

		assertFalse(p.equals(d.getTLPoint()));
		assertTrue(t.equals(d.getTLPoint()));
	}

	@Test
	public void testGetCenterPoint() {
		Point2D p = new Point();
		p.setLocation(210.0, 330.0);
		assertTrue(p.equals(d.getCenterPoint()));

	}

	@Test
	public void testSetCenterPoint() {
		Point2D p = new Point();
		p.setLocation(210.0, 330.0);
		assertTrue(p.equals(d.getCenterPoint()));

		Point2D test = new Point();
		test.setLocation(250.0, 300.0);

		d.setCenterPoint(test);

		assertFalse(p.equals(d.getCenterPoint()));
		assertTrue(test.equals(d.getCenterPoint()));

	}

	@Test
	public void testGetShape() {
		Shape r = new Rectangle2D.Double(d.getTLPoint().getX() + d.gap,
				d.getTLPoint().getY() + d.gap, d.width, d.height);
		assertTrue(r.equals(d.getShape()));

	}

}
