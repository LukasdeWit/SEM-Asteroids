package game.gamestates;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;

public class StartScreenState extends State {	
	public StartScreenState (Game game) {
		super(game);
	}

	@Override
	public void update(List<String> input) {
		startScreen(input);
		DisplayText.startScreen();		
	}
	
	/**
	 * update the gamemode startScreen.
	 * @param input - input
	 * @return 
	 */
	private void startScreen(final List<String> input) {
		Gamestate gameState = getThisGame().getGamestate();
		if (input.contains("X")) {
			mode = MODE_ARCADE;
			gameState.setState(gameState.getOngoingGameState());
			thisGame.startGame();
		} else if (input.contains("C")) {
			mode = MODE_COOP;
			gameState.setState(gameState.getOngoingGameState());
			thisGame.startGame();
		} else if (input.contains("B")) {
			mode = MODE_BOSS;
			gameState.setState(gameState.getOngoingGameState());
			thisGame.startGame();
		} else if (input.contains("N")) {
			mode = MODE_BOSS_COOP;
			gameState.setState(gameState.getOngoingGameState());
			thisGame.startGame();
		}
	}
}
