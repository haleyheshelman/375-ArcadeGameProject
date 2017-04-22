import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShipProjectileTests implements ArcadeGameWiper {
	static Ship s;
	static ArcadeGame ag;

	@Before
	public void setUp() throws Exception {

		ArcadeGame.resetArcadeGame();
		// Main.main(new String[]{"mute"});
		// System.out.println("SETUP");
		ag = ArcadeGame.getInstance();
		s = new Ship(10, 16) {
			@Override
			public boolean firedTooRecently(long curTime) {
				return false;
			}
		};
		Bomb.setBombsRemaining(Bomb.DEF_STARTING_BOMBS);
	}

	@After
	public void tearDown() throws Exception {
		ArcadeGame.resetArcadeGame();
	}

	@Test
	public void testConstructor() {
		assertEquals(5, Bomb.getBombsRemaining());
		assertEquals(Bullet.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

	}

	@Test
	public void testSetProjectileType() {
		Class[] projectileTypes = new Class[] { Bullet.class,
				ExplodingBullet.class, Bomb.class, Missile.class,
				ShotGun.class };
		for (Class type : projectileTypes) {
			trySetFor(type);
		}
	}

	public void trySetFor(Class clazz) {
		s.setProjectileType(clazz);
		assertEquals(clazz, s.getProjectileType());
		assertEquals(5, Bomb.getBombsRemaining());
		assertEquals(0, ag.getProjectiles().size());
	}

	public void waitToFire() {
		long startTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		while (currentTime - startTime < 200) {
			currentTime = System.currentTimeMillis();
		}
	}

	@Test
	public void testFireProjectileType1() {
		assertEquals(5, Bomb.getBombsRemaining());
		assertEquals(Bullet.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

		int beforeParts = ag.getDieableParts().size();
		assertEquals(Bullet.class, s.getProjectileType());
		s.fireProjectile();
		int afterParts = ag.getDieableParts().size();
		assertEquals(beforeParts + 1, afterParts);
		assertEquals(1, ag.getProjectiles().size());

		// check that the only thing in getProjectile is the new bullet
		// can't getProjectileType on dieable... what to do?
		// assertEquals(1, ((Projectile)
		// ag.getProjectiles().get(0)).getProjectileType());

		assertTrue(ag.getProjectiles().get(0) instanceof Bullet);

		assertEquals(5, Bomb.getBombsRemaining());
		waitToFire();

	}

	@Test
	public void testFireProjectileType2() {
		assertEquals(5, Bomb.getBombsRemaining());
		s.setProjectileType(Missile.class);
		assertEquals(Missile.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

		int beforeParts = ag.getDieableParts().size();
		s.fireProjectile();
		int afterParts = ag.getDieableParts().size();

		assertEquals(beforeParts + 1, afterParts);
		assertEquals(1, ag.getProjectiles().size());
		assertTrue(ag.getProjectiles().get(0) instanceof Missile);

		assertEquals(5, Bomb.getBombsRemaining());

		waitToFire();

	}

	@Test
	public void testFireProjectileType3() {
		assertEquals(5, Bomb.getBombsRemaining());
		s.setProjectileType(ShotGun.class);
		assertEquals(ShotGun.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

		int beforeParts = ag.getProjectiles().size();
		s.fireProjectile();
		int afterParts = ag.getProjectiles().size();

		assertEquals(beforeParts + 3, afterParts);
		assertEquals(3, ag.getProjectiles().size());

		assertTrue(ag.getProjectiles().get(0) instanceof Bullet);
		assertTrue(ag.getProjectiles().get(1) instanceof Bullet);
		assertTrue(ag.getProjectiles().get(2) instanceof Bullet);

		assertEquals(5, Bomb.getBombsRemaining());

		waitToFire();

	}

	@Test
	public void testFireProjectileType4() {
		assertEquals(5, Bomb.getBombsRemaining());
		s.setProjectileType(Bomb.class);
		assertEquals(Bomb.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

		int beforeParts = ag.getProjectiles().size();
		s.fireProjectile();
		int afterParts = ag.getProjectiles().size();

		assertEquals(beforeParts + 1, afterParts);
		assertEquals(1, ag.getProjectiles().size());
		assertTrue(ag.getProjectiles().get(0) instanceof Bomb);
		assertEquals(4, Bomb.getBombsRemaining());
		waitToFire();

		s.fireProjectile();
		assertEquals(2, ag.getProjectiles().size());
		assertEquals(3, Bomb.getBombsRemaining());
		assertTrue(ag.getProjectiles().get(1) instanceof Bomb);
		waitToFire();

		s.fireProjectile();
		assertEquals(3, ag.getProjectiles().size());
		assertEquals(2, Bomb.getBombsRemaining());
		assertTrue(ag.getProjectiles().get(2) instanceof Bomb);
		waitToFire();

		s.fireProjectile();
		assertEquals(4, ag.getProjectiles().size());
		assertEquals(1, Bomb.getBombsRemaining());
		assertTrue(ag.getProjectiles().get(3) instanceof Bomb);
		waitToFire();

		s.fireProjectile();
		assertEquals(5, ag.getProjectiles().size());
		assertEquals(0, Bomb.getBombsRemaining());
		assertTrue(ag.getProjectiles().get(4) instanceof Bomb);
		waitToFire();

		s.fireProjectile();
		assertEquals(5, ag.getProjectiles().size());
		assertEquals(0, Bomb.getBombsRemaining());
		waitToFire();

		s.setProjectileType(Bullet.class);
		s.fireProjectile();
		assertEquals(6, ag.getProjectiles().size());
		assertEquals(0, Bomb.getBombsRemaining());
		assertTrue(ag.getProjectiles().get(5) instanceof Bullet);
		waitToFire();

		s.setProjectileType(Bomb.class);
		assertEquals(Bomb.class, s.getProjectileType());
		s.fireProjectile();
		assertEquals(6, ag.getProjectiles().size());
		assertEquals(0, Bomb.getBombsRemaining());
		waitToFire();

	}

	@Test
	public void testFireProjectileType5() {
		assertEquals(5, Bomb.getBombsRemaining());
		s.setProjectileType(ExplodingBullet.class);
		assertEquals(ExplodingBullet.class, s.getProjectileType());
		assertEquals(0, ag.getProjectiles().size());

		int beforeParts = ag.getDieableParts().size();
		s.fireProjectile();
		int afterParts = ag.getDieableParts().size();

		assertEquals(beforeParts + 1, afterParts);
		assertEquals(1, ag.getProjectiles().size());
		assertTrue(ag.getProjectiles().get(0) instanceof ExplodingBullet);

		assertEquals(5, Bomb.getBombsRemaining());

		waitToFire();

	}

}
