package game;

import org.junit.Before;
import org.junit.Test;

import display.DisplayText;

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
		gamestate.setCurrentMode(Gamestate.ARCADEMODE);
		gamestate.setState(gamestate.getStartScreenState());
		thisGame.getScorecounter().setScore(0);
		thisGame.setEntities(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setPlayer(null);
		thisGame.setPlayerTwo(null);
		DisplayText.setTest(true);
		gamestate.setScreenSwitchTime(0);
		thisGame.getAudio().setMute(true);
	}
	
	@Test
	public final void testUpdate1() {
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate2() {
		gamestate.setState(gamestate.getOngoingGameState());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate3() {
		gamestate.setState(gamestate.getHighscoreState());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdate4() {
		gamestate.setState(gamestate.getPauseScreenState());
		gamestate.update(input);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testStartScreen1() {
		input.add("A");
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testStartScreen2() {
		input.add("C");
		gamestate.update(input);
		assertEquals(Gamestate.COOPBOSSMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testStartScreen3() {
		input.add("Z");
		gamestate.update(input);
		assertEquals(Gamestate.COOPARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testStartScreen4() {
		input.add("D");
		gamestate.update(input);
		assertEquals(Gamestate.BOSSMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testStartScreen5() {
		input.add("H");
		gamestate.update(input);
		assertEquals(Gamestate.NONEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getViewHighscoresState(), gamestate.getState());
	}
	
	@Test
	public final void testStartScreen6() {
		input.add("ESCAPE");
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getStartScreenState(), gamestate.getState());
	}
	
	@Test
	public final void testCheckModeInput1(){
		input.add("S");
		gamestate.update(input);
		assertEquals(Gamestate.SURVIVALMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testCheckModeInput2(){
		input.add("X");
		gamestate.update(input);
		assertEquals(Gamestate.COOPSURVIVALMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testIntToString(){
		final String actual = gamestate.intToString(0);
		assertEquals("none", actual);
	}
	
	@Test
	public final void testGame1() {
		gamestate.setState(gamestate.getOngoingGameState());
		input.add("R");
		gamestate.setScreenSwitchTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testGame2() {
		gamestate.setState(gamestate.getOngoingGameState());
		input.add("R");
		input.add("P");
		gamestate.setScreenSwitchTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testGame3() {
		gamestate.setState(gamestate.getOngoingGameState());
		input.add("P");
		gamestate.setScreenSwitchTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testHighscoreScreen() {
		gamestate.setState(gamestate.getHighscoreState());
		input.add("R");
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getHighscoreState(), gamestate.getState());
	}
	
	@Test
	public final void testPauseScreen1() {
		gamestate.setState(gamestate.getPauseScreenState());
		gamestate.setScreenSwitchTime(0);
		input.add("P");
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getOngoingGameState(), gamestate.getState());
	}
	
	@Test
	public final void testPauseScreen2() {
		gamestate.setState(gamestate.getPauseScreenState());
		input.add("P");
		input.add("R");
		gamestate.setScreenSwitchTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getPauseScreenState(), gamestate.getState());
	}
	
	@Test
	public final void testPauseScreen3() {
		gamestate.setState(gamestate.getPauseScreenState());
		input.add("R");
		gamestate.setScreenSwitchTime(System.currentTimeMillis());
		gamestate.update(input);
		assertEquals(Gamestate.ARCADEMODE, gamestate.getCurrentMode());
		assertEquals(gamestate.getPauseScreenState(), gamestate.getState());
	}
}
