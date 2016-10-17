package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class GamestateTest {

	private final Game thisGame = new Game();
	private final Gamestate gamestate = thisGame.getGamestate();
	private final List<String> input = new ArrayList<>();

	@Before
	public final void setUp() {
		gamestate.setPauseTime(0);
		gamestate.setRestartTime(0);
		gamestate.setMode(Gamestate.getModeArcade());
		gamestate.setState(Gamestate.getStateStartScreen());
		thisGame.getScoreCounter().setScore(0);
		thisGame.getScoreCounter().setHighscore(0);
		thisGame.setEntities(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setPlayer(null);
		thisGame.setPlayerTwo(null);
	}
	
	@Test
	public final void testUpdate1() {
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate2() {
		gamestate.setState(Gamestate.getStateGame());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate3() {
		gamestate.setState(Gamestate.getStateHighscoreScreen());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate4() {
		gamestate.setState(Gamestate.getStatePauseScreen());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testStartScreen1() {
		input.add("X");
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateGame(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testStartScreen2() {
		input.add("C");
		gamestate.update(input);
		assertEquals(Gamestate.getModeSurvivalCoop(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateGame(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testGame1() {
		gamestate.setState(Gamestate.getStateGame());
		input.add("R");
		gamestate.update(input);
		assertEquals(Gamestate.getModeNone(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateStartScreen(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testGame2() {
		gamestate.setState(Gamestate.getStateGame());
		input.add("R");
		input.add("P");
		gamestate.setRestartTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStatePauseScreen(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testGame3() {
		gamestate.setState(Gamestate.getStateGame());
		input.add("P");
		gamestate.setPauseTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateGame(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testHighscoreScreen() {
		gamestate.setState(Gamestate.getStateHighscoreScreen());
		input.add("R");
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateHighscoreScreen(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testPauseScreen1() {
		gamestate.setState(Gamestate.getStatePauseScreen());
		input.add("P");
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateGame(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testPauseScreen2() {
		gamestate.setState(Gamestate.getStatePauseScreen());
		input.add("P");
		input.add("R");
		gamestate.setPauseTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStateGame(), gamestate.getState(), 0);
	}
	
	@Test
	public final void testPauseScreen3() {
		gamestate.setState(Gamestate.getStatePauseScreen());
		input.add("R");
		gamestate.setRestartTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.getModeArcade(), gamestate.getMode(), 0);
		assertEquals(Gamestate.getStatePauseScreen(), gamestate.getState(), 0);
	}
}
