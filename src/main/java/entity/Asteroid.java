package entity;
import java.util.List;

import display.DisplayEntity;
import entity.builders.AsteroidBuilder;
import game.Audio;
import game.Logger;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends AbstractEntity {
	private int shape;

	private static final float BIG_RADIUS = 20;
	private static final float MEDIUM_RADIUS = 12;
	private static final int BIG_SCORE = 20;
	private static final float SMALL_RADIUS = 4;
	private static final int MEDIUM_SCORE = 50;
	private static final int SMALL_SCORE = 100;
	private static final int SPLIT = 2;
	/**
	 * The converted size for big asteroids in survival mode.
	 */
	private static final int SURVIVAL_CONVERTED_SIZE_BIG = 4;
	/**
	 * The converted size for medium asteroids in survival mode.
	 */
	private static final int SURVIVAL_CONVERTED_SIZE_MEDIUM = 2;
	/**
	 * The converted size for small asteroids in survival mode.
	 */
	private static final int SURVIVAL_CONVERTED_SIZE_SMALL = 1;

	/**
	 * Empty constructor for the Asteroid class.
	 */
	public Asteroid() {
		super();
		setRadius(BIG_RADIUS);
	}

	/**
	 * Calculate new position of Asteroid.
	 *
	 * @param input the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
	}

	/**
	 * Behaviour when Asteroid collides with entities.
	 *
	 * @param e2 entity this asteroid collided with
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Bullet) {
			getThisGame().destroy(this);
			getThisGame().destroy(e2);
			Logger.getInstance().log("Asteroid was hit by a bullet.");
		}
		//this is already done in Bullet.
	}

	/**
	 * on death split asteroid into 2 small ones,
	 * or if it's too small destroy it.
	 */
	@Override
	public final void onDeath() {
		final AsteroidBuilder aBuilder = new AsteroidBuilder();
		aBuilder.setThisGame(getThisGame());
		aBuilder.setX(getX());
		aBuilder.setY(getY());
		
		if (Float.compare(BIG_RADIUS, getRadius()) == 0) {
			aBuilder.setRadius(MEDIUM_RADIUS);
			for (int i = 0; i < SPLIT; i++) {
				aBuilder.setDX((float) (getDX() + Math.random() - .5));
				aBuilder.setDY((float) (getDY() + Math.random() - .5));
				getThisGame().create(aBuilder.getResult());
			}
			getThisGame().getAudio().playMultiple(Audio.LARGEEXPLOSION);
			getThisGame().addScore(BIG_SCORE);
		} else if (Float.compare(MEDIUM_RADIUS, getRadius()) == 0) {
			aBuilder.setRadius(SMALL_RADIUS);
			for (int i = 0; i < SPLIT; i++) {
				aBuilder.setDX((float) (getDX() + Math.random() - .5));
				aBuilder.setDY((float) (getDY() + Math.random() - .5));
				getThisGame().create(aBuilder.getResult());
			}
			getThisGame().getAudio().playMultiple(Audio.MEDIUMEXPLOSION);
			getThisGame().addScore(MEDIUM_SCORE);
		} else {
			getThisGame().getAudio().playMultiple(Audio.SMALLEXPLOSION);
			getThisGame().addScore(SMALL_SCORE);
		}
		Particle.explosion(getX(), getY(), getThisGame());
	}
	
	/**
	 * Returns the converted size for survival mode.
	 * @return 4 for big asteroids, 2 for medium and 1 for small.
	 */
	public final int getSurvivalSize() {
		if (Float.compare(BIG_RADIUS, getRadius()) == 0) {
			return SURVIVAL_CONVERTED_SIZE_BIG;
		} else if (Float.compare(MEDIUM_RADIUS, getRadius()) == 0) {
			return SURVIVAL_CONVERTED_SIZE_MEDIUM;
		} else {
			return SURVIVAL_CONVERTED_SIZE_SMALL;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void draw() {
		DisplayEntity.asteroid(this);
	}

	/**
	 * @return the shape
	 */
	public final int getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public final void setShape(final int shape) {
		this.shape = shape;
	}
	
	/**
	 * @return the bigRadius
	 */
	public static float getBigRadius() {
		return BIG_RADIUS;
	}

	/**
	 * @return the mediumRadius
	 */
	public static float getMediumRadius() {
		return MEDIUM_RADIUS;
	}

	/**
	 * @return the smallRadius
	 */
	public static float getSmallRadius() {
		return SMALL_RADIUS;
	}
	
	/**
	 * @return a shallow copy of the Asteroid, useful for making two copies
	 */
	public final Asteroid shallowCopy() {
		final Asteroid temp = new Asteroid();
		temp.setX(this.getX());
		temp.setY(this.getY());
		temp.setDX(this.getDX());
		temp.setDY(this.getDY());
		temp.setThisGame(this.getThisGame());
		temp.setRadius(this.getRadius());
		temp.setShape(this.getShape());
		return temp;
	}
}
