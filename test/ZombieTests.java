import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ZombieTests {
	
	static ArcadeGame g; 
	static Ship s; 
	static Zombie z; 

	@Before
	public void setUp() throws Exception {
		Main.main(null);
		System.out.println("SETUP");
		g = new ArcadeGame(100, 100);
		s = g.getShip();
		z = new Zombie(g, 50, -1);
		g.addObject(z);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}
	
	@Test
	public void testDie() {
		ArrayList<Dieable> inGame = g.getDieableParts(); 
		assertTrue(inGame.contains(z)); 
		z.die();
		inGame = g.getDieableParts(); 
		assertFalse(inGame.contains(z));
	}
	
	@Test
	public void testSwitchDirection () {
		assertTrue(z.checkMovingRight()); 
		z.switchXDirection();
		assertFalse(z.checkMovingRight());
	}

	@Test 
	public void testCheckMoveDown() {
		assertFalse(z.checkMoveDown()); 
		z.indicateMoveDown(); 
		assertTrue(z.checkMoveDown());
	}
	
	@Test
	public void testGetMoveX() {
		assertTrue(z.checkMovingRight()); 
		double nextX = z.getX() + z.getVelocityX(); 
		assertEquals(nextX, z.getMoveX(), 0); 
		
		z.switchXDirection();
		assertFalse(z.checkMovingRight()); 
		nextX = z.getX() - z.getVelocityX(); 
		assertEquals(nextX, z.getMoveX(), 0);
	}
	
	public void testGetNextPointNoDown() {
		//making sure getNextX method actually returns the correct value 
		//when not moving down 
		assertFalse(z.checkMoveDown()); 
		assertTrue(z.checkMovingRight()); 
		double nextX = z.getX() + z.getVelocityX(); 
		assertEquals(nextX, z.getNextX(), 0);
		assertEquals(z.getY(), z.getNextY(), 0);
	}
	
	public void testGetNextPointMoveDown() {
		Zombie f = new Zombie(g, 0, -1);
		assertFalse(f.checkMoveDown());
		double currentX = f.getX(); 
		assertEquals(currentX, f.getNextX(), 0);
		assertTrue(f.checkMoveDown()); 
		double nextY = f.getY() + f.getVelocityY(); 
		assertEquals(nextY, f.getNextY(), 0); 
		assertFalse(f.checkMoveDown());
		
	}
	
	
	

}
