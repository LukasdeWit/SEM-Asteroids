package game.modes;

import game.Game;

/**
 * Class that represents single-player boss mode.
 * @author Esmee
 *
 */
public class BossMode extends AbstractMode {
	private static final int INT = 5;

	/**
	 * Constructor for bossmode.
	 * @param game this belongs to
	 */
	public BossMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Boss";
	}

	@Override
	public final boolean isCoop() {
		return false;
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
