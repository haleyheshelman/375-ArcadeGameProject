import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		g = new ArcadeGame(){
			@Override 
			public void addObject(Dieable objToAdd){
				this.monsters.add(objToAdd);
			}
		};
		spidey = new Spider (){
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
		spidey.add();
		assertTrue(g.getDieableParts().contains(spidey));
	}
	
	
	

}