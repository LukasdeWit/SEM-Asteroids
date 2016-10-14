package entity.builders;

import entity.AbstractEntity;
import entity.Bullet;
import game.Game;

/**
 * A builder class that can build bullets.
 * 
 * @author Lukas
 *
 */
public class BulletBuilder implements EntityBuilder {

	private Bullet bullet;
	
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

	@Override
	public final AbstractEntity getResult() {
		Bullet temp = bullet.shallowCopy();
		temp.setBirthTime(System.currentTimeMillis());
		temp.setShot(true);
		return temp;
	}

}
