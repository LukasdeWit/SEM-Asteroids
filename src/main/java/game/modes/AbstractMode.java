package game.modes;

import game.Game;

public abstract class AbstractMode {
	Game thisGame;
	
	public AbstractMode (Game game) {
		thisGame = game;
	}
	
	public String toString() {
		return "";
	}
	
	public abstract boolean isCoop();
	
	public abstract boolean isBoss();
}
