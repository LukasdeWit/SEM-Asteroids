package entity;
import java.util.List;
import java.util.Random;

import game.Game;
import game.Logger;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends AbstractEntity {
	/**
	 * Shape of Asteroid, either 0, 1 or 2.
	 */
	private final int shape;
	private static final float[][][] SHAPES = {
		{
			{-2, -4, 0, -2},
			{0, -2, 2, -4},
			{2, -4, 4, -2},
			{4, -2, 3, 0},
			{3, 0, 4, 2},
			{4, 2, 1, 4},
			{1, 4, -2, 4},
			{-2, 4, -4, 2},
			{-4, 2, -4, -2},
			{-4, -2, -2, -4}
		},
		{
			{-2, -4, 0, -3},
			{0, -3, 2, -4},
			{2, -4, 4, -2},
			{4, -2, 2, -1},
			{2, -1, 4, 0},
			{4, 0, 2, 3},
			{2, 3, -1, 2},
			{-1, 2, -2, 3},
			{-2, 3, -4, 1},
			{-4, 1, -3, 0},
			{-3, 0, -4, -2},
			{-4, -2, -2, -4}
		},
		{
			{-2, -4, 1, -4},
			{1, -4, 4, -2},
			{4, -2, 4, -1},
			{4, -1, 2, 0},
			{2, 0, 4, 2},
			{4, 2, 2, 4},
			{2, 4, 1, 3},
			{1, 3, -2, 4},
			{-2, 4, -4, 1},
			{-4, 1, -4, -2},
			{-4, -2, -1, -2},
			{-1, -2, -2, -4}
		}
	};
	private static final float BIG_RADIUS = 20;
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
	private static final float WIDTH = 4;

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
		shape = random.nextInt(SHAPES.length);
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
	 * {@inheritDoc}
	 */
	@Override
	public final void draw() {
		
		Group group = new Group();
		for (float[] f : SHAPES[shape]) {
			Line l = new Line(f[0] * (getRadius() * SIZE), f[1] * (getRadius() * SIZE), 
					f[2] * (getRadius() * SIZE), f[1 + 2] * (getRadius() * SIZE));
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(WIDTH * SIZE);
			group.getChildren().add(l);
		}
		group.setTranslateX(getX());
		group.setTranslateY(getY());
		
		getThisGame().getRoot().getChildren().add(group);
	}
}
