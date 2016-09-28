package entity;

import java.util.List;
import java.util.Random;

import game.Game;
import game.Launcher;
import game.Logger;
import game.Spawner;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Class that represents a Saucer.
 */
public class Saucer extends AbstractEntity {
	/**
	 * 1 if this Saucer is going to the right, and 0 if going to the left.
	 */
	private int toRight;
	/**
	 * Used to get random numbers.
	 */
	private final Random random;
	/**
	 * Time since previous change of direction in milliseconds.
	 */
	private long dirChangeTime;
	/**
	 * Time since previous shot in milliseconds.
	 */
	private long shotTime;	
	
	private static final double[][] SHAPE = {
			{1.25, -3.5, 2.5, -0.75},
			{2.5, -0.75, 5, 1},
			{5, 1, 2.5, 3},
			{2.5, 3, -2.5, 3},
			{-2.5, 3, -5, 1},
			{-5, 1, -2.5, -0.75},
			{-2.5, -0.75, -1.25, -3.5},
			{-1.25, -3.5, 1.25, -3.5},
			{2.5, -0.75, -2.5, -0.75},
			{5, 1, -5, 1}
	};
	/**
	 * Radius of small Saucer.
	 */
	private static final float SMALL_RADIUS = 5;
	/**
	 * Radius of Saucer.
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
	 * Bullet speed multiplier.
	 */
	private static final float BULLET_SPEED = 4;
	/**
	 * Time between changes of direction in milliseconds.
	 */
	private static final long CHANGE_DIR_TIME = 2000;
	/**
	 * Height Multiplier.
	 */
	private static final float SIZE = .20f;
	/**
	 * Score of big Saucer.
	 */
	private static final int BIG_SCORE = 200;
	/**
	 * Extra score of big Saucer.
	 */
	private static final int SMALL_SCORE = 1000;
	/**
	 * Time between shots of Saucer.
	 */
	private static final long SHOT_TIME = 1000;
	/**
	 * The amount of time by which the time between shots
	 * becomes shorter per difficulty step.
	 */
	private static final long LESS_SHOT = 50;
	/**
	 * Maximum accuracy.
	 */
	private static final float MAX_ACCURACY = 10;
	private static final float WIDTH = 4;

	/**
	 * Constructor for Saucer class.
	 * 
	 * @param x position of Saucer along the X-axis
	 * @param y position of Saucer along the Y-axis
	 * @param dX velocity of Saucer along the X-axis
	 * @param dY velocity of Saucer along the Y-axis
	 * @param thisGame game this ufo is placed in
	 */
	public Saucer(final float x, final float y,
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		random = new Random();
		setRadius(BIG_RADIUS);
		dirChangeTime = System.currentTimeMillis();
		shotTime = dirChangeTime;
		int nextToRight = 0;
		if (x > (thisGame.getScreenX() / 2)) {
			nextToRight = 1;
		}
		setPath(nextToRight, random.nextInt((int) PATHS));
	}

	/**
	 * Set Saucer path.
	 * 
	 * @param toRight - Direction of Saucer
	 * @param path - Low, mid or high path
	 */
	public final void setPath(final int toRight, final int path) {
		this.toRight = toRight;
		setDirection((float) (toRight * Math.PI + (path - 1) * QUARTER_PI));
	}

	/**
	 * Set Saucer path.
	 * @param path - Low, mid or high path
	 */
	public final void setPath(final int path) {
		setPath(toRight, path);
	}
	
	/**
	 * Set the direction of the Saucer, so change the dX and dY using direction.
	 * @param direction - the direction in radians, 0 being right
	 */
	public final void setDirection(final float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	/**
	 * Calculate new position of UFO.
	 * @param input - the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		checkEnd();
		wrapAround();
		changeDirection();
		shoot();
	}

	/**
	 * Makes the Saucer shoot.
	 */
	private void shoot() {
		if (getThisGame().getPlayer().invincible()) {
			shotTime = System.currentTimeMillis();
		} else {
			if (Float.compare(BIG_RADIUS, getRadius()) == 0
                    && System.currentTimeMillis() - shotTime > SHOT_TIME) {
                final float shotDir = (float) (Math.random() * 2 * Math.PI);

                final Bullet newBullet = new Bullet(getX(), getY(),
                        (float) Math.cos(shotDir) * BULLET_SPEED,
                        (float) Math.sin(shotDir) * BULLET_SPEED,
                        getThisGame());
                newBullet.setFriendly(false);
                getThisGame().create(newBullet);
                shotTime = System.currentTimeMillis();
            } else if (
            		System.currentTimeMillis() - shotTime > smallShotTime()) {
                final float shotDir = smallShotDir();

                final Bullet newBullet = new Bullet(getX(), getY(),
                        (float) Math.cos(shotDir) * BULLET_SPEED,
                        (float) Math.sin(shotDir) * BULLET_SPEED,
                        getThisGame());
                newBullet.setFriendly(false);
                getThisGame().create(newBullet);
                shotTime = System.currentTimeMillis();
            }
		}
	}

	/**
	 * generates the shot direction of small saucer.
	 * @return direction in radians.
	 */
	private float smallShotDir() {
		final float playerX = getThisGame().getPlayer().getX();
		final float playerY = getThisGame().getPlayer().getY();
		float accuracy = getThisGame().getScore()
				/ (float) Spawner.getDifficultyStep();
		if (accuracy > MAX_ACCURACY) {
			accuracy = MAX_ACCURACY;
		}
			//0 is completely random, 10 is perfect.
		final float randomRange = (float) (Math.PI
				* ((MAX_ACCURACY - accuracy) / MAX_ACCURACY) * Math.random());
			//The angle of error.
		float straightDir;
		if (playerX > getX()) {
			straightDir =
					(float) Math.atan((playerY - getY()) / (playerX - getX()));
		} else {
			straightDir = (float) (Math.PI
					+ Math.atan((playerY - getY()) / (playerX - getX())));
		}
		//straightDir = (float) -(Math.PI / 2); //debug.
			//Straight direction from saucer to player in radians.
		final float errorRight = (float) (random.nextInt(2) * 2 - 1);
			//-1 is error left, 1 is error right.
		return straightDir + errorRight * randomRange;
	}

	/**
	 * The time between shots of the small Saucer,
	 * becomes more smaller when score is higher.
	 * @return shot time of small saucer
	 */
	private long smallShotTime() {
		final long score =
				getThisGame().getScore() / Spawner.getDifficultyStep();
		if (score == 0) {
			return SHOT_TIME;
		} else if (score <= SHOT_TIME / (2 * LESS_SHOT)) {
			return SHOT_TIME - (LESS_SHOT * score);
		} else {
			return SHOT_TIME / 2;
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
			setPath(random.nextInt((int) PATHS));
		}
	}

	/**
	 * Display UFO on screen.
	 */
	@Override
	public final void draw() {
		
		Group group = new Group();
		for (double[] f : SHAPE) {
			Line l = new Line(f[0] * (getRadius() * SIZE), f[1] * (getRadius() * SIZE), 
					f[2] * (getRadius() * SIZE), f[1 + 2] * (getRadius() * SIZE));
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(WIDTH * SIZE);
			group.getChildren().add(l);
		}
		group.setTranslateX(getX());
		group.setTranslateY(getY());
		Launcher.getRoot().getChildren().add(group);
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
		} else if (e2 instanceof Bullet && ((Bullet) e2).isFriendly()
				|| e2 instanceof Asteroid) {
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
		int points = BIG_SCORE;
		if (Float.compare(BIG_RADIUS, getRadius()) >= 0) {
			points = SMALL_SCORE;
		}
		getThisGame().addScore(points);
		Particle.explosion(getX(), getY(), getThisGame());
	}
	
	/**
	 * Getter for small radius.
	 * @return small saucer radius
	 */
	public static float getSmallRadius() {
		return SMALL_RADIUS;
	}
}
