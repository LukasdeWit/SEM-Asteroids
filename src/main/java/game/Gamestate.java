package game;

import game.gamestates.AbstractState;
import game.gamestates.HighscoreScreenState;
import game.gamestates.OngoingGameState;
import game.gamestates.PauseScreenState;
import game.gamestates.StartScreenState;
import game.modes.AbstractMode;
import game.modes.BossMode;
import game.modes.CoopArcadeMode;
import game.modes.CoopBossMode;
import game.modes.ArcadeMode;
import game.modes.NoneMode;

import java.util.List;

/**
 * This class handles the switching of gamestates.
 * @author Kibo
 *
 */
public final class Gamestate {	
	// states
	private AbstractState currentState;
	private final HighscoreScreenState highscoreScreenState;
	private final OngoingGameState ongoingGameState;
	private final PauseScreenState pauseScreenState;
	private final StartScreenState startScreenState;
	
	// modes
	private AbstractMode currentMode;
	private final NoneMode noneMode;
	private final ArcadeMode arcadeMode;
	private final CoopArcadeMode coopArcadeMode;
	private final BossMode bossMode;
	private final CoopBossMode coopBossMode;
		
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
		arcadeMode = new ArcadeMode(thisGame);
		bossMode = new BossMode(thisGame);
		coopBossMode = new CoopBossMode(thisGame);
		currentMode = noneMode;
	}
	
	/**
	 * start game.
	 */
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
	
	/**
	 * Set the current state.
	 * @param state the gamestate should be
	 */
	public void setState(final AbstractState state) {
		this.currentState = state;
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
}
