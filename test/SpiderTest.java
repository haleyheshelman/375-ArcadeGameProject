import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpiderTest {
	static ArcadeGame g; 
	static Spider spidey; 
	
	
	@Before 
	public void setUp() throws Exception {
		System.out.println("setup");
		Main.scoreboard = new Scoreboard(); 
		g = new ArcadeGame(318, 400){
			@Override 
			public void addObject(Dieable objToAdd){
				this.monsters.add(objToAdd);
			}
		};
		spidey = new Spider (g){
			@Override 
			public int getRandomSwitch() {
				return 2;
			}
		};
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}
	
	@Test 
	public void testSetup() {
		assertFalse(g.getDieableParts().contains(spidey)); 
		assertFalse(g.getDieableParts().contains(spidey)); 
		g.addObject(spidey); 
		assertTrue(g.getDieableParts().contains(spidey));
	}
	
	
	

}