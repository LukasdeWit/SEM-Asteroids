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

import display.DisplayEntity;
import game.Game;
import game.Launcher;
import javafx.scene.Group;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AsteroidTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private Asteroid ceres;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		ceres = new Asteroid(X_START, Y_START, DX_START, DY_START);
	}

	@Test
	public final void testConstructor() {
		//Assert that the Asteroid was constructed.
		assertNotSame(ceres, null);
		//Assert that the Asteroid's properties are correct.
		assertEquals(ceres.getX(), X_START, 0);
		assertEquals(ceres.getY(), Y_START, 0);
	}

	@Test
	public final void testConstructor2() {
		//Assert that the Asteroid's properties are correct.
		assertEquals(ceres.getDX(), DX_START, 0);
		assertEquals(ceres.getDY(), DY_START, 0);
	}

	@Test
	public final void testConstructor3() {
		ceres = new Asteroid(X_START, Y_START, .2f, .4f);
		//Low speed should be doubled, until fast enough.
		assertEquals(ceres.getDX(), .4f, 0);
		assertEquals(ceres.getDY(), .8f, 0);
	}

	@Test
	public final void testSecondConstructor() {
		ceres = new Asteroid(X_START, Y_START, DX_START, DY_START, 23);
		//size is also set in this constructor
		assertEquals(ceres.getRadius(), 23, 0);
	}

	@Test
	public final void testUpdate() {
		final List<String> input = new ArrayList<String>(0);
		ceres.update(input);
		assertEquals(ceres.getX(), X_START + DX_START, 0);
		assertEquals(ceres.getY(), Y_START + DY_START, 0);
	}
	
	@Test
	public final void testCollide() {
		final AbstractEntity e2 = new Asteroid(X_START, Y_START, DX_START + 1, DY_START + 1);
		ceres.collide(e2);
		assertFalse(Game.getInstance().getDestroyList().contains(ceres));
	}
	
	@Test
	public final void testCollide2() {
		final AbstractEntity e2 = new Bullet(X_START, Y_START, DX_START + 1, DY_START + 1);
		ceres.collide(e2);
		assertTrue(Game.getInstance().getDestroyList().contains(ceres));
		assertTrue(Game.getInstance().getDestroyList().contains(e2));
	}
	
	@Test
	public final void testOnDeath() {
		ceres.onDeath();
		final Asteroid a = (Asteroid)(Game.getInstance().getCreateList().get(0));
		assertEquals(a.getRadius(), Asteroid.getMediumRadius(), 0);
	}
	
	@Test
	public final void testOnDeath2() {
		ceres.setRadius(Asteroid.getMediumRadius());
		ceres.onDeath();
		final Asteroid a = (Asteroid)(Game.getInstance().getCreateList().get(0));
		assertEquals(a.getRadius(), Asteroid.getSmallRadius(), 0);
	}
	
	@Test
	public final void testOnDeath3() {
		ceres.setRadius(Asteroid.getSmallRadius());
		ceres.onDeath();
		assertEquals(Game.getInstance().getCreateList().size(), Particle.getExplosionParticles());
	}

	@Test
	public final void testDraw() {
		ceres.setShape(0);
		ceres.draw();
		final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
		final int strakesInShape = DisplayEntity.getAsteroidShapes()[0].length;
		assertEquals(strokesInGroup, strakesInShape);
	}
}