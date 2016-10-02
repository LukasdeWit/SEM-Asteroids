package game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.Saucer;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class SpawnerTest {

	private final Game game = Game.getInstance();
	private final Spawner spawner = Spawner.getInstance();

	@Before
	public final void setUp() {
		game.setCreateList(new ArrayList<>());
		spawner.setWave(0);
		spawner.setStartPowerupTime(0);
		spawner.setStartSaucerTime(0);
		spawner.setStartRest(0);
	}
	
	@Test
	public final void testUpdate1() {
		spawner.update();
		assertEquals(6, game.getCreateList().size(), 0);
	}
	
	@Test
	public final void testUpdate2() {
		spawner.setStartRest(1);
		spawner.setWave(8);
		spawner.update();
		assertEquals(13, game.getCreateList().size(), 0);
	}
	
	@Test
	public final void testUpdate3() {
		spawner.setStartRest(1);
		spawner.setWave(2);
		spawner.update();
		assertEquals(10, game.getCreateList().size(), 0);
	}
	
	@Test
	public final void testSpawnSaucer(){
		spawner.setStartPowerupTime(System.currentTimeMillis());
		spawner.setStartRest(System.currentTimeMillis());
		game.setScore(1000000);
		spawner.update();
		assertEquals(Saucer.getSmallRadius(), ((Saucer) game.getCreateList().get(0)).getRadius(), 0);
	}
	
	@Test
	public final void testSmallSaucerRatio(){
		spawner.setStartPowerupTime(System.currentTimeMillis());
		spawner.setStartRest(System.currentTimeMillis());
		game.setScore(50000);
		spawner.update();
		assertEquals(Saucer.getSmallRadius(), ((Saucer) game.getCreateList().get(0)).getRadius(), 5);
	}
	
	@Test
	public final void testReset(){
		spawner.setWave(12);
		spawner.reset();
		Spawner.getDifficultyStep();
		assertEquals(0, spawner.getWave(), 0);
	}
}
