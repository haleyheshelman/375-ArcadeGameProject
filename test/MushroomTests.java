import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MushroomTests implements ArcadeGameWiper {
	static ArcadeGame ag;

	@Before
	public void setUp() throws Exception {
		ag = ArcadeGame.getInstance();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Mushroom mushroom = new Mushroom(3, 3);
		mushroom.add();
		assertTrue(ag.getMushrooms().contains(mushroom));
	}

}
