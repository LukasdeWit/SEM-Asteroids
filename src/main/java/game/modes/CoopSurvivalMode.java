package game.modes;

import game.Game;

/**
 * Coop survival mode.
 * @author Esmee
 *
 */
public class CoopSurvivalMode extends AbstractMode {

	/**
	 * Constructor for coop survival mode.
	 * @param game this survival mode belongs to.
	 */
	public CoopSurvivalMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Coop Survival";
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
		return false;
	}

	@Override
	public final boolean isSurvival() {
		return true;
	}

}
