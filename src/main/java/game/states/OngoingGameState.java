package game.states;

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
		final Gamestate gamestate = getThisGame().getGamestate();

		if (input.contains("R") && gamestate.isSwitchTime()) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().overSwitch();
			getThisGame().getGamestate().getHighscoreState().setPressedButton("R");
		} else if (input.contains("P") && gamestate.isSwitchTime()) {
			Logger.getInstance().log("Game paused.");
			gamestate.setState(gamestate.getPauseScreenState());
		}
	}

}
