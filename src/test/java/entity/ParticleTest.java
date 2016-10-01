package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class ParticleTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Particle particle;


	@Before
	public void setUp() throws Exception {
		particle = new Particle(X_START, Y_START, DX_START, DY_START);
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
	}
	
	@Test
	public void testConstructor() {
		assertNotSame(particle, null);
		assertEquals(particle.getX(), X_START, 0);
		assertEquals(particle.getY(), Y_START, 0);
	}
	
	@Test
	public void testConstructor2() {
		assertEquals(particle.getDX(), DX_START, 0);
		assertEquals(particle.getDY(), DY_START, 0);
	}

	@Test
	public void testUpdate1() {
		final List<String> input = new ArrayList<String>(0);
		particle.update(input);
		assertEquals(particle.getX(), X_START + DX_START, 0);
		assertEquals(particle.getY(), Y_START + DY_START, 0);
	}
	
	@Test
	public void testUpdate2() {
		particle.setBirthTime(0);
		particle.update(null);
		assertEquals(particle.getX(), X_START + DX_START, 0);
		assertEquals(particle.getY(), Y_START + DY_START, 0);
	}

	@Test
	public void testCollide() {
		final Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START);
		particle.collide(e2);
        assertFalse(Game.getInstance().getDestroyList().contains(particle));
        assertFalse(Game.getInstance().getDestroyList().contains(e2));
	}

	@Test
	public void testOnDeath() {
		particle.onDeath();
        assertFalse(Game.getInstance().getDestroyList().contains(particle));
	}
	
	@Test
	public void testDraw() {
		particle.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}

	@Test
	public void testExplosion() {
		Particle.explosion(X_START, Y_START, Game.getInstance());
		final ArrayList<AbstractEntity> creation = (ArrayList<AbstractEntity>) Game.getInstance().getCreateList();
		int containsparticles = 0;
		for(final AbstractEntity x : creation){
			if(x instanceof Particle){
				containsparticles++;
			}
		}
		assertEquals(Particle.getExplosionParticles(), containsparticles);
	}

}
