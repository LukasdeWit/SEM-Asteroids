package game.states;

import game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * abstract class for game states.
 * @author Esmee
 *
 */
public abstract class AbstractState {
	@Getter
	@Setter
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
	 * set the switch time to the current time.
	 */
	public final void switchScreen() {
		thisGame.getGamestate().setScreenSwitchTime(System.currentTimeMillis());
	}
}
