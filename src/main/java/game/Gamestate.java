package game;

import java.util.List;

import display.DisplayText;

/**
 * This class handles the switching of gamestates.
 * @author Kibo
 *
 */
public final class Gamestate {
	private int state;
	private int mode;
	private long screenSwitchTime;
	private final Game thisGame;
	private char[] name;
	private int namePos;
	private String pressedButton;
	
	private static final String[] MODE_STRING = 
		{"None", "Arcade", "Arcade coop", "Boss", "Boss coop", "Survival", "Survival coop"};
	
	private static final int STATE_START_SCREEN = 0;
	private static final int STATE_GAME = 1;
	private static final int STATE_HIGHSCORE_SCREEN = 2;
	private static final int STATE_PAUSE_SCREEN = 3;
	private static final int STATE_VIEW_HIGHSCORES = 4;
	
	private static final int MODE_NONE = 0;
	private static final int MODE_ARCADE = 1;
	private static final int MODE_ARCADE_COOP = 2;
	private static final int MODE_BOSS = 3;
	private static final int MODE_BOSS_COOP = 4;
	private static final int MODE_SURVIVAL = 5;
	private static final int MODE_SURVIVAL_COOP = 6;
	
	private static final long MINIMAL_SWITCH_TIME = 300;
	private static final long FLICKER_TIME = 200;
	private static final int INPUT_LENGTH = 8;
	
	/**
	 * constructor.
	 * @param thisGame this game
	 */
	public Gamestate(final Game thisGame) {
		this.thisGame = thisGame;
		this.mode = MODE_NONE;
		state = STATE_START_SCREEN;
		name = new char[INPUT_LENGTH];
		namePos = 0;
	}
	
	/**
	 * start game.
	 */
	public void start() {
		screenSwitchTime = System.currentTimeMillis();
	}
	
	/**
	 * update the states.
	 * @param input - input
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
			DisplayText.highscoreScreen(thisGame.getScoreCounter().getScore(), new String(name));
			break;
		case STATE_VIEW_HIGHSCORES:
			viewHighscoresScreen(input);
			DisplayText.viewHighscoresScreen(thisGame.getScoreCounter().highScoresToStrings());
			break;
		case STATE_PAUSE_SCREEN:
		default:
			DisplayText.pauseScreen();
			pauseScreen(input);
			break;
		}
	}

	/**
	 * update the startScreen state.
	 * @param input - input
	 * @return 
	 */
	private void startScreen(final List<String> input) {
		startMode(input);
		if (input.contains("H")) {
			Logger.getInstance().log("View Highscores.");
			state = STATE_VIEW_HIGHSCORES;
		} 
	}
	
	/**
	 * starts different modes.
	 * @param input - the input.
	 */
	private void startMode(final List<String> input) {
		if (input.contains("A")) {
			mode = MODE_ARCADE;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("Z")) {
			mode = MODE_ARCADE_COOP;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("S")) {
			mode = MODE_BOSS;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("X")) {
			mode = MODE_BOSS_COOP;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("D")) {
			mode = MODE_SURVIVAL;
			state = STATE_GAME;
			thisGame.startGame();
		} else if (input.contains("C")) {
			mode = MODE_SURVIVAL_COOP;
			state = STATE_GAME;
			thisGame.startGame();
		}
	}

	/**
	 * update the highscorescreen state. 
	 * @param input - the input
	 */
	private void viewHighscoresScreen(final List<String> input) {
		if (input.contains("R") && isSwitchTime()) {
			Logger.getInstance().log("Return to start screen.");
			screenSwitchTime = System.currentTimeMillis();
			state = STATE_START_SCREEN;
		} else if (input.contains("D") && isSwitchTime()) {
			thisGame.getScoreCounter().clearHighscores();
		}
	}

	/**
	 * update the game state.
	 * @param input - the input
	 */
	private void game(final List<String> input) {
		if (input.contains("R") && isSwitchTime()) {
			Logger.getInstance().log("Game stopped.");
			screenSwitchTime = System.currentTimeMillis();
			pressedButton = "R";
			thisGame.overSwitch();
		} else if (input.contains("P") && isSwitchTime()) {
			screenSwitchTime = System.currentTimeMillis();
			thisGame.getAudio().stopAll();
			Logger.getInstance().log("Game paused.");
			state = STATE_PAUSE_SCREEN;
		}
	}

	/**
	 * update the highscore state.
	 * @param input - the input
	 */
	private void highscoreScreen(final List<String> input) {
		if (namePos != 0 && input.contains("ENTER") && isSwitchTime()) {
			Logger.getInstance().log("Game stopped.");
			thisGame.startGame();
			thisGame.getScoreCounter().startGame(nameString());
			name = new char[INPUT_LENGTH];
			namePos = 0;
			mode = MODE_NONE;
			state = STATE_START_SCREEN;
		} else {
			checkInput(input);
		}
	}

	/**
	 * checks input.
	 * @param input - the input
	 */
	private void checkInput(final List<String> input) {
		if (!input.isEmpty() && "BACK_SPACE".equals(input.get(0))
				&& !"BACK_SPACE".equals(pressedButton) && namePos > 0) {
			if (namePos != INPUT_LENGTH) {
				name[namePos] = ' ';
			}
			namePos--;
			pressedButton = "BACK_SPACE";
		} else if (namePos != INPUT_LENGTH) {
			updateName(input);
		}
	}

	/**
	 * make name into string by replacing underscores.
	 * @return the string
	 */
	private String nameString() {
		for (int i = 0; i < name.length; i++) {
			if (name[i] == '_') {
				name[i] = ' ';
			}
		}
		return new String(name);
	}

	/**
	 * updates the name input.
	 * @param input - the input.
	 */
	private void updateName(final List<String> input) {
		if (!input.isEmpty() && input.get(0).length() == 1 && !input.get(0).equals(pressedButton)) {
			name[namePos] = input.get(0).charAt(0);
			namePos++;
			pressedButton = input.get(0);
		} else if (!checkSpace(input)) {
			flicker();
		}
		if (input.isEmpty()) {
			pressedButton = "Released";
		}
	}

	/**
	 * checks if there is a space.
	 * @param input - the input
	 * @return true if there is a space
	 */
	private boolean checkSpace(final List<String> input) {
		if (!input.isEmpty() && "SPACE".equals(input.get(0))	&& !"SPACE".equals(pressedButton)) {
			name[namePos] = ' ';
			namePos++;
			pressedButton = "SPACE";
			return true;
		}
		return false;
	}

	/**
	 * flicker the underscore to indicate input.
	 */
	private void flicker() {
		if (System.currentTimeMillis() % FLICKER_TIME < FLICKER_TIME / 2) {
			name[namePos] = '_';
		} else {
			name[namePos] = ' ';
		}
	}

	/**
	 * update the pause screen state.
	 * @param input - the input
	 */
	private void pauseScreen(final List<String> input) {
		if (input.contains("P") && isSwitchTime()) {
			screenSwitchTime = System.currentTimeMillis();
			Logger.getInstance().log("Game unpaused.");
			state = STATE_GAME;
		} else if (input.contains("R") && isSwitchTime()) {
			Logger.getInstance().log("Game stopped.");
			screenSwitchTime = System.currentTimeMillis();
			thisGame.overSwitch();
		}
	}
	
	/**
	 * @return true if time to switch screens.
	 */
	private boolean isSwitchTime() {
		return System.currentTimeMillis() - screenSwitchTime > MINIMAL_SWITCH_TIME;
	}
	
	/**
	 * Get string of current gamestate for logging.
	 * @return String representing the current state.
	 */
	public String toString() {
		return MODE_STRING[mode];
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
	 * @return the modeArcadeCoop
	 */
	public static int getModeArcadeCoop() {
		return MODE_ARCADE_COOP;
	}
	
	/**
	 * @return the modeSurvival
	 */
	public static int getModeSurvival() {
		return MODE_SURVIVAL;
	}
	
	/**
	 * @return the modeSurvivalCoop
	 */
	public static int getModeSurvivalCoop() {
		return MODE_SURVIVAL_COOP;
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
		return getMode() == getModeArcadeCoop() || getMode() == getModeSurvivalCoop() || getMode() == getModeBossCoop();
	}
	
	/**
	 * @return true if arcade
	 */
	public boolean isArcade() {
		return getMode() == getModeArcade() || getMode() == getModeArcadeCoop();
	}
	
	/**
	 * @return true if survival
	 */
	public boolean isSurvival() {
		return getMode() == getModeSurvival() || getMode() == getModeSurvivalCoop();
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
	 * @param screenSwitchTime the screenSwitchTime to set
	 */
	public void setScreenSwitchTime(final long screenSwitchTime) {
		this.screenSwitchTime = screenSwitchTime;
	}

	/**
	 * @return the modeString
	 */
	public static String[] getModeString() {
		return MODE_STRING.clone();
	}
}
