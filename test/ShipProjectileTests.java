import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ShipProjectileTests {
	static Ship s; 
	static ArcadeGame ag; 
	
	@Before
	public void setUp() throws Exception {
		Main.main(null); 
		System.out.println("SETUP"); 
		ag = new ArcadeGame(100,100); 
		s = new Ship(ag, 10,16); 
	}
	
	@After 
	public void tearDown() throws Exception {
		System.out.println("teardown");
	}
	
	@Test
	public void testConstructor () {
		assertEquals(5,s.bombsRemaining);
		assertEquals(1,s.getProjectileType());
		assertEquals(0,ag.getProjectiles().size()); 
		
	}
	
	@Test 
	public void testSetProjectileType (){
		s.setProjectileType(2);
		assertEquals(2,s.getProjectileType());
		assertEquals(5, s.bombsRemaining);
		assertEquals(0,ag.getProjectiles().size());
		
		s.setProjectileType(3);
		assertEquals(3,s.getProjectileType());
		assertEquals(5, s.bombsRemaining);
		assertEquals(0,ag.getProjectiles().size());
		
		s.setProjectileType(4);
		assertEquals(4,s.getProjectileType());
		assertEquals(5, s.bombsRemaining);
		assertEquals(0,ag.getProjectiles().size());
		
	}
	
	@Test 
	public void testFireProjectileProjectileType1 () {
		assertEquals(5,s.bombsRemaining);
		assertEquals(1, s.getProjectileType()); 
		assertEquals(0, ag.getProjectiles().size());
		
		int beforeParts = ag.getDieableParts().size(); 
		s.fireProjectile(); 
		int afterParts = ag.getDieableParts().size();
		
		assertEquals(beforeParts+1, afterParts); 
		assertEquals(1, ag.getProjectiles().size());
		
		//check that the only thing in getProjectile is the new projectile 
		//assertEquals(1, (Projectile) ag.getProjectiles().get(0).getProjectileType());
		
		
		
		
		
		
		

		
		
		assertEquals(5,s.bombsRemaining);
		
	}
	
	
	

}
