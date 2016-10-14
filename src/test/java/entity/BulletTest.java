package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

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
	private Bullet bullet;
	/**
	 * Test bullet #2.
	 */
	private Bullet ball;

	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		bullet = new Bullet(X_START, Y_START, DX_START, DY_START, thisGame);
		//Used for testing methods involving the piercing attribute.
		ball = new Bullet(X_START,Y_START, DX_START, DY_START, 3, thisGame);
		thisGame.setCreateList(new ArrayList<>());
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor1() {
		assertNotSame(bullet, null);
		assertEquals(bullet.getX(), X_START, 0);
		assertEquals(bullet.getY(), Y_START, 0);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor2() {
		assertEquals(bullet.getDX(), DX_START, 0);
		assertEquals(bullet.getDY(), DY_START, 0);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate() {
		final List<String> input = new ArrayList<>(0);
		bullet.update(input);
		assertEquals(bullet.getX(), X_START + DX_START, 0);
		assertEquals(bullet.getY(), Y_START + DY_START, 0);
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate2() {
		final List<String> input = new ArrayList<>(0);
		bullet.setBirthTime(0);
		bullet.update(input);
        assertFalse(thisGame.getEntities().contains(bullet));
	}
	
	/**
	 * Test isFriendly.
	 */
	@Test
	public final void testIsFriendly() {
		bullet.setFriendly(true);
		assertTrue(bullet.isFriendly());
	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide1() {
		final Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		bullet.collide(e2);
		assertFalse(thisGame.getEntities().contains(bullet));
		assertFalse(thisGame.getEntities().contains(e2));
     }
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		final Asteroid e3 = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		ball.collide(e3);
		assertTrue(thisGame.getEntities().contains(ball));
		assertFalse(thisGame.getEntities().contains(e3));
     }
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide3() {
		final Player e2 = new Player(X_START, Y_START, 0, 0, thisGame, false);
		bullet.collide(e2);
		assertTrue(thisGame.getEntities().contains(bullet));
        assertTrue(thisGame.getEntities().contains(e2));
        }

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		bullet.onDeath();
        assertTrue(thisGame.getEntities().contains(bullet));
    }
	
	@Test
	public void testDraw() {
		bullet.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}
	
	@Test
	public void testPlayer() {
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, thisGame, false);
		bullet.setPlayer(p);
		assertEquals(DX_START,bullet.getPlayer().getDX(),0);
	}
}
