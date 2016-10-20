package game.modes;

import game.Game;

public class NoneMode extends AbstractMode {

	public NoneMode(Game game) {
		super(game);
	}

	@Override
	public String toString() {
		return "None";
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
