package entity;

import java.util.List;
import java.util.Random;

import display.DisplayEntity;
import entity.shooters.SaucerShooter;
import game.Audio;
import game.Game;
import game.Logger;

/**
 * Class that represents a Saucer.
 */
public class Saucer extends AbstractEntity {
	private int toRight;
	private final Random random;
	private long dirChangeTime;
	private final SaucerShooter shooter;

	private static final float SMALL_RADIUS = 5;
	private static final float BIG_RADIUS = 10;

	private static final double PATHS = 3;
	private static final double PATH_ANGLE = Math.PI / 4;
	private static final long CHANGE_DIR_TIME = 2000;

	private static final int BIG_SCORE = 200;
	private static final int SMALL_SCORE = 1000;


	/**
	 * Constructor for Saucer class.
	 *
	 * @param x        position of Saucer along the X-axis
	 * @param y        position of Saucer along the Y-axis
	 * @param dX       velocity of Saucer along the X-axis
	 * @param dY       velocity of Saucer along the Y-axis
	 * @param thisGame the game this particle belongs to
	 */
	public Saucer(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		random = new Random();
		setRadius(BIG_RADIUS);
		dirChangeTime = System.currentTimeMillis();
		int nextToRight = 0;
		if (x > (getThisGame().getScreenX() / 2)) {
			nextToRight = 1;
		}
		setPath(nextToRight, random.nextInt((int) PATHS));
		shooter = new SaucerShooter(this);
	}

	/**
	 * Set Saucer path.
	 *
	 * @param toRight - Direction of Saucer
	 * @param path    - Low, mid or high path
	 */
	public final void setPath(final int toRight, final int path) {
		this.toRight = toRight;
		setDirection((float) (toRight * Math.PI + (path - 1) * PATH_ANGLE));
	}

	/**
	 * Set Saucer path.
	 *
	 * @param path - Low, mid or high path
	 */
	public final void setPath(final int path) {
		setPath(toRight, path);
	}

	/**
	 * Set the direction of the Saucer.
	 *
	 * @param direction - the direction in radians, 0 being right
	 */
	public final void setDirection(final float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	/**
	 * Calculate new position of UFO.
	 *
	 * @param input the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		if (!checkEnd()) {
			wrapAround();
		}
		changeDirection();
		shooter.shoot();
		if (getThisGame().getGamestate().getOngoingGameState() == getThisGame().getGamestate().getState()) {
			if (isSmall()) {
				getThisGame().getAudio().play(Audio.UFOSMALL);
			} else {
				getThisGame().getAudio().play(Audio.UFOBIG);
			}
		}
	}

	/**
	 * Destroy this if it's outside the screen.
	 * @return true if it is the end
	 */
	private boolean checkEnd() {
		if (getX() > getThisGame().getScreenX() || getX() < 0) {
			getThisGame().destroy(this);
			return true;
		}
		return false;
	}

	/**
	 * Change the ufo direction randomly at certain times in a random direction.
	 */
	private void changeDirection() {
		if (System.currentTimeMillis() - dirChangeTime > CHANGE_DIR_TIME) {
			dirChangeTime = System.currentTimeMillis();
			setPath(random.nextInt((int) PATHS));
		}
	}

	/**
	 * DisplayText UFO on screen.
	 */
	@Override
	public final void draw() {
		DisplayEntity.saucer(this);
	}

	/**
	 * Describes what happens when UFO collides with entities.
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			((Player) e2).onHit();
			getThisGame().destroy(this);
			Logger.getInstance().log("Player was hit by a saucer.");
		} else if (e2 instanceof Bullet && ((Bullet) e2).isFriendly() || e2 instanceof Asteroid) {
			getThisGame().destroy(e2);
			getThisGame().destroy(this);
			Logger.getInstance().log("Saucer was hit.");
		}
	}

	/**
	 * kills this Saucer, adds the points to score and explodes.
	 */
	@Override
	public final void onDeath() {
		if (getX() < getThisGame().getScreenX() && getX() > 0) {
			int points;
			if (Float.compare(SMALL_RADIUS, getRadius()) >= 0) {
				points = SMALL_SCORE;
				getThisGame().getAudio().playMultiple(Audio.SMALLEXPLOSION);
			} else {
				points = BIG_SCORE;
				getThisGame().getAudio().playMultiple(Audio.MEDIUMEXPLOSION);
			}
			getThisGame().addScore(points);
			Particle.explosion(getX(), getY(), getThisGame());
		}
		if (isSmall()) {
			getThisGame().getAudio().stop(Audio.UFOSMALL);
		} else {
			getThisGame().getAudio().stop(Audio.UFOBIG);
		}
	}

	/**
	 * Getter for small radius.
	 *
	 * @return small saucer radius
	 */
	public static float getSmallRadius() {
		return SMALL_RADIUS;
	}
	
	/**
	 * Check the size of the ufo.
	 * @return true if small
	 */
	public final boolean isSmall() {
		return getRadius() == SMALL_RADIUS;
	}

	/**
	 * @return the bigRadius
	 */
	public static float getBigRadius() {
		return BIG_RADIUS;
	}

	/**
	 * @return the toRight
	 */
	public final int getToRight() {
		return toRight;
	}

	/**
	 * @param dirChangeTime the dirChangeTime to set
	 */
	public final void setDirChangeTime(final long dirChangeTime) {
		this.dirChangeTime = dirChangeTime;
	}

	/**
	 * @return the dirChangeTime
	 */
	public final long getDirChangeTime() {
		return dirChangeTime;
	}

	/**
	 * @return the smallScore
	 */
	public static int getSmallScore() {
		return SMALL_SCORE;
	}

	/**
	 * @return the bigScore
	 */
	public static int getBigScore() {
		return BIG_SCORE;
	}
	
	/**
	 * @return the saucershooter
	 */
	public final SaucerShooter getShooter() {
		return shooter;
	}
}
