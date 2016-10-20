package game.gamestates;

import java.util.List;

import game.Game;

/**
 * abstract class for game states.
 * @author Esmee
 *
 */
public abstract class AbstractState {
	private Game thisGame;
	protected static final long MINIMAL_PAUSE_TIME = 300;
	protected static final long MINIMAL_RESTART_TIME = 300;
	private long pauseTime;
	private long restartTime;
	
	/**
	 * Constructor for abstract game state.
	 * @param game this state belongs to
	 */
	public AbstractState(final Game game) {
		thisGame = game;
	}
	
	/**
	 * Update state with keyboard input.
	 * @param input to update the state with
	 */
	public abstract void update(List<String> input);
	
	/**
	 * Set current game of state.
	 * @param game this state should belong to
	 */
	public final void setThisGame(final Game game) {
		thisGame = game;
	}
	
	/**
	 * Get current game of state.
	 * @return the game this state belongs to
	 */
	public final Game getThisGame() {
		return thisGame;
	}
	
	/**
	 * start game.
	 */
	public final void start() {
		restartTime = System.currentTimeMillis();
		pauseTime = restartTime;
		
	}
	
	/**
	 * @param restartTime the restartTime to set.
	 */
	public final void setRestartTime(final long restartTime) {
		this.restartTime = restartTime;
	}
	
	/**
	 * getter for restarttime.
	 * @return the restart time
	 */
	public final long getRestartTime() {
		return restartTime;
	}

	/**
	 * @param pauseTime the pauseTime to set.
	 */
	public final void setPauseTime(final long pauseTime) {
		this.pauseTime = pauseTime;
	}
	
	/**
	 * getter for pausetime.
	 * @return the pause time
	 */
	public final long getPauseTime() {
		return pauseTime;
	}
}
