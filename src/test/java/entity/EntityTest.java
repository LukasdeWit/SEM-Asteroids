package entity;

import game.Game;
import game.Launcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class EntityTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private Game thisGame;
	private AbstractEntity e;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		e = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		thisGame.getAudio().setMute(true);
	}
	
	@Test
	public final void testWrapAround1(){
		e.setY(Game.getCanvasSize() + 10);
		e.wrapAround();
		assertEquals(10, e.getY(), 0);
	}
	
	@Test
	public final void testWrapAround2(){
		e.setY(-10);
		e.wrapAround();
		assertEquals(Game.getCanvasSize() - 10, e.getY(), 0);
	}
	
	@Test
	public final void testDistance(){
		e.setX(3);
		e.setY(4);
		final Asteroid e2 = new Asteroid(0, 0, 0, 0, thisGame);
		assertEquals(5, AbstractEntity.distance(e, e2), 0);
	}
	
	@Test
	public final void testCollision1(){
		final Asteroid e2 = new Asteroid(0, 0, 0, 0, thisGame);
		assertTrue(AbstractEntity.collision(e, e2));
	}
	
	@Test
	public final void testCollision2(){
		final Asteroid e2 = new Asteroid(50, 0, 0, 0, thisGame);
		assertFalse(AbstractEntity.collision(e, e2));
	}
}
