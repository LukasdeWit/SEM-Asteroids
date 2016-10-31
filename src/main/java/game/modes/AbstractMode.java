package game.modes;

import game.Game;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class that game modes can inherit from.
 * @author Esmee
 *
 */
public abstract class AbstractMode {
	@Getter
	@Setter private Game thisGame;
	
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
	@Override
	public abstract String toString();
	
	/**
	 * give the number of this mode.
	 * @return the number
	 */
	public abstract int toInt();
	
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
}
