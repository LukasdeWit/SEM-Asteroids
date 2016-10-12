package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Launcher;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class AbstrEntityTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private AbstractEntity e;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		Launcher.getRoot().getChildren().clear();
		e = new Asteroid(X_START, Y_START, DX_START, DY_START);
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
		final Asteroid e2 = new Asteroid(0, 0, 0, 0);
		assertEquals(5, AbstractEntity.distance(e, e2), 0);
	}
	
	@Test
	public final void testCollision1(){
		final Asteroid e2 = new Asteroid(0, 0, 0, 0);
		assertTrue(AbstractEntity.collision(e, e2));
	}
	
	@Test
	public final void testCollision2(){
		final Asteroid e2 = new Asteroid(50, 0, 0, 0);
		assertFalse(AbstractEntity.collision(e, e2));
	}
}
