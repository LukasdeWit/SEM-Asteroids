package entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import game.Game;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AsteroidTest {
	/**
	 * Starting value of x.
	 */
	private static final float X_START = 1;
	/**
	 * Starting value of y.
	 */
	private static final float Y_START = 2;
	/**
	 * Starting value of dX.
	 */
	private static final float DX_START = 3;
	/**
	 * Starting value of dY.
	 */
	private static final float DY_START = 4;
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
	 */
	@Before
	public final void setUp() {
		mockGame = mock(Game.class);
		ceres = new Asteroid(X_START, Y_START, DX_START, DY_START, mockGame);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor() {
		//Assert that the Asteroid was constructed.
		assertFalse(ceres == null);
		//Assert that the Asteroid's properties are correct.
		assertTrue(ceres.getX() == X_START);
		assertTrue(ceres.getY() == Y_START);
		assertTrue(ceres.getDX() == DX_START);
		assertTrue(ceres.getDY() == DY_START);
		assertTrue(ceres.getThisGame() == mockGame);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor2() {
		ceres = new Asteroid(X_START, Y_START, .2f, .4f, mockGame);
		assertFalse(ceres == null);
		assertTrue(ceres.getX() == X_START);
		assertTrue(ceres.getY() == Y_START);
		//Low speed should be doubled, until fast enough.
		assertTrue(ceres.getDX() == .4f);
		assertTrue(ceres.getDY() == .8f);
		assertTrue(ceres.getThisGame() == mockGame);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testSecondConstructor() {
		ceres = new Asteroid(X_START, Y_START, DX_START, DY_START, 23, mockGame);
		assertFalse(ceres == null);
		assertTrue(ceres.getX() == X_START);
		assertTrue(ceres.getY() == Y_START);
		assertTrue(ceres.getDX() == DX_START);
		assertTrue(ceres.getDY() == DY_START);
		//size is also set in this constructor
		assertTrue(ceres.getRadius() == 23);
		assertTrue(ceres.getThisGame() == mockGame);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate() {
		List<String> input = new ArrayList<String>(0);
		ceres.update(input);
		assertTrue(ceres.getX() == X_START + DX_START);
		assertTrue(ceres.getY() == Y_START + DY_START);
	}

	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide() {
		Bullet e2 = new Bullet(X_START,Y_START,0,0,mockGame);
		ceres.collide(e2);
		verify(mockGame, times(2)).destroy(Mockito.any(AbstractEntity.class));
	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		Player e2 = new Player(X_START,Y_START,0,0,mockGame);
		ceres.collide(e2);
		verify(mockGame, never()).destroy(Mockito.any(AbstractEntity.class));
	}

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		ceres.onDeath();
		verify(mockGame, times(2)).create(Mockito.any(Asteroid.class));
		verify(mockGame).addScore(20);
	}

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath2() {
		ceres.setRadius(12);
		ceres.onDeath();
		verify(mockGame, times(2)).create(Mockito.any(Asteroid.class));
		verify(mockGame).addScore(50);
	}

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath3() {
		ceres.setRadius(4);
		ceres.onDeath();
		verify(mockGame, never()).create(Mockito.any(Asteroid.class));
		verify(mockGame).addScore(100);
	}

}