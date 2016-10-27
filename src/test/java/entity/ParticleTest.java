package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.builders.AsteroidBuilder;
import entity.builders.ParticleBuilder;
import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class ParticleTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Game thisGame;
	private AsteroidBuilder aBuilder;
	private ParticleBuilder pBuilder;

	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		
		aBuilder = new AsteroidBuilder();
		aBuilder.setX(X_START);
		aBuilder.setY(Y_START);
		aBuilder.setDX(DX_START);
		aBuilder.setDY(DY_START);
		aBuilder.setThisGame(thisGame);
		
		pBuilder = new ParticleBuilder();
		pBuilder.setX(X_START);
		pBuilder.setY(Y_START);
		pBuilder.setDX(DX_START);
		pBuilder.setDY(DY_START);
		pBuilder.setThisGame(thisGame);
	}

	@Test
	public void testUpdate1() {
		final Particle particle = (Particle) pBuilder.getResult();
		final List<String> input = new ArrayList<>(0);
		particle.update(input);
		assertEquals(particle.getX(), X_START + DX_START, 0);
		assertEquals(particle.getY(), Y_START + DY_START, 0);
	}
	
	@Test
	public void testUpdate2() {
		final Particle particle = (Particle) pBuilder.getResult();
		particle.setBirthTime(0);
		particle.update(null);
		assertEquals(particle.getX(), X_START + DX_START, 0);
		assertEquals(particle.getY(), Y_START + DY_START, 0);
	}

	@Test
	public void testCollide() {
		final Particle particle = (Particle) pBuilder.getResult();
		final Asteroid e2 = (Asteroid) aBuilder.getResult();
		particle.collide(e2);
        assertFalse(thisGame.getDestroyList().contains(particle));
        assertFalse(thisGame.getDestroyList().contains(e2));
	}

	@Test
	public void testOnDeath() {
		final Particle particle = (Particle) pBuilder.getResult();
		particle.onDeath();
        assertFalse(thisGame.getDestroyList().contains(particle));
	}
	
	@Test
	public void testDraw() {
		final Particle particle = (Particle) pBuilder.getResult();
		particle.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}

	@Test
	public void testExplosion() {
		Particle.explosion(X_START, Y_START, thisGame);
		final List<AbstractEntity> creation = thisGame.getCreateList();
		final int containsParticles = (int) creation.stream().filter(e -> e instanceof Particle).count();
		assertEquals(Particle.getExplosionParticles(), containsParticles);
	}
}
