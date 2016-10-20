package entity;

import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;

import entity.builders.AsteroidBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParticleTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Game thisGame;
	private Particle particle;
	private AsteroidBuilder aBuilder;


	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		particle = new Particle(X_START, Y_START, DX_START, DY_START, thisGame);
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		
		aBuilder = new AsteroidBuilder();
		aBuilder.setX(X_START);
		aBuilder.setY(Y_START);
		aBuilder.setDX(DX_START);
		aBuilder.setDY(DY_START);
		aBuilder.setThisGame(thisGame);
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
		final List<String> input = new ArrayList<>(0);
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
		final Asteroid e2 = (Asteroid) aBuilder.getResult();
		particle.collide(e2);
        assertFalse(thisGame.getDestroyList().contains(particle));
        assertFalse(thisGame.getDestroyList().contains(e2));
	}

	@Test
	public void testOnDeath() {
		particle.onDeath();
        assertFalse(thisGame.getDestroyList().contains(particle));
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
		Particle.explosion(X_START, Y_START, thisGame);
		final List<AbstractEntity> creation = thisGame.getCreateList();
		final int containsParticles = (int) creation.stream().filter(e -> e instanceof Particle).count();
		assertEquals(Particle.getExplosionParticles(), containsParticles);
	}
}
