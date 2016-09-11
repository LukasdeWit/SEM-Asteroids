package Entity;
import java.util.ArrayList;

import Game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that represents a UFO.
 */
public class UFO extends Entity {
	/**
	 * 1 if this UFO is going to the right, and 0 if going to the left.
	 */
	private int toRight;
	/**
	 * Time since previous change of direction in miliseconds.
	 */
	private long dirChangeTime;
	/**
	 * Time since previous shot in miliseconds.
	 */
	private long shotTime;
	/**
	 * X coordinates of UFO shape.
	 */
	private final double[] xShape0 = 
		{ 1.25, 2.5, 5, 2.5, -2.5, -5, -2.5, -1.25 };
	/**
	 * Y coordinates of UFO shape.
	 */
	private final double[] yShape0 = { -3.5, -0.75, 1, 3, 3, 1, -0.75, -3.5 };
	/**
	 * The points of horizontal lines of UFO shape. point 1 to point 6, etc.
	 */
	private final int[][] horLines = {{1, 6}, {2, 5}};
	/**
	 * Radius of UFO.
	 */
	private static final float BIG_RADIUS = 10;
	/**
	 * Number of paths.
	 */
	private static final double PATHS = 3;
	/**
	 * A quarter pi.
	 */
	private static final double QUARTER_PI = Math.PI / 4;
	/**
	 * Time between shots.
	 */
	private static final long SHOTTIME = 1000;
	/**
	 * Bullet speed multiplier.
	 */
	private static final float BULLET_SPEED = 2;
	/**
	 * Time between changes of direction in miliseconds.
	 */
	private static final long CHANGE_DIR_TIME = 2000;
	/**
	 * Number of lines per shape.
	 */
	private static final int SHAPE_LINES = 8;
	/**
	 * Heigth Multiplier.
	 */
	private static final float SIZE = .20f;
	/**
	 * Score of big UFO.
	 */
	private static final int BIG_SCORE = 200;
	/**
	 * Extra score of big UFO.
	 */
	private static final int SMALL_SCORE = 1000;

	/**
	 * Constructor for UFO class.
	 * 
	 * @param x
	 *            position of UFO along the X-axis
	 * @param y
	 *            position of UFO along the Y-axis
	 * @param dX
	 *            velocity of UFO along the X-axis
	 * @param dY
	 *            velocity of UFO along the Y-axis
	 * @param thisGame
	 *            game this ufo is placed in
	 */
	public UFO(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(BIG_RADIUS);
		dirChangeTime = System.currentTimeMillis();
		shotTime = dirChangeTime;
		int nextToRight = 0;
		if (x > (thisGame.getScreenX() / 2)) {
			nextToRight = 1;
		}
		setPath(nextToRight, (int) (Math.random() * PATHS));
	}

	/**
	 * Set UFO path.
	 * 
	 * @param toRight - Direction of UFO
	 * @param path - Low, mid or high path
	 */
	public final void setPath(final int toRight, final int path) {
		this.toRight = toRight;
		setDirection((float) (toRight * Math.PI + (path - 1) * QUARTER_PI));
	}

	/**
	 * Set UFO path.
	 * @param path - Low, mid or high path
	 */
	public final void setPath(final int path) {
		setPath(toRight, path);
	}
	
	/**
	 * Set the direction of the UFO, so change the dX and dY using direction.
	 * @param direction - the direction in radians, 0 being right
	 */
	public final void setDirection(final float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	@Override
	public final void update(final ArrayList<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		checkEnd();
		wrapAround();
		changeDirection();
		shoot();
	}

	/**
	 * Makes the UFO shoot.
	 */
	private void shoot() {
		if (System.currentTimeMillis() - shotTime > SHOTTIME) {
			float randomDir = (float) (Math.random() * 2 * Math.PI);
			Bullet newBullet = new Bullet(getX(), getY(), 
					getDX() + (float) Math.cos(randomDir) * BULLET_SPEED,
					getDY() - (float) Math.sin(randomDir) * BULLET_SPEED, 
					getThisGame());
			newBullet.setFriendly(false);
			getThisGame().create(newBullet);
			shotTime = System.currentTimeMillis();
		}
	}

	/**
	 * Destroy this if it's outside the screen.
	 */
	private void checkEnd() {
		if (getX() > getThisGame().getScreenX() || getX() < 0) {
			getThisGame().destroy(this);
		}
	}

	/**
	 * Change the ufo direction randomly at certain times in a random direction.
	 */
	private void changeDirection() {
		if (System.currentTimeMillis() - dirChangeTime > CHANGE_DIR_TIME) {
			dirChangeTime = System.currentTimeMillis();
			setPath((int) (Math.random() * PATHS));
		}
	}

	@Override
	public final void draw(final GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(.5);
		double[] xShape = new double[SHAPE_LINES];
		double[] yShape = new double[SHAPE_LINES];
		for (int i = 0; i < SHAPE_LINES; i++) {
			xShape[i] = xShape0[i] * (getRadius() * SIZE) + getX();
			yShape[i] = yShape0[i] * (getRadius() * SIZE) + getY();
		}
		gc.strokePolygon(xShape, yShape, SHAPE_LINES);
		for (int i = 0; i < 2; i++) {
			gc.strokeLine(xShape[horLines[i][1]], yShape[horLines[i][1]], 
					xShape[horLines[i][0]], yShape[horLines[i][1]]);
		}
	}

	@Override
	public final void collide(final Entity e2) {
		int points = BIG_SCORE;
		if (getRadius() != BIG_RADIUS) {
			points = SMALL_SCORE;
		}
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			((Player) e2).die();
			getThisGame().addScore(points);
			getThisGame().destroy(this);
		} else if (e2 instanceof Bullet && ((Bullet) e2).getFriendly()) {
			getThisGame().destroy(e2);
			getThisGame().addScore(points);
			getThisGame().destroy(this);
		} else if (e2 instanceof Asteroid) {
			((Asteroid) e2).split();
			getThisGame().addScore(points);
			getThisGame().destroy(this);
		}
	}
}
