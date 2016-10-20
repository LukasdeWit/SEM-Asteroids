package game;

import display.DisplayText;
import game.gamestates.AbstractState;
import game.gamestates.HighscoreScreenState;
import game.gamestates.OngoingGameState;
import game.gamestates.PauseScreenState;
import game.gamestates.StartScreenState;
import game.modes.AbstractMode;
import game.modes.BossMode;
import game.modes.CoopArcadeMode;
import game.modes.CoopBossMode;
import game.modes.SinglePlayerArcadeMode;
import game.modes.NoneMode;
import game.modes.SinglePlayerArcadeMode;

import java.util.List;

/**
 * This class handles the switching of gamestates.
 * @author Kibo
 *
 */
public final class Gamestate {
	private int state;
	private int mode;
	private final Game thisGame;
	
	// states
	private AbstractState currentState;
	private HighscoreScreenState highscoreScreenState;
	private OngoingGameState ongoingGameState;
	private PauseScreenState pauseScreenState;
	private StartScreenState startScreenState;
	
	// modes
	private AbstractMode currentMode;
	private NoneMode noneMode;
	private SinglePlayerArcadeMode singlePlayerArcadeMode;
	private CoopArcadeMode coopArcadeMode;
	private BossMode bossMode;
	private CoopBossMode coopBossMode;
	
	private static final int STATE_START_SCREEN = 0;
	private static final int STATE_GAME = 1;
	private static final int STATE_HIGHSCORE_SCREEN = 2;
	private static final int STATE_PAUSE_SCREEN = 3;
	
	private static final int MODE_NONE = 0;
	private static final int MODE_ARCADE = 1;
	private static final int MODE_COOP = 2;
	private static final int MODE_BOSS = 3;
	private static final int MODE_BOSS_COOP = 4;
		
	
	/**
	 * constructor.
	 * @param thisGame this game
	 */
	public Gamestate(final Game thisGame) {
		startScreenState = new StartScreenState(thisGame);
		pauseScreenState = new PauseScreenState(thisGame);
		ongoingGameState = new OngoingGameState(thisGame);
		highscoreScreenState = new HighscoreScreenState(thisGame);
		currentState = startScreenState;
		coopArcadeMode = new CoopArcadeMode(thisGame);
		noneMode = new NoneMode(thisGame);
		singlePlayerArcadeMode = new SinglePlayerArcadeMode(thisGame);
		currentMode = noneMode;
		
		this.thisGame = thisGame;
		this.mode = MODE_NONE;
		state = STATE_START_SCREEN;
	}
	
	public void start() {
		currentState.start();
	}
	
	/**
	 * update the gamemodes.
	 * @param input - input
	 */
	public void update(final List<String> input) {
		currentState.update(input);
	}

	/**
	 * Get string of current gamestate for logging.
	 * @return String representing the current state.
	 */
	public String toString() {
		String res;
		switch (state) {
		case MODE_NONE:
			res = "None";
			break;
		case MODE_ARCADE:
			res = "Arcade";
			break;
		case MODE_COOP:
			res = "Arcade coop";
			break;
		case MODE_BOSS:
			res = "Boss";
			break;
		case MODE_BOSS_COOP:
			res = "Boss coop";
			break;
		default:
			res = "";
			break;
		}
		return res;
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(final int mode) {
		this.mode = mode;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(final int state) {
		this.state = state;
	}

	/**
	 * @return the stateStartScreen
	 */
	public static int getStateStartScreen() {
		return STATE_START_SCREEN;
	}

	/**
	 * @return the stateHighscoreScreen
	 */
	public static int getStateHighscoreScreen() {
		return STATE_HIGHSCORE_SCREEN;
	}

	/**
	 * @return the modeArcade
	 */
	public static int getModeArcade() {
		return MODE_ARCADE;
	}

	/**
	 * @return the modeCoop
	 */
	public static int getModeCoop() {
		return MODE_COOP;
	}
	
	/**
	 * @return the modeBoss
	 */
	public static int getModeBoss() {
		return MODE_BOSS;
	}
	
	/**
	 * @return the modeBossCoop
	 */
	public static int getModeBossCoop() {
		return MODE_BOSS_COOP;
	}

	/**
	 * @return true if coop
	 */
	public boolean isCoop() {
		return getMode() == getModeCoop() || getMode() == getModeBossCoop();
	}
	
	/**
	 * @return true if boss
	 */
	public boolean isBoss() {
		return getMode() == getModeBoss() || getMode() == getModeBossCoop();
	}

	/**
	 * @return the stateGame
	 */
	public static int getStateGame() {
		return STATE_GAME;
	}

	/**
	 * @return the statePauseScreen
	 */
	public static int getStatePauseScreen() {
		return STATE_PAUSE_SCREEN;
	}

	/**
	 * @return the modeNone
	 */
	public static int getModeNone() {
		return MODE_NONE;
	}
	
	/**
	 * @param restartTime the restartTime to set
	 */
	public void setRestartTime(final long restartTime) {
		currentState.setRestartTime(restartTime);
	}

	/**
	 * @param pauseTime the pauseTime to set
	 */
	public void setPauseTime(final long pauseTime) {
		currentState.setPauseTime(pauseTime);
	}
	
	public void setState(AbstractState state) {
		this.currentState = state;
	}
	
	public HighscoreScreenState getHighscoreState() {
		return highscoreScreenState;
	}
	
	public StartScreenState getStartScreenState() {
		return startScreenState;
	}
	
	public OngoingGameState getOngoingGameState() {
		return ongoingGameState;
	}
	
	public PauseScreenState getPauseScreenState() {
		return pauseScreenState;
	}
	
	public void setMode(AbstractMode mode) {
		this.currentMode = mode;
	}
	
	public NoneMode getNoneMode() {
		return noneMode;
	}
	
	public CoopArcadeMode getCoopArcadeMode() {
		return coopArcadeMode;
	}
	
	public BossMode getBossMode() {
		return bossMode;
	}
	
	public CoopBossMode getCoopBossMode() {
		return coopBossMode;
	}
	
	public SinglePlayerArcadeMode getSinglePlayerArcadeMode() {
		return singlePlayerArcadeMode;
	}
}
