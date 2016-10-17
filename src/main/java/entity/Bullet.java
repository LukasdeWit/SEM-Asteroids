package entity;
import java.util.List;

import display.DisplayEntity;
import game.Logger;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends AbstractEntity {
	private long birthTime;
	private boolean friendly;
	private AbstractEntity shooter;
	private int piercing = 1;
	private boolean shot;

	private static final long LIFETIME = 2000;
	private static final float RADIUS = 2;

	/**
	 * Constructor for the Bullet class.
	 */
	public Bullet() {
		super();
		setRadius(RADIUS);
		birthTime = 0;
		friendly = true;
		shot = false;
	}

	/**
	 * Calculate new position of Bullet.
	 *
	 * @param input the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		if (this.isShot()) {
			setX(getX() + getDX());
			setY(getY() + getDY());
			wrapAround();
			if (System.currentTimeMillis() - birthTime > LIFETIME) {
				getThisGame().destroy(this);
			}
		}
	}

	/**
	 * Get whether the bullet is friendly.
	 *
	 * @return boolean that is true when bullet is friendly
	 */
	public final boolean isFriendly() {
		return friendly;
	}

	/**
	 * Set whether the bullet is friendly.
	 *
	 * @param friendly value that is true when the bullet is friendly
	 */
	public final void setFriendly(final boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * Describes what happens when the bullet collides with entities.
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			if (piercing < 2) {
				getThisGame().destroy(this);
			} else {
				piercing--;
			}
			getThisGame().destroy(e2);
			Logger.getInstance().log("Asteroid was hit by a bullet.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onDeath() {
		//no-op
	}
	
	/**
	 * @return true if the bullet is shot, false otherwise.
	 */
	public final boolean isShot() {
		return this.shot;
	}

	/**
	 * DisplayText bullet on screen.
	 */
	@Override
	public final void draw() {
		DisplayEntity.bullet(this);
	}

	/**
	 * @return the player
	 */
	public final AbstractEntity getShooter() {
		return shooter;
	}

	/**
	 * @param player the player to set
	 */
	public final void setShooter(final AbstractEntity shooter) {
		this.shooter = shooter;
	}

	/**
	 * @param birthTime the birthTime to set
	 */
	public final void setBirthTime(final long birthTime) {
		this.birthTime = birthTime;
	}
	
	/**
	 * @param pierce - the amount of objects the bullet pierces.
	 */
	public final void setPierce(final int pierce) {
		this.piercing = pierce;
	}
	
	/**
	 * @return the amount of objects the bullet pierces
	 */
	public final int getPierce() {
		return this.piercing;
	}
	
	/**
	 * @param shot - true if the bullet is shot and moving, false otherwise
	 */
	public final void setShot(final boolean shot) {
		this.shot = shot;
	}
	
	/**
	 * @return a shallow copy of the current bullet, useful for making two entities.
	 */
	public final Bullet shallowCopy() {
		final Bullet bullet = new Bullet();
		bullet.setX(this.getX());
		bullet.setY(this.getY());
		bullet.setDX(this.getDX());
		bullet.setDY(this.getDY());
		bullet.setThisGame(this.getThisGame());
		bullet.setPierce(this.getPierce());
		bullet.setShooter(getShooter());
		bullet.setBirthTime(birthTime);
		bullet.setShot(isShot());
		bullet.setFriendly(isFriendly());
		return bullet;
	}
}