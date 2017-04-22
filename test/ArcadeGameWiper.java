import org.junit.AfterClass;
import org.junit.BeforeClass;

public interface ArcadeGameWiper {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ArcadeGame.resetArcadeGame();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ArcadeGame.resetArcadeGame();
	}
}
