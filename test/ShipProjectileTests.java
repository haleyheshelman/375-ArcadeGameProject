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
		//System.out.println("SETUP"); 
		ag = new ArcadeGame(318, 400); 
		s = new Ship(ag, 10,16); 
	}
	
	@After 
	public void tearDown() throws Exception {
		//System.out.println("teardown");
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
	
	public void waitToFire() {
		long startTime = System.currentTimeMillis(); 
		long currentTime = System.currentTimeMillis(); 
		while (currentTime - startTime < 200){
			currentTime = System.currentTimeMillis(); 
		}
	}
	
	@Test 
	public void testFireProjectileType1 () {
		assertEquals(5,s.bombsRemaining);
		assertEquals(1, s.getProjectileType()); 
		assertEquals(0, ag.getProjectiles().size());
		
		int beforeParts = ag.getDieableParts().size(); 
		s.fireProjectile(); 
		int afterParts = ag.getDieableParts().size();
		
		assertEquals(beforeParts+1, afterParts); 
		assertEquals(1, ag.getProjectiles().size());
		
		//check that the only thing in getProjectile is the new bullet 
		//can't getProjectileType on dieable... what to do? 
		//assertEquals(1, (Projectile) ag.getProjectiles().get(0).getProjectileType());
		
		assertEquals(5,s.bombsRemaining);
		waitToFire(); 
		 
	}
	
	@Test 
	public void testFireProjectileType2() {
		assertEquals(5, s.bombsRemaining); 
		s.setProjectileType(2);
		assertEquals(2, s.getProjectileType()); 
		assertEquals(0, ag.getProjectiles().size());
		
		int beforeParts = ag.getDieableParts().size(); 
		System.out.println(beforeParts);
		s.fireProjectile();
		int afterParts = ag.getDieableParts().size();
		System.out.println(afterParts);
		
		//assertEquals(beforeParts+1, afterParts); 
		assertEquals(1, ag.getProjectiles().size()); 
		
		assertEquals(5, s.bombsRemaining); 

		waitToFire(); 
		
	}
	
	@Test 
	public void testFireProjectileType3() {
		assertEquals(5, s.bombsRemaining); 
		s.setProjectileType(3);
		assertEquals(3, s.getProjectileType()); 
		assertEquals(0, ag.getProjectiles().size());
		
		int beforeParts = ag.getDieableParts().size(); 
		s.fireProjectile();
		int afterParts = ag.getDieableParts().size(); 
		
		assertEquals(beforeParts+3, afterParts); 
		assertEquals(3, ag.getProjectiles().size()); 
		
		assertEquals(5, s.bombsRemaining); 
		
		waitToFire(); 
		
	}
	
	@Test 
	public void testFireProjectileType4() {
		assertEquals(5, s.bombsRemaining); 
		s.setProjectileType(4);
		assertEquals(4, s.getProjectileType()); 
		assertEquals(0, ag.getProjectiles().size()); 
		
		int beforeParts = ag.getDieableParts().size(); 
		s.fireProjectile(); 
		int afterParts = ag.getDieableParts().size(); 
		
		assertEquals(beforeParts+1, afterParts); 
		assertEquals(1, ag.getProjectiles().size()); 
		
		assertEquals(4, s.bombsRemaining); 
		waitToFire(); 
		
		s.fireProjectile();
		assertEquals(2, ag.getProjectiles().size()); 
		assertEquals(3, s.bombsRemaining);
		waitToFire();
		
		s.fireProjectile();
		assertEquals(3, ag.getProjectiles().size()); 
		assertEquals(2, s.bombsRemaining);
		waitToFire();
		
		s.fireProjectile();
		assertEquals(4, ag.getProjectiles().size()); 
		assertEquals(1, s.bombsRemaining);
		waitToFire();
		
		s.fireProjectile();
		assertEquals(5, ag.getProjectiles().size()); 
		assertEquals(0, s.bombsRemaining);
		waitToFire();
		
		s.fireProjectile();
		assertEquals(5, ag.getProjectiles().size()); 
		assertEquals(0, s.bombsRemaining);
		waitToFire();
		
		s.setProjectileType(1);
		s.fireProjectile();
		assertEquals(6, ag.getProjectiles().size()); 
		assertEquals(0, s.bombsRemaining); 
		waitToFire(); 
		
		s.setProjectileType(4);
		assertEquals(4, s.getProjectileType()); 
		s.fireProjectile();
		assertEquals(6, ag.getProjectiles().size()); 
		assertEquals(0, s.bombsRemaining); 
		
	}
	
	

}
