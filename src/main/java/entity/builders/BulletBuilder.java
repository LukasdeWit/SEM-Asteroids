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
	public void setDX(float dX) {
		bullet.setDX(dX);
	}

	@Override
	public void setDY(float dY) {
		bullet.setDY(dY);
	}

	@Override
	public void setThisGame(Game thisGame) {
		bullet.setThisGame(thisGame);
	}

	@Override
	public AbstractEntity getResult() {
		Bullet temp = bullet.shallowCopy();
		temp.setShot(true);
		return temp;
	}

}
