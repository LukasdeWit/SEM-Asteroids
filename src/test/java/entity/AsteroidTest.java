package entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import game.Game;

//@RunWith(MockitoJUnitRunner.class)
/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class AsteroidTest {
	/**
	 * Test Asteroid.
	 */
	private Asteroid ceres;
	/**
	 * Mocked Game.
	 */
	private Game mockGame;

	/**
	 * Setup for tests.
	 * @throws Exception - the exception
	 */
	@Before
	public final void setUp() throws Exception {
//		mockGame = new Game();
//		mockGame = mock(Game.class);
		/**Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		mockGame = new Game(gc);**/
		ceres = new Asteroid(0, 0, 0, 0, mockGame);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor() {
		//Assert that the Asteroid was constructed.
		assertFalse(ceres == null);
		//Assert that the Asteroid's properties are correct.
		assertTrue(ceres.getDX() == 0);
		assertTrue(ceres.getDY() == 0);
		assertTrue(ceres.getX() == 0);
		assertTrue(ceres.getY() == 0);
		assertTrue(ceres.getThisGame() == mockGame);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test draw.
	 */
	@Test
	public final void testDraw() {
		fail("Not yet implemented");
	}

	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide() {
		fail("Not yet implemented");
	}

	/**
	 * Test Asteroid.
	 */
	@Test
	public final void testAsteroid() {
		fail("Not yet implemented");
	}

	/**
	 * Test Split.
	 */
	@Test
	public final void testSplit() {
		fail("Not yet implemented");
	}

}