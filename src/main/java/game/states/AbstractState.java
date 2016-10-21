package game.states;

import java.util.List;

import game.Game;

/**
 * abstract class for game states.
 * @author Esmee
 *
 */
public abstract class AbstractState {
	private Game thisGame;
	
	/**
	 * Constructor for abstract game state.
	 * @param game this state belongs to
	 */
	public AbstractState(final Game game) {
		thisGame = game;
	}
	
	/**
	 * Update state with keyboard input.
	 * @param input to update the state with
	 */
	public abstract void update(List<String> input);
	
	/**
	 * Set current game of state.
	 * @param game this state should belong to
	 */
	public final void setThisGame(final Game game) {
		thisGame = game;
	}
	
	/**
	 * Get current game of state.
	 * @return the game this state belongs to
	 */
	public final Game getThisGame() {
		return thisGame;
	}
	
	/**
	 * set the switch time to the current time.
	 */
	public final void switchScreen() {
		thisGame.getGamestate().setScreenSwitchTime(System.currentTimeMillis());
	}
}
