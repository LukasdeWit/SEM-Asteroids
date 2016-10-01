package game;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.Asteroid;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class GameTest {
	
	private final Game game = Game.getInstance();
	private final List<String> noInput = new ArrayList<String>();

	@Before
	public final void setUp() {
		Gamestate.getInstance().setMode(Gamestate.getModeArcade());
		game.setScore(0);
		game.setHighscore(0);
	}
	
	@Test
	public final void testStartGame1(){
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.setScore(10);
		game.startGame();
		//test
	}
	
	@Test
	public final void testStartGame2(){
		game.setHighscore(10);
		game.startGame();
		//test
	}
	
	@Test
	public final void testUpdate(){
		game.update(noInput);
		//test
	}
	
	@Test
	public final void testUpdateGame(){
		final Asteroid a = new Asteroid(0, 0, 0, 0);
		game.getCreateList().add(a);
		//game.updateGame(noInput);
		//test
	}

}
