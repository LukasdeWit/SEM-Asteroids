package game.states;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;
import game.Logger;

/**
 * Pause screen state.
 * @author Esmee
 *
 */
public class PauseScreenState extends AbstractState {
	/**
	 * Constructor for pause screen state.
	 * @param game this state belongs to
	 */
	public PauseScreenState(final Game game) {
		super(game);
	}

	@Override
	public final void update(final List<String> input) {
		DisplayText.pauseScreen();
		pauseScreen(input);		
	}
	
	/**
	 * update the pause screen gamemode.
	 * @param input - the input
	 */
	private void pauseScreen(final List<String> input) {
		final Gamestate gameState = getThisGame().getGamestate();

		if (input.contains("P") && System.currentTimeMillis() 
				- getPauseTime() > MINIMAL_PAUSE_TIME) {
			setPauseTime(System.currentTimeMillis());
			Logger.getInstance().log("Game unpaused.");
			gameState.setState(gameState.getOngoingGameState());
		} else if (input.contains("R") && System.currentTimeMillis() 
				- getRestartTime() > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().startGame();
			gameState.setState(gameState.getOngoingGameState());
		}
	}
}
