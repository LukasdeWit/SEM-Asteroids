package game.modes;

import game.Game;

/**
 * Class that represents when the game isn't in any gamemode.
 * @author Esmee
 *
 */
public class NoneMode extends AbstractMode {
	/**
	 * Constructor for none mode.
	 * @param game this mode belongs to.
	 */
	public NoneMode(final Game game) {
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
		return false;
	}

	@Override
	public boolean isArcade() {
		return false;
	}

	@Override
	public boolean isSurvival() {
		return false;
	}
}
