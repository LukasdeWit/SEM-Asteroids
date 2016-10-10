package entity;

import display.DisplayEntity;
import game.Game;
import game.Launcher;
import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class AsteroidTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private Asteroid asteroid;
	private Game thisGame;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		asteroid = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
	}

	@Test
	public final void testConstructor() {
		//Assert that the Asteroid was constructed.
		assertNotSame(asteroid, null);
		//Assert that the Asteroid's properties are correct.
		assertEquals(asteroid.getX(), X_START, 0);
		assertEquals(asteroid.getY(), Y_START, 0);
	}

	@Test
	public final void testConstructor2() {
		//Assert that the Asteroid's properties are correct.
		assertEquals(asteroid.getDX(), DX_START, 0);
		assertEquals(asteroid.getDY(), DY_START, 0);
	}

	@Test
	public final void testConstructor3() {
		asteroid = new Asteroid(X_START, Y_START, .2f, .4f, thisGame);
		//Low speed should be doubled, until fast enough.
		assertEquals(asteroid.getDX(), .4f, 0);
		assertEquals(asteroid.getDY(), .8f, 0);
	}

	@Test
	public final void testSecondConstructor() {
		asteroid = new Asteroid(X_START, Y_START, DX_START, DY_START, 23, thisGame);
		//size is also set in this constructor
		assertEquals(asteroid.getRadius(), 23, 0);
	}

	@Test
	public final void testUpdate() {
		final List<String> input = new ArrayList<String>(0);
		asteroid.update(input);
		assertEquals(asteroid.getX(), X_START + DX_START, 0);
		assertEquals(asteroid.getY(), Y_START + DY_START, 0);
	}
	
	@Test
	public final void testCollide() {
		final AbstractEntity e2 = new Asteroid(X_START, Y_START, DX_START + 1, DY_START + 1, thisGame);
		asteroid.collide(e2);
		assertFalse(thisGame.getDestroyList().contains(asteroid));
	}
	
	@Test
	public final void testCollide2() {
		final AbstractEntity e2 = new Bullet(X_START, Y_START, DX_START + 1, DY_START + 1, thisGame);
		asteroid.collide(e2);
		assertTrue(thisGame.getDestroyList().contains(asteroid));
		assertTrue(thisGame.getDestroyList().contains(e2));
	}
	
	@Test
	public final void testOnDeath() {
		asteroid.onDeath();
		final Asteroid a = (Asteroid)(thisGame.getCreateList().get(0));
		assertEquals(a.getRadius(), Asteroid.getMediumRadius(), 0);
	}
	
	@Test
	public final void testOnDeath2() {
		asteroid.setRadius(Asteroid.getMediumRadius());
		asteroid.onDeath();
		final Asteroid a = (Asteroid)(thisGame.getCreateList().get(0));
		assertEquals(a.getRadius(), Asteroid.getSmallRadius(), 0);
	}
	
	@Test
	public final void testOnDeath3() {
		asteroid.setRadius(Asteroid.getSmallRadius());
		asteroid.onDeath();
		assertEquals(thisGame.getCreateList().size(), Particle.getExplosionParticles());
	}

	@Test
	public final void testDraw() {
		asteroid.setShape(0);
		asteroid.draw();
		final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
		final int strakesInShape = DisplayEntity.getAsteroidShapes()[0].length;
		assertEquals(strokesInGroup, strakesInShape);
	}
}