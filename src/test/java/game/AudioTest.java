package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import display.DisplayText;
import entity.AbstractEntity;
import entity.Asteroid;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import javafx.scene.shape.Rectangle;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class AudioTest {

	private final Game thisGame = new Game();
	private final Gamestate gamestate = thisGame.getGamestate();
	private final Audio audio = thisGame.getAudio();
	
	private PlayerBuilder pBuilder;
	private BulletBuilder bBuilder;

	@Before
	public final void setUp() {
		thisGame.getScorecounter().clearHighscores();
		gamestate.setCurrentMode(gamestate.getArcadeMode());
		thisGame.getScorecounter().setScore(0);
		thisGame.setEntities(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setPlayer(null);
		thisGame.setPlayerTwo(null);
		thisGame.getScorecounter().setHighscore("", 0);
		
		pBuilder = new PlayerBuilder();
		pBuilder.setX(0);
		pBuilder.setY(0);
		pBuilder.setDX(0);
		pBuilder.setDY(0);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		
		bBuilder = new BulletBuilder();
		bBuilder.setX(0);
		bBuilder.setY(1);
		bBuilder.setDX(0);
		bBuilder.setDY(0);
		bBuilder.setThisGame(thisGame);
		
		DisplayText.setTest(true);
		audio.setMute(true);
	}
	
	@Test
	public final void testMute1(){
		thisGame.startGame();
		audio.switchMute();
		assertEquals(audio.isMute(), false);
	}
	
	@Test
	public final void testMute2(){
		thisGame.startGame();
		audio.setMute(false);
		audio.switchMute();
		assertEquals(audio.isMute(), true);
	}
	
	@Test
	public final void testMute3(){
		List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(true);
		audio.setReleased(true);

		audio.update(input);
		assertEquals(audio.isMute(), false);
	}
	
	@Test
	public final void testMute4(){
		List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(false);
		audio.setReleased(true);

		audio.update(input);
		assertEquals(audio.isMute(), true);
	}
	
	@Test
	public final void testMute5(){
		List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(false);
		audio.setReleased(false);

		audio.update(input);
		assertEquals(audio.isMute(), false);
	}
	
	@Test
	public final void testMute6(){
		List<String> input = new ArrayList<String>();
		audio.setMute(false);
		audio.setReleased(false);

		audio.update(input);
		assertEquals(audio.isMute(), false);
		assertTrue(audio.isReleased());
	}
}
