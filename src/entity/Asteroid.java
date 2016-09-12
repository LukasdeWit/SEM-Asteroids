package entity;
import java.util.List;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends Entity {
	/**
	 * Shape of Asteroid, either 0, 1 or 2.
	 */
	private int shape;
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
	 * Constructor for the Asteroid class.
	 * 
	 * @param x
	 *            location of Asteroid along the X-axis.
	 * @param y
	 *            location of Asteroid along the Y-axis.
	 * @param dX
	 *            velocity of Asteroid along the X-axis.
	 * @param dY
	 *            velocity of Asteroid along the Y-axis.
	 * @param thisGame
	 *            Game the Asteroid exists in.
	 */
	public Asteroid(final float x, final float y,
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(BIG_RADIUS);
		shape = (int) (Math.random() * SHAPES);
	}

	/**
	 * Calculate new position of Asteroid.
	 * @param input - the pressed keys
	 */
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
	}

	/**
	 * Behaviour when Asteroid collides with entities.
	 */
	@Override
	public final void collide(final Entity e2) {
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			split();
			((Player) e2).die();
		} else if (e2 instanceof Bullet) {
			split();
			getThisGame().destroy(e2);
		}
	}

	/**
	 * Split asteroid into 2 small ones, or if it's too small destroy it.
	 */
	public final void split() {
		if (getRadius() == BIG_RADIUS) {
			getThisGame().addAsteroid(getX(), getY(), 
					(float) (getDX() + Math.random() - .5), 
					(float) (getDY() + Math.random() - .5), MEDIUM_RADIUS);
			getThisGame().addAsteroid(getX(), getY(), 
					(float) (getDX() + Math.random() - .5), 
					(float) (getDY() + Math.random() - .5), MEDIUM_RADIUS);
			getThisGame().addScore(BIG_SCORE);
			getThisGame().destroy(this);
		} else if (getRadius() == MEDIUM_RADIUS) {
			getThisGame().addAsteroid(getX(), getY(), 
					(float) (getDX() + Math.random() * 2 - 1), 
					(float) (getDY() + Math.random() - .5), SMALL_RADIUS);
			getThisGame().addAsteroid(getX(), getY(), 
					(float) (getDX() + Math.random() * 2 - 1), 
					(float) (getDY() + Math.random() - .5), SMALL_RADIUS);
			getThisGame().addScore(MEDIUM_SCORE);
			getThisGame().destroy(this);
		} else {
			getThisGame().addScore(SMALL_SCORE);
			getThisGame().destroy(this);
		}
	}

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
	
	/**
	 * Getter for shape.
	 * @return int describing the shape of the asteroid
	 */
	public final int getShape() {
		return shape;
	}
	
	/**
	 * Setter for shape.
	 * @param newShape int describing the shape of the asteroid
	 */
	public final void setShape(final int newShape) {
		shape = newShape;
	}
}
