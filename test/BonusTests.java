import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class BonusTests {
	static ArcadeGame ag; 
	static Bonus b; 
	static int offset =  Dieable.GRID_SIZE; 
	
	@Before 
	public void setUp() throws Exception {
		Main.scoreboard = new Scoreboard();
		ag = new ArcadeGame(318, 400); 
		b = new Bonus(ag) {
			@Override
			public int getRandX(){
				return 100; 
			}
			
			@Override 
			public int getRandY(){
				return 150;
			}
			
			@Override 
			public int getRandBonusType() {
				return 1;
			}
			
			@Override 
			public boolean checkObtain() {
				return true; 
			}
		};
	}
	
	@Test
	public void testCreateBonus() {
		assertFalse(ag.getDieableParts().contains(b));
		ag.addObject(b);
		assertTrue(ag.getDieableParts().contains(b));
		assertEquals(100*offset, b.getX(),0); 
		assertEquals(150*offset, b.getY(), 0);
	}
	
	@Test
	public void testSetBonusType() {
		assertEquals(1, b.getBonusTyp());
		assertEquals(Color.GRAY, b.getColor()); 
		
	}
	
	@Test 
	public void testSetColor() {
		assertEquals(Color.GRAY, b.getColor()); 
		b.setColor(2);
		assertEquals(Color.YELLOW, b.getColor()); 
		b.setColor(3);
		assertEquals(Color.ORANGE, b.getColor());
	}
	
	@Test
	public void testMove() {
		ag.getShip().decrementBombsRemaining();
		ag.getShip().decrementBombsRemaining();
		ag.getShip().decrementBombsRemaining();
		ag.getShip().decrementBombsRemaining();
		assertEquals(1, ag.getShip().bombsRemaining);
		b.move(); 
		assertEquals(5, ag.getShip().bombsRemaining);
	}
	




}
