package game.gamestates;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;
import game.Logger;

/**
 * Highscore screen state.
 * @author Esmee
 *
 */
public class HighscoreScreenState extends AbstractState {
	/**
	 * Constructor for highscore screen state.
	 * @param game this screen belongs to
	 */
	public HighscoreScreenState(final Game game) {
		super(game);
	}

	@Override
	public final void update(final List<String> input) {
		highscoreScreen(input);
		DisplayText.highscoreScreen(getThisGame().getScoreCounter().getHighscore());		
	}
	
	/**
	 * update the highscore gamemode.
	 * @param input - the input
	 */
	private void highscoreScreen(final List<String> input) {
		final Gamestate gameState = getThisGame().getGamestate();

		if (input.contains("R")) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().startGame();
			gameState.setMode(gameState.getNoneMode());
			gameState.setState(gameState.getStartScreenState());
		}
	}

}
