package entity.shooters;

import entity.Bullet;
import entity.Player;
import entity.builders.BulletBuilder;
import game.Audio;

/**
 * Class that regulates shooter activity of Player.
 * @author Esmee
 *
 */
public class PlayerShooter extends AbstractShooter {
	private static final float BULLET_SPEED = 4;
	private static final float BULLET_SIZE = 2;
    private static final double TRIPLE_SHOT_ANGLE = .1;
	private static final int MAX_BULLETS = 4;
	private static final long FIRE_RATE = 200;

    private int maxBullets;
	private int piercing;
	private float bulletSize;
	private boolean tripleShot;
	private double fireRate;

	/**
	 * Constructor for playershooter.
	 * @param player this belongs to
	 */
	public PlayerShooter(final Player player) {
		super(player);
		// Initialize the Bullet Builder
		setBBuilder(new BulletBuilder());
		getBBuilder().setPierce(piercing);
		getBBuilder().setFriendly(true);
		getBBuilder().setShooter(player);
		piercing = 1;
		maxBullets = MAX_BULLETS;
		bulletSize = BULLET_SIZE;
		fireRate = FIRE_RATE;
		tripleShot = false;
	}

	@Override
	public final void shoot() {
		fire();
	}
	
	/**
	 * Method to handle firing bullets.
	 */
	private void fire() {
		Player owner = (Player) getOwner();
		double rotation = owner.getRotation();
		if (System.currentTimeMillis() - getLastShot() > fireRate && owner.getThisGame().bullets(owner) < maxBullets) {
			fireBullet(rotation);
			if (tripleShot) {
				fireBullet(rotation - TRIPLE_SHOT_ANGLE);
				fireBullet(rotation + TRIPLE_SHOT_ANGLE);
			}
			setLastShot(System.currentTimeMillis());
			if (owner.isPlayerTwo()) {
				owner.getThisGame().getAudio().playMultiple(Audio.SHOOTING2);
			} else {
				owner.getThisGame().getAudio().playMultiple(Audio.SHOOTING);
			}
		}
	}

	/**
	 * fire a bullet in a direction.
	 * @param direction - the direction
	 */
	private void fireBullet(final double direction) {
		Player owner = (Player) getOwner();

		BulletBuilder bBuilder = getBBuilder();
		bBuilder.coordinatesOfShooter();
		bBuilder.setDX((float) (owner.getDX() / 2 + Math.cos(direction) * BULLET_SPEED));
		bBuilder.setDY((float) (owner.getDY() / 2 - Math.sin(direction) * BULLET_SPEED));
		bBuilder.setRadius(bulletSize);
		bBuilder.setThisGame(owner.getThisGame());
		bBuilder.setShooter(owner);
		bBuilder.setPierce(piercing);
		final Bullet b = (Bullet) bBuilder.getResult();
		
		owner.getThisGame().create(b);
	}
	

	/**
	 * @param maxBullets the maxBullets to set
	 */
	public final void setMaxBullets(final int maxBullets) {
		this.maxBullets = maxBullets;
	}

	/**
	 * @return the maxBullets
	 */
	public static int getMaxBullets() {
		return MAX_BULLETS;
	}
	
	/**
	 * @param piercing the piercing to set
	 */
	public final void setPiercing(final int piercing) {
		this.piercing = piercing;
	}

	/**
	 * @return the piercing
	 */
	public final int getPiercing() {
		return piercing;
	}
	

	/**
	 * @param bulletSize the bulletSize to set
	 */
	public final void setBulletSize(final float bulletSize) {
		this.bulletSize = bulletSize;
	}
	
	/**
	 * @return bulletSize
	 */
	public final float getCurrentBulletSize() {
		return bulletSize;
	}

	/**
	 * @return the bulletSize
	 */
	public static float getBulletSize() {
		return BULLET_SIZE;
	}
	
	/**
	 * @param tripleShot the tripleShot to set
	 */
	public final void setTripleShot(final boolean tripleShot) {
		this.tripleShot = tripleShot;
	}

	/**
	 * @return the tripleShot
	 */
	public final boolean isTripleShot() {
		return tripleShot;
	}
	
	/**
	 * @param fireRate the fireRate to set
	 */
	public final void setFireRate(final double fireRate) {
		this.fireRate = fireRate;
	}
	
	/**
	 * @return fireRate
	 */
	public final double getCurrentFireRate() {
		return fireRate;
	}

	/**
	 * @return the fireRate
	 */
	public static long getFireRate() {
		return FIRE_RATE;
	}
}
