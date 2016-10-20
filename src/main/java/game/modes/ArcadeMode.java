package game.modes;

import game.Game;

public class ArcadeMode extends AbstractMode {
	public ArcadeMode(Game game) {
		super(game);
	}

	@Override
	public String toString() {
		return "Single Player Arcade";
	}

	@Override
	public boolean isCoop() {
		return false;
	}

	@Override
	public boolean isBoss() {
		return false;
	}
}
