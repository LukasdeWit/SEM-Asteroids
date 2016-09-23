package entity;
import game.Game;
import game.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends AbstractEntity {
	/**
	 * Shape of Asteroid, either 0, 1 or 2.
	 */
	private final int shape;
	/**
	 * X coordinates of shape 0.
	 */
	private static final int[] XSHAPE0 =
		{ -2, 0, 2, 4, 3, 4, 1, 0, -2, -4, -4, -4 };
	/**
	 * Y coordinates of shape 0.
	 */
	private static final int[] YSHAPE0 = 
		{ -4, -2, -4, -2, 0, 2, 4, 4, 4, 2, 0, -2 };
	/**
	 * X coordinates of shape 1.
	 */
	private static final int[] XSHAPE1 = 
		{ -2, 0, 2, 4, 2, 4, 2, -1, -2, -4, -3, -4 };
	/**
	 * Y coordinates of shape 1.
	 */
	private static final int[] YSHAPE1 = 
		{ -4, -3, -4, -2, -1, 0, 3, 2, 3, 1, 0, -2 };
	/**
	 * X coordinates of shape 2.
	 */
	private static final int[] XSHAPE2 = 
		{ -2, 1, 4, 4, 2, 4, 2, 1, -2, -4, -4, -1 };
	/**
	 * Y coordinates of shape 2.
	 */
	private static final int[] YSHAPE2 = 
		{ -4, -4, -2, -1, 0, 2, 4, 3, 4, 1, -2, -2 };
	/**
	 * Radius of big asteroid in pixels.
	 */
	private static final float BIG_RADIUS = 20;
	/**
	 * Number of shapes.
	 */
	private static final double SHAPES = 3;
	/**
	 * Radius of medium asteroid in pixels.
	 */
	private static final float MEDIUM_RADIUS = 12;
	/**
	 * Score of big Asteroid.
	 */
	private static final int BIG_SCORE = 20;
	/**
	 * Radius of small asteroid in pixels.
	 */
	private static final float SMALL_RADIUS = 4;
	/**
	 * Score of medium Asteroid.
	 */
	private static final int MEDIUM_SCORE = 50;
	/**
	 * Score of small Asteroid.
	 */
	private static final int SMALL_SCORE = 100;
	/**
	 * Number of lines per shape.
	 */
	private static final int SHAPE_LINES = 12;
	/**
	 * Size multiplier.
	 */
	private static final float SIZE = .25f;
	/**
	 * Minimum speed of any asteroid in pixels per tick.
	 */
	private static final float MIN_SPEED = .5f;
	/**
	 * Number of asteroids after a split.
	 */
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
	 * Constructor for the Asteroid class.
	 * 
	 * @param x location of Asteroid along the X-axis.
	 * @param y location of Asteroid along the Y-axis.
	 * @param dX velocity of Asteroid along the X-axis.
	 * @param dY velocity of Asteroid along the Y-axis.
	 * @param thisGame Game the Asteroid exists in.
	 */
	public Asteroid(final float x, final float y,
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		final Random random = new Random();
		setRadius(BIG_RADIUS);
		shape = random.nextInt((int) SHAPES);
		while (speed() < MIN_SPEED) {
			setDX(getDX() * 2);
			setDY(getDY() * 2);
		}
	}
	
	/**
	 * Constructor for the Asteroid class, with radius.
	 * 
	 * @param x location of Asteroid along the X-axis.
	 * @param y location of Asteroid along the Y-axis.
	 * @param dX velocity of Asteroid along the X-axis.
	 * @param dY velocity of Asteroid along the Y-axis.
	 * @param radius - radius of the new Asteroid.
	 * @param thisGame Game the Asteroid exists in.
	 */
	public Asteroid(final float x, final float y,
			final float dX, final float dY, 
			final float radius, final Game thisGame) {
		this(x, y, dX, dY, thisGame);
		setRadius(radius);
	}

	/**
	 * Calculate new position of Asteroid.
	 * @param input - the pressed keys
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
				getThisGame().create(new Asteroid(getX(), getY(),
						(float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), 
						MEDIUM_RADIUS, getThisGame()));
			}
			getThisGame().addScore(BIG_SCORE);
		} else if (Float.compare(MEDIUM_RADIUS, getRadius()) == 0) {
			for (int i = 0; i < SPLIT; i++) {
				getThisGame().create(new Asteroid(getX(), getY(),
						(float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), 
						SMALL_RADIUS, getThisGame()));
			}
			getThisGame().addScore(MEDIUM_SCORE);
		} else {
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
	public final void draw(final GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(1);
		double[] xShape = new double[SHAPE_LINES];
		double[] yShape = new double[SHAPE_LINES];
		if (shape == 0) {
			for (int i = 0; i < SHAPE_LINES; i++) {
				xShape[i] = XSHAPE0[i] * (getRadius() * SIZE) + getX();
				yShape[i] = YSHAPE0[i] * (getRadius() * SIZE) + getY();
			}
		} else if (shape == 1) {
			for (int i = 0; i < SHAPE_LINES; i++) {
				xShape[i] = XSHAPE1[i] * (getRadius() * SIZE) + getX();
				yShape[i] = YSHAPE1[i] * (getRadius() * SIZE) + getY();
			}
		} else if (shape == 2) {
			for (int i = 0; i < SHAPE_LINES; i++) {
				xShape[i] = XSHAPE2[i] * (getRadius() * SIZE) + getX();
				yShape[i] = YSHAPE2[i] * (getRadius() * SIZE) + getY();
			}
		}
		gc.strokePolygon(xShape, yShape, SHAPE_LINES);
	}
}
