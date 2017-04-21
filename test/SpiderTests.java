import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpiderTests {

	static ArcadeGame g; 
	static Ship s; 
	static Spider spidey; 

	@Before
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		System.out.println("SETUP");
		spidey = new Spider(g){
			@Override 
			public int getRandomSwitch(){
				return 10;
			}
		};
		g = new ArcadeGame(100, 100);
		MonsterManager m = new MonsterManager(g){
			@Override
			public void addSpiders(){
				g.addObject(spidey);
			}
		};
		g.setMonsterManager(m);
		s = g.getShip();
	}
	
	@Test
	public void testSetup(){
		ArrayList<Dieable> inGame = g.getDieableParts(); 
		assertFalse(inGame.contains(spidey));
		g.addObject(spidey);
		assertTrue(inGame.contains(spidey));
		
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}
	
	@Test
	public void testThing(){
		System.out.println("Hello from the other side");
	}
	
}
