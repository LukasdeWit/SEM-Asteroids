package entity;

import game.Game;

/**
 * Class representing one of a pair of bosses.
 * 
 * @author Dario
 *
 */
public class DoubleBoss extends Boss {
	private static final int DOUBLE_BOSS_LIVES = 7;
	private static final int SHOT_SPEED = 500;
	private static final int BULLETS = 3;

	/**
	 * Constructor for DoubleBoss.
	 * 
	 * @param x
	 * @param y
	 * @param dX
	 * @param dY
	 * @param thisGame
	 */
	public DoubleBoss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		this.setCurrentLives(DOUBLE_BOSS_LIVES);
		this.setShotSpeed(SHOT_SPEED);
		this.setBullets(BULLETS);
	}

}
