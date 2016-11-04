package game;

import game.states.AbstractState;
import game.states.HighscoreScreenState;
import game.states.OngoingGameState;
import game.states.PauseScreenState;
import game.states.StartScreenState;
import game.states.ViewHighscoresState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class handles the switching of gamestates.
 * @author Kibo
 *
 */
@Getter
public final class Gamestate {
	private static final String[] MODE_STRINGS = 
		{"none", "arcade", "coop arcade", "survival", "coop survival", "boss", "coop boss"};
	private static final long MINIMAL_SWITCH_TIME = 300;
	
	private final Game thisGame;
	
	// states
	private AbstractState currentState;
	private final HighscoreScreenState highscoreScreenState;
	private final OngoingGameState ongoingGameState;
	private final PauseScreenState pauseScreenState;
	private final StartScreenState startScreenState;
	private final ViewHighscoresState viewHighscoresState;
	
	// modes
	@Setter
	private int currentMode;
	
	public static final int NONEMODE = 0;
	public static final int ARCADEMODE = 1;
	public static final int COOPARCADEMODE = 2;
	public static final int SURVIVALMODE = 3;
	public static final int COOPSURVIVALMODE = 4;
	public static final int BOSSMODE = 5;
	public static final int COOPBOSSMODE = 6;
	
	@Setter
	private long screenSwitchTime;
		
	/**
	 * constructor.
	 * @param thisGame this game
	 */
	public Gamestate(final Game thisGame) {	
		this.thisGame = thisGame;
		screenSwitchTime = System.currentTimeMillis();
		
		startScreenState = new StartScreenState(thisGame);
		pauseScreenState = new PauseScreenState(thisGame);
		ongoingGameState = new OngoingGameState(thisGame);
		highscoreScreenState = new HighscoreScreenState(thisGame);
		viewHighscoresState = new ViewHighscoresState(thisGame);
		
		currentState = startScreenState;
				
		currentMode = NONEMODE;
	}
	
	/**
	 * update the states.
	 * @param input - input
	 */
	public void update(final List<String> input) {
		currentState.update(input);
	}
	
	/**
	 * update the gamemode startScreen.
	 * @param input - input
	 */
	public void startScreen(final List<String> input) {
		if (input.contains("H")) {
			Logger.getInstance().log("Go to highscores screen");
			setCurrentMode(NONEMODE);
			setState(viewHighscoresState);
		} else if (input.contains("ESCAPE")) {
			Logger.getInstance().log("Player quit the game.");
			Launcher.quit();
		} else {
			checkModeInput(input);
		}
	}
	
	/**
	 * checks the input on which mode should be chosen.
	 * @param input - the input.
	 */
	private void checkModeInput(final List<String> input) {
		if (input.contains("A")) {
			setCurrentMode(ARCADEMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("Z")) {
			setCurrentMode(COOPARCADEMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("S")) {
			setCurrentMode(SURVIVALMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("X")) {
			setCurrentMode(COOPSURVIVALMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("D")) {
			setCurrentMode(BOSSMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("C")) {
			setCurrentMode(COOPBOSSMODE);
			setState(ongoingGameState);
			thisGame.startGame();
		} 
	}

	/**
	 * makes modeInt into string.
	 * @param modeInt - the int of the mode
	 * @return the string of the mode
	 */
	public String intToString(final int modeInt) {
		return MODE_STRINGS[modeInt];
	}
	
	/**
	 * @return true if time to switch screens.
	 */
	public boolean isSwitchTime() {
		return System.currentTimeMillis() - screenSwitchTime > MINIMAL_SWITCH_TIME;
	}


	/**
	 * Get string of current gamestate for logging.
	 * @return String representing the current state.
	 */
	public String toString() {
		return MODE_STRINGS[currentMode];
	}

	/**
	 * @return the state
	 */
	public AbstractState getState() {
		return currentState;
	}

	/**
	 * @return true if coop
	 */
	public boolean isCoop() {
		return currentMode == COOPARCADEMODE || currentMode == COOPBOSSMODE || currentMode == COOPSURVIVALMODE;
	}
	
	/**
	 * @return true if boss
	 */
	public boolean isBoss() {
		return currentMode == BOSSMODE || currentMode == COOPBOSSMODE;
	}
	
	/**
	 * @return true if arcade mode
	 */
	public boolean isArcade() {
		return currentMode == ARCADEMODE || currentMode == COOPARCADEMODE;
	}
	
	/**
	 * @return true if survival mode
	 */
	public boolean isSurvival() {
		return currentMode == SURVIVALMODE || currentMode == COOPSURVIVALMODE;
	}
	
	/**
	 * Set the current state.
	 * @param state the gamestate should be
	 */
	public void setState(final AbstractState state) {
		this.currentState = state;
		screenSwitchTime = System.currentTimeMillis();
	}
	
	/**
	 * @return state for highscore screen
	 */
	public HighscoreScreenState getHighscoreState() {
		return highscoreScreenState;
	}
}
