package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.Game;

public class ParticleTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Particle quintessence;


	@Before
	public void setUp() throws Exception {
		quintessence = new Particle(X_START, Y_START, DX_START, DY_START);
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
	}
	
	@Test
	public void testConstructor() {
		assertFalse(quintessence == null);
		assertTrue(quintessence.getX() == X_START);
		assertTrue(quintessence.getY() == Y_START);
		assertTrue(quintessence.getDX() == DX_START);
		assertTrue(quintessence.getDY() == DY_START);
	}

	@Test
	public void testUpdate() {
		List<String> input = new ArrayList<String>(0);
		quintessence.update(input);
		assertTrue(quintessence.getX() == X_START + DX_START);
		assertTrue(quintessence.getY() == Y_START + DY_START);
	}
	@Test
	public void testUpdate2() {
		List<String> input = new ArrayList<String>(0);
		quintessence.update(input);
		assertTrue(quintessence.getX() == X_START + DX_START);
		assertTrue(quintessence.getY() == Y_START + DY_START);
	}

	@Test
	public void testCollide() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnDeath() {
		fail("Not yet implemented");
	}

	@Test
	public void testParticle() {
		fail("Not yet implemented");
	}

	@Test
	public void testExplosion() {
		fail("Not yet implemented");
	}

}
