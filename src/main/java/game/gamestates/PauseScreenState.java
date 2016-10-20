package game.gamestates;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;
import game.Logger;

public class PauseScreenState extends State {

	public PauseScreenState(Game game) {
		super(game);
	}

	@Override
	public void update(List<String> input) {
		DisplayText.pauseScreen();
		pauseScreen(input);		
	}
	
	/**
	 * update the pause screen gamemode.
	 * @param input - the input
	 */
	private void pauseScreen(final List<String> input) {
		Gamestate gameState = getThisGame().getGamestate();

		if (input.contains("P") && System.currentTimeMillis() 
				- pauseTime > MINIMAL_PAUSE_TIME) {
			pauseTime = System.currentTimeMillis();
			Logger.getInstance().log("Game unpaused.");
			gameState.setState(gameState.getOngoingGameState());
		} else if (input.contains("R") && System.currentTimeMillis() 
				- restartTime > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			getThisGame().startGame();
			gameState.setState(gameState.getOngoingGameState());
		}
	}
}
