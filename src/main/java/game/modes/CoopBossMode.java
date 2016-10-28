package game.modes;

import game.Game;

/**
 * Class that represents coop boss mode.
 * @author Esmee
 *
 */
public class CoopBossMode extends AbstractMode {
	private static final int INT = 6;

	/**
	 * Constructor for coop boss mode.
	 * @param game that this belongs to
	 */
	public CoopBossMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Coop Boss";
	}

	@Override
	public final boolean isCoop() {
		return true;
	}

	@Override
	public final boolean isBoss() {
		return true;
	}

	@Override
	public final boolean isArcade() {
		return false;
	}

	@Override
	public final boolean isSurvival() {
		return false;
	}
	
	@Override
	public final int toInt() {
		return INT;
	}
}
