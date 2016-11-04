package game;


import game.highscore.HighscoreStore;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreCounterTest {
	private final Game thisGame = new Game();
	private final Gamestate gamestate = thisGame.getGamestate();
	private final ScoreCounter sc = thisGame.getScorecounter();

	@Before
	public final void setUp() {
		sc.clearHighscores();
		gamestate.setCurrentMode(Gamestate.ARCADEMODE);
		sc.setScore(0);
		sc.setHighscore("", 0);
		thisGame.getAudio().setMute(true);
	}

	@Test
	public void testStartGame() {
		sc.setScore(100);
		sc.startGame("");
		assertEquals(sc.getScore(), 0);
		assertEquals(sc.getHighscore(), 100);
	}
	
	@Test
	public void testStartGame2() {
		sc.setScore(50);
		sc.setHighscore("", 100);
		sc.startGame("");
		assertEquals(sc.getScore(), 0);
		assertEquals(sc.getHighscore(), 100);
	}
	
	@Test
	public void testIsHighscore() {
		sc.setScore(50);
		sc.setHighscore("", 100);
		assertFalse(sc.isHighscore());
		assertTrue(sc.isNotHighscore());
	}
	
	@Test
	public void testIsHighscore2() {
		sc.setScore(100);
		sc.setHighscore("", 50);
		
		assertTrue(sc.isHighscore());
		assertFalse(sc.isNotHighscore());
	}
	
	@Test
	public void testUpdateHighscore() {
		sc.setScore(100);
		sc.setHighscore("", 50);
		sc.updateHighscore("");
		assertEquals(100, sc.getHighscore());
	}
	
	@Test
	public void testUpdateHighscore2() {
		sc.setScore(50);
		sc.setHighscore("", 55);
		sc.updateHighscore("");
		assertNotSame(50, sc.getHighscore());
		assertEquals(55, sc.getHighscore());
	}
	
	@Test
	public void testCanGainLife() {
		sc.setScore(9900);
		
		assertTrue(sc.canGainLife(100));
		assertFalse(sc.canGainLife(99));
	}
	
	@Test
	public void testAddScore() {
		sc.addScore(100);
		assertEquals(100, sc.getScore());
	}
	
	@Test
	public void testConstructor() {
		final ScoreCounter score = new ScoreCounter(thisGame, new HighscoreStore());
		assertEquals(score.getThisGame(), thisGame);
	}
	
	@Test
	public void testConstructor2() {
		// write a new highscore to file
		sc.setScore(1234);
		sc.updateHighscore("");
		
		// check if the highscore is read by new ScoreCounter object
		final ScoreCounter counter = new ScoreCounter(thisGame, new HighscoreStore());
		assertEquals(1234, counter.getHighscore());
	}

}
