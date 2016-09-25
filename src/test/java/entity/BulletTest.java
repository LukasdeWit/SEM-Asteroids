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
 * Tests for Bullet.
 * @author Kibo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BulletTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	/**
	 * Test Asteroid.
	 */
	private Bullet bill;
	/**
	 * Mocked Game.
	 */
	private Game mockGame;

	@Before
	public void setUp() throws Exception {
		mockGame = mock(Game.class);
		bill = new Bullet(X_START, Y_START, DX_START, DY_START, mockGame);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor() {
		assertFalse(bill == null);
		assertTrue(bill.getX() == X_START);
		assertTrue(bill.getY() == Y_START);
		assertTrue(bill.getDX() == DX_START);
		assertTrue(bill.getDY() == DY_START);
		assertTrue(bill.getThisGame() == mockGame);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate() {
		List<String> input = new ArrayList<String>(0);
		bill.update(input);
		assertTrue(bill.getX() == X_START + DX_START);
		assertTrue(bill.getY() == Y_START + DY_START);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate2() {
		List<String> input = new ArrayList<String>(0);
		bill.setBirthTime(0);
		bill.update(input);
		verify(mockGame).destroy(bill);
	}
	
	/**
	 * Test isFriendly.
	 */
	@Test
	public final void testIsFriendly() {
		bill.setFriendly(true);
		assertTrue(bill.isFriendly());
	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide() {
		Asteroid e2 = new Asteroid(X_START,Y_START,DX_START,DY_START,mockGame);
		bill.collide(e2);
		verify(mockGame, times(2)).destroy(Mockito.any(AbstractEntity.class));
	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		Player e2 = new Player(X_START,Y_START,0,0,mockGame);
		bill.collide(e2);
		verify(mockGame, never()).destroy(Mockito.any(AbstractEntity.class));
	}

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		bill.onDeath();
		verify(mockGame, never()).destroy(Mockito.any(AbstractEntity.class));
	}

}
