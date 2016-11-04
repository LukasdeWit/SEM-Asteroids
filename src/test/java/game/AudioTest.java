package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import display.DisplayText;

/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class AudioTest {

	private final Game thisGame = new Game();
	private final Gamestate gamestate = thisGame.getGamestate();
	private final Audio audio = thisGame.getAudio();
	
	@Before
	public final void setUp() {
		gamestate.setCurrentMode(gamestate.getArcadeMode());
		Launcher.getRoot().getChildren().clear();
				
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
		final List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(true);
		audio.setReleased(true);

		audio.update(input);
		assertEquals(audio.isMute(), false);
	}
	
	@Test
	public final void testMute4(){
		final List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(false);
		audio.setReleased(true);

		audio.update(input);
		assertEquals(audio.isMute(), true);
	}
	
	@Test
	public final void testMute5(){
		final List<String> input = new ArrayList<String>();
		input.add("M");
		audio.setMute(false);
		audio.setReleased(false);

		audio.update(input);
		assertFalse(audio.isMute());
	}
	
	@Test
	public final void testMute6(){
		final List<String> input = new ArrayList<String>();
		audio.setMute(false);
		audio.setReleased(false);

		audio.update(input);
		assertFalse(audio.isMute());
		assertTrue(audio.isReleased());
	}
}
