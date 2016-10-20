package game.gamestates;

import java.util.List;

import game.Game;
import game.Gamestate;
import game.Logger;

/**
 * Class that represents the state of an ongoing game.
 * @author Esmee
 *
 */
public class OngoingGameState extends AbstractState {
	/**
	 * Constructor for ongoing game state.
	 * @param game this state belongs to
	 */
	public OngoingGameState(final Game game) {
		super(game);
	}
	
	@Override
	public final void update(final List<String> input) {
		getThisGame().updateGame(input);
		game(input);		
	}
	
	/**
	 * update the game gamemode.
	 * @param input - the input
	 */
	private void game(final List<String> input) {
		Gamestate gameState = getThisGame().getGamestate();

		if (input.contains("R") && System.currentTimeMillis() 
				- getRestartTime() > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			gameState.setMode(gameState.getNoneMode());
			gameState.setState(gameState.getStartScreenState());
		} else if (input.contains("P") && System.currentTimeMillis() 
				- getPauseTime() > MINIMAL_PAUSE_TIME) {
			setPauseTime(System.currentTimeMillis());
			Logger.getInstance().log("Game paused.");
			gameState.setState(gameState.getPauseScreenState());
		}
	}

}
