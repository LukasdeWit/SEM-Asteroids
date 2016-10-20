package game.modes;

import game.Game;

/**
 * Abstract class that game modes can inherit from.
 * @author Esmee
 *
 */
public abstract class AbstractMode {
	private Game thisGame;
	
	/**
	 * Constructor for AbstractMode.
	 * @param game that this mode belongs to
	 */
	public AbstractMode(final Game game) {
		setThisGame(game);
	}
	
	/**
	 * Default tostring method.
	 * @return string that represents this gamemode
	 */
	public abstract String toString();
	
	/**
	 * Method that returns whether the mode is a coop mode.
	 * @return true when it's a coop mode
	 */
	public abstract boolean isCoop();
	
	/**
	 * Method that returns whether the mode is a boss mode.
	 * @return true when it's a boss mode
	 */
	public abstract boolean isBoss();
	
	/**
	 * Method that returns whether the mode is an arcade mode.
	 * @return true when it's an arcade mode
	 */
	public abstract boolean isArcade();
	
	/**
	 * Method that returns whether the mode is a survival mode.
	 * @return true when it's a survival mode
	 */
	public abstract boolean isSurvival();
	
	/**
	 * Getter for thisGame.
	 * @return thisgame
	 */
	public final Game getThisGame() {
		return thisGame;
	}
	
	/**
	 * Setter for thisgame.
	 * @param game thisGame should be
	 */
	public final void setThisGame(final Game game) {
		thisGame = game;
	}
}
