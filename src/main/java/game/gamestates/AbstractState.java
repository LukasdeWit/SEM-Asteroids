package game.gamestates;

import java.util.List;

import game.Game;

public abstract class AbstractState {
	private Game thisGame;
	protected static final long MINIMAL_PAUSE_TIME = 300;
	protected static final long MINIMAL_RESTART_TIME = 300;
	protected long pauseTime;
	protected long restartTime;
	
	public AbstractState(Game game) {
		thisGame = game;
	}
	
	public abstract void update(List<String> input);
	
	public void setThisGame(Game game) {
		thisGame = game;
	}
	
	public Game getThisGame() {
		return thisGame;
	}
	
	/**
	 * start game.
	 */
	public void start() {
		restartTime = System.currentTimeMillis();
		pauseTime = restartTime;
		
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
