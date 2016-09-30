package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import display.DisplayEntity;
import game.Game;
import game.Launcher;
import javafx.scene.Group;

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
        Asteroid e2 = new Asteroid(X_START, Y_START, DX_START, DY_START);
		quintessence.collide(e2);
        assertFalse(Game.getInstance().getDestroyList().contains(quintessence));
        assertFalse(Game.getInstance().getDestroyList().contains(e2));
	}

	@Test
	public void testOnDeath() {
		quintessence.onDeath();
        assertFalse(Game.getInstance().getDestroyList().contains(quintessence));
	}
	
	
	//@Test
	//public void testDraw() {
	//	quintessence.draw();
	//	final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
	//	final int strakesInShape = DisplayEntity.particle(quintessence);
	//	assertEquals(strokesInGroup, strakesInShape);
	//}

	@Test
	public void testExplosion() {
		quintessence.explosion(X_START, Y_START, Game.getInstance());
		ArrayList<AbstractEntity> creation = (ArrayList<AbstractEntity>) Game.getInstance().getCreateList();
		boolean containsparticles = false;
		for(AbstractEntity x : creation){
			if(x instanceof Particle){
				containsparticles = true;
			}
		}
		assertTrue(containsparticles);
	}

}
