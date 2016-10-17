package entity.builders;

import entity.AbstractEntity;
import entity.Bullet;
import entity.Player;
import game.Game;

/**
 * A builder class that can build bullets.
 * 
 * @author Lukas
 *
 */
public class BulletBuilder implements EntityBuilder {

	private final Bullet bullet;
	
	/**
	 * Constructor for the BulletBuilder class.
	 */
	public BulletBuilder() {
		this.bullet = new Bullet();
	}
	
	@Override
	public final void setX(final float x) {
		bullet.setX(x);
	}

	@Override
	public final void setY(final float y) {
		bullet.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		bullet.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		bullet.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		bullet.setThisGame(thisGame);
	}
	
	/**
	 * @param pierce - the amount of objects the bullet pierces.
	 */
	public final void setPierce(final int pierce) {
		bullet.setPierce(pierce);
	}
	
	/**
	 * @param friendly - true if the bullet can't hurt the player, false if it can.
	 */
	public final void setFriendly(final boolean friendly) {
		bullet.setFriendly(friendly);
	}
	
	/**
	 * @param radius - the radius of the bullet.
	 */
	public final void setRadius(final float radius) {
		bullet.setRadius(radius);
	}
	
	/**
	 * @param player - the player that shot the bullet.
	 */
	public final void setPlayer(final Player player) {
		bullet.setPlayer(player);
	}

	@Override
	public final AbstractEntity getResult() {
		final Bullet temp = bullet.shallowCopy();
		temp.setBirthTime(System.currentTimeMillis());
		temp.setShot(true);
		return temp;
	}

}