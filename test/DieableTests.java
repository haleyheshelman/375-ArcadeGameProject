import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

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
	public void testSetupForShip(){
		assertEquals(d.getHealth(), 10);
		assertEquals(d.getGame(), game);
		assertEquals(d.getColor(), Color.RED);
		
	}
	
	
	@Test
	public void testDie(){
		ArrayList<Dieable> inGame = game.getDieableParts();
		System.out.println(inGame);
		System.out.println(d);
		assertTrue(inGame.contains(d));
		
		
	}
	
	@Test
	public void testRemoveHealth(){
		assertEquals(d.getHealth(), 10);
		for(int i = 1; i<d.getHealth(); i++){
			d.removeHealth(1);
			assertEquals(d.getHealth(), 10 - i);
		}
	}
}
