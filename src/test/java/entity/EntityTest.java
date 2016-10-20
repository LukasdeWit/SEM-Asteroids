package entity;

import game.Game;
import game.Launcher;
import org.junit.Before;
import org.junit.Test;

import entity.builders.AsteroidBuilder;

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
	private AsteroidBuilder aBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		
		aBuilder = new AsteroidBuilder();
		aBuilder.setX(X_START);
		aBuilder.setY(Y_START);
		aBuilder.setDX(DX_START);
		aBuilder.setDY(DY_START);
		aBuilder.setThisGame(thisGame);
	}
	
	@Test
	public final void testWrapAround1(){
		final AbstractEntity e = aBuilder.getResult();
		e.setY(Game.getCanvasSize() + 10);
		e.wrapAround();
		assertEquals(10, e.getY(), 0);
	}
	
	@Test
	public final void testWrapAround2(){
		final AbstractEntity e = aBuilder.getResult();
		e.setY(-10);
		e.wrapAround();
		assertEquals(Game.getCanvasSize() - 10, e.getY(), 0);
	}
	
	@Test
	public final void testDistance(){
		final AbstractEntity e = aBuilder.getResult();
		e.setX(3);
		e.setY(4);
		aBuilder.setX(0);
		aBuilder.setY(0);
		aBuilder.setDX(0);
		aBuilder.setDY(0);
		final Asteroid e2 = (Asteroid) aBuilder.getResult();
		assertEquals(5, AbstractEntity.distance(e, e2), 0);
	}
	
	@Test
	public final void testCollision1(){
		final AbstractEntity e = aBuilder.getResult();
		aBuilder.setX(0);
		aBuilder.setY(0);
		aBuilder.setDX(0);
		aBuilder.setDY(0);
		final Asteroid e2 = (Asteroid) aBuilder.getResult();
		assertTrue(AbstractEntity.collision(e, e2));
	}
	
	@Test
	public final void testCollision2(){
		final AbstractEntity e = aBuilder.getResult();
		aBuilder.setX(50);
		aBuilder.setY(0);
		aBuilder.setDX(0);
		aBuilder.setDY(0);
		final Asteroid e2 = (Asteroid) aBuilder.getResult();
		assertFalse(AbstractEntity.collision(e, e2));
	}
}
