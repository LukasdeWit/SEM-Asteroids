package entity;

import game.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParticleTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Game thisGame;
	private Particle particle;


	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		particle = new Particle(X_START, Y_START, DX_START, DY_START, thisGame);
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
	}
	
	@Test
	public void testConstructor() {
		assertFalse(particle == null);
		assertTrue(particle.getX() == X_START);
		assertTrue(particle.getY() == Y_START);
		assertTrue(particle.getDX() == DX_START);
		assertTrue(particle.getDY() == DY_START);
	}

	@Test
	public void testUpdate() {
		List<String> input = new ArrayList<String>(0);
		particle.update(input);
		assertTrue(particle.getX() == X_START + DX_START);
		assertTrue(particle.getY() == Y_START + DY_START);
	}
	@Test
	public void testUpdate2() {
		List<String> input = new ArrayList<String>(0);
		particle.update(input);
		assertTrue(particle.getX() == X_START + DX_START);
		assertTrue(particle.getY() == Y_START + DY_START);
	}

	@Test
	public void testCollide() {
        Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START, thisGame);
		particle.collide(e2);
        assertFalse(thisGame.getDestroyList().contains(particle));
        assertFalse(thisGame.getDestroyList().contains(e2));
	}

	@Test
	public void testOnDeath() {
		particle.onDeath();
        assertFalse(thisGame.getDestroyList().contains(particle));
	}
	
	
	//@Test
	//public void testDraw() {
	//	particle.draw();
	//	final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
	//	final int strakesInShape = DisplayEntity.particle(particle);
	//	assertEquals(strokesInGroup, strakesInShape);
	//}

	@Test
	public void testExplosion() {
		Particle.explosion(X_START, Y_START, thisGame);
		ArrayList<AbstractEntity> creation = (ArrayList<AbstractEntity>) thisGame.getCreateList();
		boolean containsparticles = false;
		for(AbstractEntity x : creation){
			if(x instanceof Particle){
				containsparticles = true;
			}
		}
		assertTrue(containsparticles);
	}

}
