package game;

import game.modes.*;
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
	private AbstractMode currentMode;
	private final NoneMode noneMode;
	private final ArcadeMode arcadeMode;
	private final CoopArcadeMode coopArcadeMode;
	private final BossMode bossMode;
	private final CoopBossMode coopBossMode;
	private final SurvivalMode survivalMode;
	private final CoopSurvivalMode coopSurvivalMode;
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
		
		coopArcadeMode = new CoopArcadeMode(thisGame);
		noneMode = new NoneMode(thisGame);
		arcadeMode = new ArcadeMode(thisGame);
		bossMode = new BossMode(thisGame);
		coopBossMode = new CoopBossMode(thisGame);
		survivalMode = new SurvivalMode(thisGame);
		coopSurvivalMode = new CoopSurvivalMode(thisGame);
		
		currentMode = noneMode;
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
			setCurrentMode(noneMode);
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
			setCurrentMode(arcadeMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("Z")) {
			setCurrentMode(coopArcadeMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("S")) {
			setCurrentMode(survivalMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("X")) {
			setCurrentMode(coopSurvivalMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("D")) {
			setCurrentMode(bossMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("C")) {
			setCurrentMode(coopBossMode);
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
		return currentMode.toString();
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
		return currentMode.isCoop();
	}
	
	/**
	 * @return true if boss
	 */
	public boolean isBoss() {
		return currentMode.isBoss();
	}
	
	/**
	 * @return true if arcade mode
	 */
	public boolean isArcade() {
		return currentMode.isArcade();
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
