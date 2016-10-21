package game.modes;

import game.Game;

/**
 * Class that represents coop arcade mode.
 * @author Esmee
 *
 */
public class CoopArcadeMode extends AbstractMode {
	/**
	 * Constructor for coop arcade mode.
	 * @param game this belongs to
	 */
	public CoopArcadeMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Arcade Coop";
	}

	@Override
	public final boolean isCoop() {
		return true;
	}

	@Override
	public final boolean isBoss() {
		return false;
	}

	@Override
	public final boolean isArcade() {
		return true;
	}

	@Override
	public final boolean isSurvival() {
		return false;
	}

}
