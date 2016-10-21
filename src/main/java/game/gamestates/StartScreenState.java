package game.gamestates;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;

/**
 * Start screen state.
 * @author Esmee
 *
 */
public class StartScreenState extends AbstractState {
	/**
	 * Constructor for start screen state.
	 * @param game this state belongs to
	 */
	public StartScreenState(final Game game) {
		super(game);
	}

	@Override
	public final void update(final List<String> input) {
		startScreen(input);
		DisplayText.startScreen();		
	}
	
	/**
	 * update the gamemode startScreen.
	 * @param input - input
	 * @return 
	 */
	private void startScreen(final List<String> input) {
		final Gamestate gameState = getThisGame().getGamestate();
		if (input.contains("A")) {
			gameState.setMode(gameState.getArcadeMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("Z")) {
			gameState.setMode(gameState.getCoopArcadeMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("S")) {
			gameState.setMode(gameState.getBossMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("X")) {
			gameState.setMode(gameState.getCoopBossMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("D")) {
			gameState.setMode(gameState.getSurvivalMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("C")) {
			gameState.setMode(gameState.getCoopSurvivalMode());
			gameState.setState(gameState.getOngoingGameState());
			getThisGame().startGame();
		}
	}
}
