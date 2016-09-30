package entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
	 * Test bullet.
	 */
	private Bullet bill;
	/**
	 * Test bullet #2.
	 */
	private Bullet ball;

	@Before
	public void setUp() throws Exception {
		bill = new Bullet(X_START, Y_START, DX_START, DY_START);
		//Used for testing methods involving the piercing attribute.
		ball = new Bullet(X_START,Y_START, DX_START, DY_START, 3);
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
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
        assertTrue(Game.getInstance().getDestroyList().contains(bill));
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
		Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START);
		bill.collide(e2);
        assertTrue(Game.getInstance().getDestroyList().contains(bill));
        assertTrue(Game.getInstance().getDestroyList().contains(e2));
        Asteroid e3 = new Asteroid(X_START, Y_START, DX_START, DY_START);
		ball.collide(e3);
        assertFalse(Game.getInstance().getDestroyList().contains(ball));
        assertTrue(Game.getInstance().getDestroyList().contains(e3));	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		Player e2 = new Player(X_START, Y_START, 0, 0, false);
		bill.collide(e2);
        assertFalse(Game.getInstance().getDestroyList().contains(bill));
        assertFalse(Game.getInstance().getDestroyList().contains(e2));
        }

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		bill.onDeath();
        assertFalse(Game.getInstance().getDestroyList().contains(bill));
        }

}
