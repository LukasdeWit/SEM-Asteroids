package game.states;

import java.util.List;

import display.DisplayText;
import game.Game;
import game.Gamestate;
import game.Launcher;
import game.Logger;

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
			if (input.contains("H")) {
				Logger.getInstance().log("Go to highscores screen");
				gamestate.setState(gamestate.getViewHighscoresState());
			} else if (input.contains("ESCAPE")) {
				Logger.getInstance().log("Player quit the game.");
				Launcher.quit();
			} else {
				startScreen(input);
			}
		}
		DisplayText.startScreen();		
	}
	
	/**
	 * update the gamemode startScreen.
	 * @param input - input
	 * @return 
	 */
	private void startScreen(final List<String> input) {
		if (input.contains("A")) {
			gamestate.setMode(gamestate.getArcadeMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("Z")) {
			gamestate.setMode(gamestate.getCoopArcadeMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("S")) {
			gamestate.setMode(gamestate.getSurvivalMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("X")) {
			gamestate.setMode(gamestate.getCoopSurvivalMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("D")) {
			gamestate.setMode(gamestate.getBossMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		} else if (input.contains("C")) {
			gamestate.setMode(gamestate.getCoopBossMode());
			gamestate.setState(gamestate.getOngoingGameState());
			getThisGame().startGame();
		}
	}
}
