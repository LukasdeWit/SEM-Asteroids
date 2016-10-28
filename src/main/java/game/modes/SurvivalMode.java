package game.modes;

import game.Game;

/**
 * Survival mode.
 * @author Esmee
 *
 */
public class SurvivalMode extends AbstractMode {
	private static final int INT = 3;

	/**
	 * Constructor for survival mode.
	 * @param game this mode belongs to
	 */
	public SurvivalMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Survival";
	}

	@Override
	public final boolean isCoop() {
		return false;
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
	
	@Override
	public final int toInt() {
		return INT;
	}
}
