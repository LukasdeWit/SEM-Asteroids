package game.modes;

import game.Game;

/**
 * Class that represents coop boss mode.
 * @author Esmee
 *
 */
public class CoopBossMode extends AbstractMode {

	/**
	 * Constructor for coop boss mode.
	 * @param game that this belongs to
	 */
	public CoopBossMode(final Game game) {
		super(game);
	}

	@Override
	public final String toString() {
		return "Boss Coop";
	}

	@Override
	public final boolean isCoop() {
		return true;
	}

	@Override
	public final boolean isBoss() {
		return true;
	}

}
