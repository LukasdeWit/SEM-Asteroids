package entity.builders;

import entity.AbstractEntity;
import entity.Player;
import entity.Powerup;
import game.Game;

/**
 * A builder that builds new Powerup objects.
 * 
 * @author Lukas
 *
 */
public class PowerupBuilder implements EntityBuilder {

	private final Powerup powerup;
	
	/**
	 * Constructor for the PowerupBuilder class.
	 */
	public PowerupBuilder() {
		powerup = new Powerup();
	}
	
	@Override
	public final void setX(final float x) {
		powerup.setX(x);
	}

	@Override
	public final void setY(final float y) {
		powerup.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		powerup.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		powerup.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		powerup.setThisGame(thisGame);
	}
	
	/**
	 * @param type the type of the powerup
	 */
	public final void setType(final int type) {
		powerup.setType(type);
	}
	
	/**
	 * @param player the player the powerup belongs to
	 */
	public final void setPlayer(final Player player) {
		powerup.setPlayer(player);
	}

	/**
	 * @param startTime the start time of the powerup
	 */
	public final void setStartTime(final long startTime) {
		powerup.setStartTime(startTime);
	}
	
	/**
	 * @param pickupTime the pickup time of the powerup
	 */
	public final void setPickupTime(final long pickupTime) {
		powerup.setPickupTime(pickupTime);
	}
	
	@Override
	public final AbstractEntity getResult() {
		return powerup.shallowCopy();
	}

}
