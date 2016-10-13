package game;

import display.DisplayText;

import java.util.List;

/**
 * This class handles the switching of gamestates.
 *
 * @author Kibo
 */
public final class Gamestate {
	private int state;
	private int mode;
	private long pauseTime;
	private long restartTime;
	private final Game thisGame;

	private static final int STATE_START_SCREEN = 0;
	private static final int STATE_GAME = 1;
	private static final int STATE_HIGHSCORE_SCREEN = 2;
	private static final int STATE_PAUSE_SCREEN = 3;

	private static final int MODE_NONE = 0;
	private static final int MODE_ARCADE = 1;
	private static final int MODE_COOP = 2;

	private static final long MINIMAL_PAUSE_TIME = 300;
	private static final long MINIMAL_RESTART_TIME = 300;

	/**
	 * constructor.
	 *
	 * @param thisGame this game
	 */
	public Gamestate(final Game thisGame) {
		this.thisGame = thisGame;
		this.mode = MODE_NONE;
		state = STATE_START_SCREEN;
	}

	/**
	 * start game.
	 */
	public void start() {
		restartTime = System.currentTimeMillis();
		pauseTime = restartTime;

	}

	/**
	 * update the gamemodes.
	 *
	 * @param input input
	 */
	public void update(final List<String> input) {
		switch(state) {
		case STATE_START_SCREEN:
			startScreen(input);
			DisplayText.startScreen();
			break;
		case STATE_GAME:
			thisGame.updateGame(input);
			game(input);
			break;
		case STATE_HIGHSCORE_SCREEN:
			highscoreScreen(input);
			DisplayText.highscoreScreen(thisGame.getHighscore());
			break;
		case STATE_PAUSE_SCREEN:
		default:
			DisplayText.pauseScreen();
			pauseScreen(input);
			break;
		}
	}

	/**
	 * update the gamemode startScreen.
	 *
	 * @param input input
	 */
	private void startScreen(final List<String> input) {
		if (input.contains("X")) {
			mode = MODE_ARCADE;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("C")) {
			mode = MODE_COOP;
			state = STATE_GAME;
			thisGame.startGame();
		}
	}

	/**
	 * update the game gamemode.
	 *
	 * @param input the input
	 */
	private void game(final List<String> input) {
		if (input.contains("R") && System.currentTimeMillis()
				- restartTime > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			mode = MODE_NONE;
			state = STATE_START_SCREEN;
		} else if (input.contains("P") && System.currentTimeMillis()
				- pauseTime > MINIMAL_PAUSE_TIME) {
			pauseTime = System.currentTimeMillis();
			Logger.getInstance().log("Game paused.");
			state = STATE_PAUSE_SCREEN;
		}
	}

	/**
	 * update the highscore gamemode.
	 *
	 * @param input  the input
	 */
	private void highscoreScreen(final List<String> input) {
		if (input.contains("R")) {
			Logger.getInstance().log("Game stopped.");
			thisGame.startGame();
		}
	}

	/**
	 * update the pause screen gamemode.
	 *
	 * @param input  the input
	 */
	private void pauseScreen(final List<String> input) {
		if (input.contains("P") && System.currentTimeMillis()
				- pauseTime > MINIMAL_PAUSE_TIME) {
			pauseTime = System.currentTimeMillis();
			Logger.getInstance().log("Game unpaused.");
			state = STATE_GAME;
		} else if (input.contains("R") && System.currentTimeMillis()
				- restartTime > MINIMAL_RESTART_TIME) {
			Logger.getInstance().log("Game stopped.");
			thisGame.startGame();
			state = STATE_GAME;
		}
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
	 * @return true if coop
	 */
	public boolean isCoop() {
		return getMode() == getModeCoop();
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
		this.restartTime = restartTime;
	}

	/**
	 * @param pauseTime the pauseTime to set
	 */
	public void setPauseTime(final long pauseTime) {
		this.pauseTime = pauseTime;
	}
}
