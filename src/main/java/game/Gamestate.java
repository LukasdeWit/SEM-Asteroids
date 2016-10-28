package game;

import java.util.List;

import game.modes.AbstractMode;
import game.modes.ArcadeMode;
import game.modes.BossMode;
import game.modes.CoopArcadeMode;
import game.modes.CoopBossMode;
import game.modes.CoopSurvivalMode;
import game.modes.NoneMode;
import game.modes.SurvivalMode;
import game.states.AbstractState;
import game.states.HighscoreScreenState;
import game.states.OngoingGameState;
import game.states.PauseScreenState;
import game.states.StartScreenState;
import game.states.ViewHighscoresState;

/**
 * This class handles the switching of gamestates.
 * @author Kibo
 *
 */
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
	private AbstractMode currentMode;
	private final NoneMode noneMode;
	private final ArcadeMode arcadeMode;
	private final CoopArcadeMode coopArcadeMode;
	private final BossMode bossMode;
	private final CoopBossMode coopBossMode;
	private final SurvivalMode survivalMode;
	private final CoopSurvivalMode coopSurvivalMode;
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
	 * @return 
	 */
	public void startScreen(final List<String> input) {
		if (input.contains("H")) {
			Logger.getInstance().log("Go to highscores screen");
			setMode(noneMode);
			setState(viewHighscoresState);
		} else if (input.contains("A")) {
			setMode(arcadeMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("Z")) {
			setMode(coopArcadeMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("S")) {
			setMode(survivalMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("X")) {
			setMode(coopSurvivalMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("D")) {
			setMode(bossMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("C")) {
			setMode(coopBossMode);
			setState(ongoingGameState);
			thisGame.startGame();
		} else if (input.contains("ESCAPE")) {
			Logger.getInstance().log("Player quit the game.");
			Launcher.quit();
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
	 * @return the mode
	 */
	public AbstractMode getMode() {
		return currentMode;
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
	 * @return true if survival mode
	 */
	public boolean isSurvival() {
		return currentMode.isSurvival();
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
	
	/**
	 * @return state for start screen
	 */
	public StartScreenState getStartScreenState() {
		return startScreenState;
	}
	
	/**
	 * @return state for ongoing game
	 */
	public OngoingGameState getOngoingGameState() {
		return ongoingGameState;
	}
	
	/**
	 * @return state for pause screen
	 */
	public PauseScreenState getPauseScreenState() {
		return pauseScreenState;
	}
	
	/**
	 * Setter for mode.
	 * @param mode the game should be in
	 */
	public void setMode(final AbstractMode mode) {
		this.currentMode = mode;
	}
	
	/**
	 * @return mode for no gamemode
	 */
	public NoneMode getNoneMode() {
		return noneMode;
	}
	
	/**
	 * @return mode for coop arcade game
	 */
	public CoopArcadeMode getCoopArcadeMode() {
		return coopArcadeMode;
	}
	
	/**
	 * @return mode for single player boss fight
	 */
	public BossMode getBossMode() {
		return bossMode;
	}
	
	/**
	 * @return mode for multiplayer boss fight
	 */
	public CoopBossMode getCoopBossMode() {
		return coopBossMode;
	}
	
	/**
	 * @return mode for single player arcade game
	 */
	public ArcadeMode getArcadeMode() {
		return arcadeMode;
	}
	
	/**
	 * @param screenSwitchTime the screenSwitchTime to set
	 */
	public void setScreenSwitchTime(final long screenSwitchTime) {
		this.screenSwitchTime = screenSwitchTime;
	}
	
	/**
	 * @return survival mode
	 */
	public SurvivalMode getSurvivalMode() {
		return survivalMode;
	}
	
	/**
	 * @return the coop survival mode
	 */
	public CoopSurvivalMode getCoopSurvivalMode() {
		return coopSurvivalMode;
	}

	/**
	 * @return the viewHighscoresState
	 */
	public ViewHighscoresState getViewHighscoresState() {
		return viewHighscoresState;
	}
}
