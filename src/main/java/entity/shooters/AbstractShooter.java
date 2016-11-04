package entity.shooters;

import entity.AbstractEntity;
import entity.builders.BulletBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * Regulates the logic when entities shoot bullets.
 * @author Esmee
 *
 */
@Setter
@Getter
public abstract class AbstractShooter {
	private long lastShot;
	private BulletBuilder bBuilder;
	private final AbstractEntity owner;
	
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

}
