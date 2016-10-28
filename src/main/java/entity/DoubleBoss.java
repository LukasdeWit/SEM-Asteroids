package entity;

import display.DisplayEntity;
import game.Game;

/**
 * Class representing one of a pair of bosses.
 * 
 * @author Dario
 *
 */
public class DoubleBoss extends BasicBoss {
	private static final int DOUBLE_BOSS_LIVES = 7;
	private static final int SHOT_SPEED = 600;
	private static final int BULLETS = 3;
	private static final int DOUBLE_RADIUS = 40;

	/**
	 * Constructor for DoubleBoss.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param dX speed in x-direction
	 * @param dY speed in y-direction
	 * @param thisGame the game
	 */
	public DoubleBoss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		this.setCurrentLives(DOUBLE_BOSS_LIVES);
		this.setShotSpeed(SHOT_SPEED);
		this.setBullets(BULLETS);
		this.setRadius(DOUBLE_RADIUS);
	}
	
	@Override
	public final void draw() {
		DisplayEntity.boss(this);
	}

}
