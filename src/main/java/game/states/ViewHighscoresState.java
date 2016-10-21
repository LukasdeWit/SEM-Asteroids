package game.states;

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
public class ViewHighscoresState extends AbstractState {
	/**
	 * Constructor for highscore screen state.
	 * @param game this screen belongs to
	 */
	public ViewHighscoresState(final Game game) {
		super(game);
	}

	@Override
	public final void update(final List<String> input) {
		viewHighscoresScreen(input);
		DisplayText.viewHighscoresScreen(getThisGame().getScoreCounter().highScoresToStrings());
	}
	
	/**
	 * update the highscorescreen state. 
	 * @param input - the input
	 */
	private void viewHighscoresScreen(final List<String> input) {
		final Gamestate gamestate = getThisGame().getGamestate();
		if (input.contains("R") && gamestate.isSwitchTime()) {
			Logger.getInstance().log("Return to start screen.");
			gamestate.setState(gamestate.getStartScreenState());
		} else if (input.contains("D") && gamestate.isSwitchTime()) {
			getThisGame().getScoreCounter().clearHighscores();
		}
	}
}