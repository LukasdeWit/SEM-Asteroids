package entity.shooters;

import entity.AbstractEntity;
import entity.Bullet;
import entity.Saucer;
import entity.builders.BulletBuilder;
import game.Game;

import java.util.Random;

/**
 * Class that regulates logic when saucers shoot bullets.
 * @author Esmee
 *
 */
public class SaucerShooter extends AbstractShooter {
	private static final int PIERCING = 1;
	private static final long SHOT_TIME = 1000;
	private static final long LESS_SHOT = 50;
	private static final float MAX_ACCURACY = 10;
	private static final float BULLET_SPEED = 4;
	
	private final Random random;

	/**
	 * Constructor for saucer.
	 * @param saucer this belongs to
	 */
	public SaucerShooter(final Saucer saucer) {
		super(saucer);
		getBBuilder().setThisGame(saucer.getThisGame());
		getBBuilder().setPierce(PIERCING);
		getBBuilder().setFriendly(false);
		random = new Random();
	}

	
	/**
	 * Makes the Saucer shoot.
	 */
	@Override
	public final void shoot() {
		final Game thisGame = getOwner().getThisGame();
		
		if (thisGame.getPlayer() == null) {
			return;
		}
		if (thisGame.getPlayer().invincible()) {
			setLastShot(System.currentTimeMillis());
		} else {
			final BulletBuilder bBuilder = getBBuilder();
			bBuilder.coordinatesOfShooter();
			
			if (((Saucer) getOwner()).isSmall()) {
				if (System.currentTimeMillis() - getLastShot() > smallShotTime()) {
	                final float shotDir = smallShotDir();
	                shootBullet(shotDir);
				}
            } else {
                if (System.currentTimeMillis() - getLastShot() > SHOT_TIME) {
					final float shotDir = (float) (Math.random() * 2 * Math.PI);
					shootBullet(shotDir);
				}
			}
		}
	}
	
	/**
	 * Shoot bullet, given a direction.
	 * @param shotDir direction you want the saucer to shoot in
	 */
	private void shootBullet(final float shotDir) {
		final BulletBuilder bBuilder = getBBuilder();
		bBuilder.setDX((float) Math.cos(shotDir) * BULLET_SPEED);
		bBuilder.setDY((float) Math.sin(shotDir) * BULLET_SPEED);
		final Bullet newBullet = (Bullet) bBuilder.getResult();
		
		getOwner().getThisGame().create(newBullet);
		setLastShot(System.currentTimeMillis());
	}
	
	/**
	 * generates the shot direction of small saucer.
	 *
	 * @return direction in radians.
	 */
	private float smallShotDir() {
		final AbstractEntity owner = getOwner();
		final float playerX = owner.getThisGame().getPlayer().getX();
		final float playerY = owner.getThisGame().getPlayer().getY();
		float accuracy = owner.getThisGame().getScorecounter().smallSaucerDifficulty();
		if (accuracy > MAX_ACCURACY) {
			accuracy = MAX_ACCURACY;
		}
		//0 is completely random, 10 is perfect.
		final float randomRange = (float) (Math.PI * ((MAX_ACCURACY - accuracy) / MAX_ACCURACY) * Math.random());
		//The angle of error.
		float straightDir;
		if (playerX > owner.getX()) {
			straightDir = (float) Math.atan((playerY - owner.getY()) / (playerX - owner.getX()));
		} else {
			straightDir = (float) (Math.PI + Math.atan((playerY - owner.getY()) / (playerX - owner.getX())));
		}
			//Straight direction from saucer to player in radians.
		final float errorRight = (float) (random.nextInt(2) * 2 - 1);
		//-1 is error left, 1 is error right.
		return straightDir + errorRight * randomRange;
	}
	
	/**
	 * The time between shots of the small Saucer,
	 * becomes more smaller when score is higher.
	 *
	 * @return shot time of small saucer
	 */
	private long smallShotTime() {
		final long score = getOwner().getThisGame().getScorecounter().smallSaucerDifficulty();
		if (score == 0) {
			return SHOT_TIME;
		} else if (score <= SHOT_TIME / (2 * LESS_SHOT)) {
			return SHOT_TIME - (LESS_SHOT * score);
		} else {
			return SHOT_TIME / 2;
		}
	}
}
