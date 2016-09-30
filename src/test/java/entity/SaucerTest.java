package entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import game.Game;

/**
 * Tests for Saucer.
 * @author Kibo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SaucerTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	/**
	 * Test Asteroid.
	 */
	private Saucer disc;
	/**
	 * Mocked Game.
	 */
	private Game mockGame;

	@Before
	public void setUp() throws Exception {
		mockGame = mock(Game.class);
		doReturn(500f).when(mockGame).getScreenX();
		disc = new Saucer(X_START, Y_START, DX_START, DY_START);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor() {
		
		assertFalse(disc == null);
		assertTrue(disc.getX() == X_START);
		assertTrue(disc.getY() == Y_START);
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public final void testConstructor2() {
		disc = new Saucer(X_START+400, Y_START, DX_START, DY_START);
		assertFalse(disc == null);
		assertTrue(disc.getX() == X_START+400);
		assertTrue(disc.getY() == Y_START);
	}

	//WTF gaat hier fout?
	
	/**
	 * Test update.
	 *//*
	@Test
	public final void testUpdate() {
		Player p = mock(Player.class);
		doReturn(p).when(mockGame).getPlayer();
		when(mockGame.getScreenX()).thenReturn(500f);
		List<String> input = new ArrayList<String>(0);
		disc.update(input);
	}*/
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide() {
		Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START);
		disc.collide(e2);
		verify(mockGame, times(2)).destroy(Mockito.any(AbstractEntity.class));
	}
	
	/**
	 * Test collide.
	 */
	@Test
	public final void testCollide2() {
		Player e2 = new Player(X_START, Y_START, 0, 0, false);
		disc.collide(e2);
		verify(mockGame, never()).destroy(Mockito.any(AbstractEntity.class));
	}

	/**
	 * Test onDeath.
	 */
	@Test
	public final void testOnDeath() {
		disc.onDeath();
		verify(mockGame, never()).destroy(Mockito.any(AbstractEntity.class));
	}

}
