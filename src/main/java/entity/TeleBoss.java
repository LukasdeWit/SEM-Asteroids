package entity;

import java.util.List;

import game.Game;

/**
 * Class representing a randomly teleporting boss variant.
 * 
 * @author Dario
 *
 */
public class TeleBoss extends Boss {
	private long teleTime;
	private static final long TELEPORT_TIME = 1800;

	/**
	 * The constructor for DoubleBoss.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param dX speed in x-direction
	 * @param dY speed in y-direction
	 * @param thisGame the game
	 */
	public TeleBoss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		teleTime = System.currentTimeMillis();
	}

	/**
	 * The update method for TeleBoss.
	 */
	@Override
	public final void update(final List<String> input) {
		teleport();
		shoot();
	}

	/**
	 * The method that teleports a TeleBoss.
	 */
	private void teleport() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - teleTime > TELEPORT_TIME) {
			teleTime = System.currentTimeMillis();
			Particle.explosion(getX(), getY(), getThisGame());
			setX((float) Math.random() * this.getThisGame().getScreenX());
			setY((float) Math.random() * this.getThisGame().getScreenY());
		}
	}
	
}
