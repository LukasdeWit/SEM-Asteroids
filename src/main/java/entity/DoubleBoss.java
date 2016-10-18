package entity;

import game.Game;

public class DoubleBoss extends Boss {
	private static final int DOUBLE_BOSS_LIVES = 5;
	private static final int SHOT_SPEED = 900;
	private static final int BULLETS = 2;

	public DoubleBoss(float x, float y, float dX, float dY, Game thisGame) {
		super(x, y, dX, dY, thisGame);
		this.setCurrentLives(DOUBLE_BOSS_LIVES);
		this.setShotSpeed(SHOT_SPEED);
		this.setBullets(BULLETS);
	}

}
