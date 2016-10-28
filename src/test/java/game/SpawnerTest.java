package game;

import entity.Saucer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class SpawnerTest {

	private final Game thisGame = new Game();
	private final Spawner spawner = thisGame.getSpawner();

	@Before
	public final void setUp() {
		thisGame.setCreateList(new ArrayList<>());
		spawner.setWave(0);
		spawner.setStartPowerupTime(0);
		spawner.setStartSaucerTime(0);
		spawner.setStartRest(0);
		thisGame.getAudio().setMute(true);
	}
	
	@Test
	public final void testUpdate1() {
		spawner.updateArcade();
		assertEquals(6, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testUpdate2() {
		spawner.setStartRest(1);
		spawner.setWave(8);
		spawner.updateArcade();
		assertEquals(13, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testUpdate3() {
		spawner.setStartRest(1);
		spawner.setWave(2);
		spawner.updateArcade();
		assertEquals(10, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testSpawnSaucer(){
		spawner.setStartPowerupTime(System.currentTimeMillis());
		spawner.setStartRest(System.currentTimeMillis());
		thisGame.getScoreCounter().setScore(1000000);
		spawner.updateArcade();
		assertEquals(Saucer.getSmallRadius(), thisGame.getCreateList().get(0).getRadius(), 0);
	}
	
	@Test
	public final void testSmallSaucerRatio(){
		spawner.setStartPowerupTime(System.currentTimeMillis());
		spawner.setStartRest(System.currentTimeMillis());
		thisGame.getScoreCounter().setScore(50000);
		spawner.updateArcade();
		assertEquals(Saucer.getSmallRadius(), thisGame.getCreateList().get(0).getRadius(), 5);
	}
	
	@Test
	public final void testReset(){
		spawner.setWave(12);
		spawner.reset();
		assertEquals(0, spawner.getWave(), 0);
	}
}
