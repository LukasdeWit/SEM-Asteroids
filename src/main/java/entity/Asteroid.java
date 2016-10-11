package entity;
import display.DisplayEntity;
import game.Game;
import game.Logger;

import java.util.List;
import java.util.Random;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends AbstractEntity {
	private int shape;

	private static final int SHAPES = 3;
	private static final float BIG_RADIUS = 20;
	private static final float MEDIUM_RADIUS = 12;
	private static final int BIG_SCORE = 20;
	private static final float SMALL_RADIUS = 4;
	private static final int MEDIUM_SCORE = 50;
	private static final int SMALL_SCORE = 100;
	private static final float MIN_SPEED = .5f;
	private static final int SPLIT = 2;

	/**
	 * Constructor for the Asteroid class.
	 *
	 * @param x        location of Asteroid along the X-axis.
	 * @param y        location of Asteroid along the Y-axis.
	 * @param dX       velocity of Asteroid along the X-axis.
	 * @param dY       velocity of Asteroid along the Y-axis.
	 * @param thisGame Game the AbstractEntity exists in.
	 */
	public Asteroid(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		final Random random = new Random();
		setRadius(BIG_RADIUS);
		shape = random.nextInt(SHAPES);
		while (speed() < MIN_SPEED) {
			setDX(getDX() * 2);
			setDY(getDY() * 2);
		}
	}

	/**
	 * Constructor for the Asteroid class, with radius.
	 *
	 * @param x        location of Asteroid along the X-axis.
	 * @param y        location of Asteroid along the Y-axis.
	 * @param dX       velocity of Asteroid along the X-axis.
	 * @param dY       velocity of Asteroid along the Y-axis.
	 * @param radius   radius of the new Asteroid.
	 * @param thisGame Game the asteroid exists in.
	 */
	public Asteroid(final float x, final float y, final float dX, final float dY, final float radius,
					final Game thisGame) {
		this(x, y, dX, dY, thisGame);
		setRadius(radius);
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
		if (Float.compare(BIG_RADIUS, getRadius()) == 0) {
			for (int i = 0; i < SPLIT; i++) {
				getThisGame().create(new Asteroid(getX(), getY(), (float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), MEDIUM_RADIUS, getThisGame()));
			}
			getThisGame().addScore(BIG_SCORE);
		} else if (Float.compare(MEDIUM_RADIUS, getRadius()) == 0) {
			for (int i = 0; i < SPLIT; i++) {
				getThisGame().create(new Asteroid(getX(), getY(), (float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), SMALL_RADIUS, getThisGame()));
			}
			getThisGame().addScore(MEDIUM_SCORE);
		} else {
			getThisGame().addScore(SMALL_SCORE);
		}
		Particle.explosion(getX(), getY(), getThisGame());
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
}
