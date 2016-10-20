package game.gamestates;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;
import game.Logger;

public class HighscoreScreenState extends State {

	public HighscoreScreenState(Game game) {
		super(game);
	}

	@Override
	public void update(List<String> input) {
		highscoreScreen(input);
		DisplayText.highscoreScreen(getThisGame().getScoreCounter().getHighscore());		
	}
	
	/**
	 * update the highscore gamemode.
	 * @param input - the input
	 */
	private void highscoreScreen(final List<String> input) {
		Gamestate gameState = getThisGame().getGamestate();

		if (input.contains("R")) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().startGame();
			mode = MODE_NONE;
			gameState.setState(gameState.getStartScreenState());
		}
	}

}
