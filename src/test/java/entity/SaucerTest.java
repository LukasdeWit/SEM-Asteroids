package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Launcher;

/**
 * Tests for Saucer.
 * @author Kibo
 *
 */
public class SaucerTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private Saucer saucer;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		Launcher.getRoot().getChildren().clear();
		saucer = new Saucer(X_START, Y_START, DX_START, DY_START);
	}
	
	@Test
	public final void testConstructor1(){
		assertNotSame(saucer, null);
		assertEquals(X_START, saucer.getX(), 0);
		assertEquals(Y_START, saucer.getY(), 0);
	}

	@Test
	public final void testConstructor2(){
		final Saucer saucer2 = new Saucer(Game.getCanvasSize() - 1, Game.getCanvasSize() - 1, 0, 0);
		assertEquals(Game.getCanvasSize() - 1, saucer2.getX(), 0);
		assertEquals(1, saucer2.getToRight());
	}
	
	@Test
	public final void testSetPath(){
		saucer.setPath(1);
		assertEquals(0, saucer.getDY(), 0);
	}
	
	@Test
	public final void testUpdate(){
		saucer.setPath(1);
		saucer.update(null);
		assertEquals(X_START + 2, saucer.getX(), 0);
	}
	
	@Test
	public final void testShoot(){
		//LATER.
	}
}
