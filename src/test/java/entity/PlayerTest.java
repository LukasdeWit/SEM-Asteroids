package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Gamestate;

public class PlayerTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Player player;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		player = new Player(X_START, Y_START, DX_START, DY_START, false);
	}
	
	@Test
	public void testConstructor1() {
		assertNotSame(player, null);
		assertEquals(X_START, player.getX(), 0);
		assertEquals(Y_START, player.getY(), 0);
	}
	
	@Test
	public void testConstructor2() {
		assertEquals(DX_START, player.getDX(), 0);
		assertEquals(DY_START, player.getDY(), 0);
		assertFalse(player.isPlayerTwo());
	}

	@Test
	public void testOnDeath() {
		player.onDeath();
		assertTrue(Game.getInstance().getDestroyList().isEmpty());
	}

	@Test
	public void testOnHit1() {
		player.onHit();
		assertEquals(2, player.getLives(), 0);
	}

	@Test
	public void testOnHit2() {
		player.setShielding(1);
		player.onHit();
		assertEquals(3, player.getLives(), 0);
		assertEquals(0, player.getShielding(), 0);
	}
	
	@Test
	public void testOnHit3() {
		player.setLives(1);
		player.onHit();
		assertEquals(0, player.getLives(), 0);
	}

	@Test
	public void testOnHit4() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		player2.onHit();
		assertEquals(Game.getInstance().getScreenX() / 2 + Player.getSpawnOffset(), player2.getX(), 0);
	}

	@Test
	public void testOnHit5() {
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		player.onHit();
		assertEquals(Game.getInstance().getScreenX() / 2 - Player.getSpawnOffset(), player.getX(), 0);
	}
	
	@Test
	public void testGainLife1() {
		player.gainLife();
		assertEquals(4,player.getLives(),0);
	}
	
	@Test
	public void testGainLife2() {
		player.setLives(0);
		player.gainLife();
		assertEquals(1,player.getLives(),0);
		assertEquals(Game.getInstance().getScreenX() / 2, player.getX(), 0);
	}
}
