package entity;

import java.util.List;
import java.util.Random;

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

	public TeleBoss(float x, float y, float dX, float dY, Game thisGame) {
		super(x, y, dX, dY, thisGame);
		teleTime = System.currentTimeMillis();
	}

	/**
	 * 
	 */
	@Override
	public void update(final List<String> input) {
		teleport();
		shoot();
	}

	private void teleport() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - teleTime > TELEPORT_TIME) {
			teleTime = System.currentTimeMillis();
			Particle.explosion(getX(), getY(), getThisGame());
			Random random = new Random();
			setX((float) Math.random() * this.getThisGame().getScreenX());
			setY((float) Math.random() * this.getThisGame().getScreenY());
		}
	}
	
}
