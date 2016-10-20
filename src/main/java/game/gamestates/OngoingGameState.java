package game.gamestates;

import java.util.List;

import game.Game;
import game.Gamestate;
import game.Logger;

public class OngoingGameState extends State {
	public OngoingGameState (Game game) {
		super(game);
	}
	@Override
	public void update(List<String> input) {
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
				- restartTime > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			mode = MODE_NONE;
			gameState.setState(gameState.getStartScreenState());
		} else if (input.contains("P") && System.currentTimeMillis() 
				- pauseTime > MINIMAL_PAUSE_TIME) {
			pauseTime = System.currentTimeMillis();
			Logger.getInstance().log("Game paused.");
			gameState.setState(gameState.getPauseScreenState());
		}
	}

}
