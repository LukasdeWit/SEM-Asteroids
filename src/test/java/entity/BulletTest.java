package entity;

import game.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	private Game thisGame;
	/**
	 * Test bullet.
	 */
	private Bullet bill;
	/**
	 * Test bullet #2.
	 */
	private Bullet ball;

	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		bill = new Bullet(X_START, Y_START, DX_START, DY_START, thisGame);
		//Used for testing methods involving the piercing attribute.
		ball = new Bullet(X_START,Y_START, DX_START, DY_START, 3, thisGame);
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
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
        assertTrue(thisGame.getDestroyList().contains(bill));
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
		Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		bill.collide(e2);
        assertTrue(thisGame.getDestroyList().contains(bill));
        assertTrue(thisGame.getDestroyList().contains(e2));
		Asteroid e3 = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		ball.collide(e3);
        assertFalse(thisGame.getDestroyList().contains(ball));
        assertTrue(thisGame.getDestroyList().contains(e3));
     }
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		Player e2 = new Player(X_START, Y_START, 0, 0, thisGame, false);
		bill.collide(e2);
        assertFalse(thisGame.getDestroyList().contains(bill));
        assertFalse(thisGame.getDestroyList().contains(e2));
        }

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		bill.onDeath();
        assertFalse(thisGame.getDestroyList().contains(bill));
        }

}
