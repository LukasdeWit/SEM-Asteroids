package game.modes;

import game.Game;

/**
 * Class that represents single-player arcade mode.
 * @author Esmee
 *
 */
public class ArcadeMode extends AbstractMode {
	/**
	 * Constructor for arcademode.
	 * @param game this mode belongs to.
	 */
	public ArcadeMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Single Player Arcade";
	}

	@Override
	public final boolean isCoop() {
		return false;
	}

	@Override
	public final boolean isBoss() {
		return false;
	}
}
