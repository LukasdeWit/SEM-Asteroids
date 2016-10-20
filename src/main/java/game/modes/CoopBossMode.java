package game.modes;

import game.Game;

public class CoopBossMode extends AbstractMode {

	public CoopBossMode(Game game) {
		super(game);
	}

	@Override
	public String toString() {
		return "Boss Coop";
	}

	@Override
	public boolean isCoop() {
		return true;
	}

	@Override
	public boolean isBoss() {
		return true;
	}

}
