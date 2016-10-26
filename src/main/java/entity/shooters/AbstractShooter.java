package entity.shooters;

import entity.AbstractEntity;
import entity.builders.BulletBuilder;

/**
 * Regulates the logic when entities shoot bullets.
 * @author Esmee
 *
 */
public abstract class AbstractShooter {
	private long lastShot;
	private BulletBuilder bBuilder;
	private AbstractEntity owner;
	
	/**
	 * Constructor for Abstracthooter.
	 * @param owner AbstractEntity this belongs to.
	 */
	public AbstractShooter(final AbstractEntity owner) {
		this.owner = owner;
		bBuilder = new BulletBuilder();
		bBuilder.setShooter(owner);
		lastShot = System.currentTimeMillis();
	}
	
	/**
	 * method that shoots a bullet.
	 */
	public abstract void shoot();
	
	/**
	 * @return the last time a shot was made
	 */
	public final long getLastShot() {
		return lastShot;
	}
	
	/**
	 * @param lastShot the last time a shot was made
	 */
	public final void setLastShot(final long lastShot) {
		this.lastShot = lastShot;
	}
	
	/**
	 * @return the bulletbuilder
	 */
	public final BulletBuilder getBBuilder() {
		return bBuilder;
	}
	
	/**
	 * @param bBuilder the new bulletbuilder
	 */
	public final void setBBuilder(final BulletBuilder bBuilder) {
		this.bBuilder = bBuilder;
	}
	
	/**
	 * @return the owner
	 */
	public final AbstractEntity getOwner() {
		return owner;
	}
	
	/**
	 * @param owner the new owner
	 */
	public final void setOwner(final AbstractEntity owner) {
		this.owner = owner;
	}
}
