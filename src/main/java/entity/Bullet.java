package entity;
import display.DisplayEntity;
import game.Logger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class that stores the information for a bullet.
 */
@Setter
@Getter
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
	 * DisplayText bullet on screen.
	 */
	@Override
	public final void draw() {
		DisplayEntity.bullet(this);
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
		bullet.setPiercing(this.getPiercing());
		bullet.setShooter(getShooter());
		bullet.setBirthTime(birthTime);
		bullet.setShot(isShot());
		bullet.setFriendly(isFriendly());
		bullet.setRadius(this.getRadius());
		return bullet;
	}
}