package game.states;

import display.DisplayText;
import game.Game;
import game.Gamestate;

import java.util.List;

/**
 * Start screen state.
 * @author Esmee
 *
 */
public class StartScreenState extends AbstractState {
	private Gamestate gamestate;
	/**
	 * Constructor for start screen state.
	 * @param game this state belongs to
	 */
	public StartScreenState(final Game game) {
		super(game);
		gamestate = game.getGamestate();
	}

	@Override
	public final void update(final List<String> input) {
		gamestate = getThisGame().getGamestate();
		if (gamestate.isSwitchTime()) {
			gamestate.startScreen(input);
		}
		DisplayText.startScreen();		
	}
}
