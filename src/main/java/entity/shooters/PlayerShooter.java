package entity.shooters;

import entity.Bullet;
import entity.Player;
import entity.builders.BulletBuilder;
import game.Audio;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that regulates logic when a Player shoots bullets.
 * @author Esmee
 *
 */
@Setter
@Getter
public class PlayerShooter extends AbstractShooter {
	private static final float BULLET_SPEED = 4;
	private static final float BULLET_SIZE = 1;
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
		final Player owner = (Player) getOwner();
		final double rotation = owner.getRotation();
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
		final Player owner = (Player) getOwner();

		final BulletBuilder bBuilder = getBBuilder();
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
	 * @return the maxBullets
	 */
	public static int getMaxBullets() {
		return MAX_BULLETS;
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
