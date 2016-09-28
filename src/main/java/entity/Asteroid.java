package entity;
import java.util.List;
import java.util.Random;

import game.Game;
import game.Launcher;
import game.Logger;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends AbstractEntity {
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
	private static final float MEDIUM_RADIUS = 12;
	private static final int BIG_SCORE = 20;
	private static final float SMALL_RADIUS = 4;
	private static final int MEDIUM_SCORE = 50;
	private static final int SMALL_SCORE = 100;
	private static final float SIZE = .25f;
	private static final float MIN_SPEED = .5f;
	private static final int SPLIT = 2;
	private static final float WIDTH = 4;

	/**
	 * Constructor for the Asteroid class.
	 * 
	 * @param x location of Asteroid along the X-axis.
	 * @param y location of Asteroid along the Y-axis.
	 * @param dX velocity of Asteroid along the X-axis.
	 * @param dY velocity of Asteroid along the Y-axis.
	 */
	public Asteroid(final float x, final float y,
			final float dX, final float dY) {
		super(x, y, dX, dY);
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
	 */
	public Asteroid(final float x, final float y,
			final float dX, final float dY, 
			final float radius) {
		this(x, y, dX, dY);
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
			Game.getInstance().destroy(this);
			Game.getInstance().destroy(e2);
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
				Game.getInstance().create(new Asteroid(getX(), getY(),
						(float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), 
						MEDIUM_RADIUS));
			}
			Game.getInstance().addScore(BIG_SCORE);
		} else if (Float.compare(MEDIUM_RADIUS, getRadius()) == 0) {
			for (int i = 0; i < SPLIT; i++) {
				Game.getInstance().create(new Asteroid(getX(), getY(),
						(float) (getDX() + Math.random() - .5),
						(float) (getDY() + Math.random() - .5), 
						SMALL_RADIUS));
			}
			Game.getInstance().addScore(MEDIUM_SCORE);
		} else {
			Game.getInstance().addScore(SMALL_SCORE);
		}
		Particle.explosion(getX(), getY(), Game.getInstance());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void draw() {
		final Group group = new Group();
		for (final float[] f : SHAPES[shape]) {
			final Line l = new Line(f[0] * (getRadius() * SIZE), f[1] * (getRadius() * SIZE), 
					f[2] * (getRadius() * SIZE), f[1 + 2] * (getRadius() * SIZE));
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(WIDTH * SIZE);
			group.getChildren().add(l);
		}
		group.setTranslateX(getX());
		group.setTranslateY(getY());
		Launcher.getRoot().getChildren().add(group);
	}
}
