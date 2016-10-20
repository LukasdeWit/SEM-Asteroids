package game.modes;

import game.Game;

public class CoopArcadeMode extends AbstractMode {

	public CoopArcadeMode(Game game) {
		super(game);
	}

	@Override
	public String toString() {
		return "Arcade Coop";
	}

	@Override
	public boolean isCoop() {
		return true;
	}

	@Override
	public boolean isBoss() {
		return false;
	}

}
