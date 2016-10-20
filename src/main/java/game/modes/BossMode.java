package game.modes;

import game.Game;

/**
 * Class that represents single-player boss mode.
 * @author Esmee
 *
 */
public class BossMode extends AbstractMode {
	/**
	 * Constructor for bossmode.
	 * @param game this belongs to
	 */
	public BossMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "None";
	}

	@Override
	public final boolean isCoop() {
		return false;
	}

	@Override
	public final boolean isBoss() {
		return true;
	}
}
