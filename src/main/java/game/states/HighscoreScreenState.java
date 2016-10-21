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
public class HighscoreScreenState extends AbstractState {
	private char[] name;
	private int namePos;
	private String pressedButton;
	private static final long FLICKER_TIME = 200;
	private static final int INPUT_LENGTH = 8;
	
	/**
	 * Constructor for highscore screen state.
	 * @param game this screen belongs to
	 */
	public HighscoreScreenState(final Game game) {
		super(game);
		name = new char[INPUT_LENGTH];
		namePos = 0;
	}

	@Override
	public final void update(final List<String> input) {
		highscoreScreen(input);
		DisplayText.highscoreScreen(getThisGame().getScoreCounter().getScore(), nameString());		
	}

	/**
	 * update the highscore state.
	 * @param input - the input
	 */
	private void highscoreScreen(final List<String> input) {
		final Gamestate gamestate = getThisGame().getGamestate();
		if (namePos != 0 && input.contains("ENTER") && getThisGame().getGamestate().isSwitchTime()) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().startGame();
			getThisGame().getScoreCounter().startGame(nameString());
			name = new char[INPUT_LENGTH];
			namePos = 0;
			gamestate.setMode(gamestate.getNoneMode());
			gamestate.setState(gamestate.getStartScreenState());
		} else {
			checkInput(input);
		}
	}

	/**
	 * checks input.
	 * @param input - the input
	 */
	private void checkInput(final List<String> input) {
		if (!input.isEmpty() && "BACK_SPACE".equals(input.get(0))
				&& !"BACK_SPACE".equals(pressedButton) && namePos > 0) {
			if (namePos != INPUT_LENGTH) {
				name[namePos] = ' ';
			}
			namePos--;
			pressedButton = "BACK_SPACE";
		} else if (namePos != INPUT_LENGTH) {
			updateName(input);
		}
	}

	/**
	 * make name into string by replacing underscores.
	 * @return the string
	 */
	private String nameString() {
		for (int i = 0; i < name.length; i++) {
			if (name[i] == '_') {
				name[i] = ' ';
			}
		}
		return new String(name);
	}

	/**
	 * updates the name input.
	 * @param input - the input.
	 */
	private void updateName(final List<String> input) {
		if (!input.isEmpty() && input.get(0).length() == 1 && !input.get(0).equals(pressedButton)) {
			name[namePos] = input.get(0).charAt(0);
			namePos++;
			pressedButton = input.get(0);
		} else if (!checkSpace(input)) {
			flicker();
		}
		if (input.isEmpty()) {
			pressedButton = "Released";
		}
	}

	/**
	 * checks if there is a space.
	 * @param input - the input
	 * @return true if there is a space
	 */
	private boolean checkSpace(final List<String> input) {
		if (!input.isEmpty() && "SPACE".equals(input.get(0))	&& !"SPACE".equals(pressedButton)) {
			name[namePos] = ' ';
			namePos++;
			pressedButton = "SPACE";
			return true;
		}
		return false;
	}

	/**
	 * flicker the underscore to indicate input.
	 */
	private void flicker() {
		if (System.currentTimeMillis() % FLICKER_TIME < FLICKER_TIME / 2) {
			name[namePos] = '_';
		} else {
			name[namePos] = ' ';
		}
	}

	/**
	 * @param pressedButton the pressedButton to set
	 */
	public final void setPressedButton(final String pressedButton) {
		this.pressedButton = pressedButton;
	}

}
