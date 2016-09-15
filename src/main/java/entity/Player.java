package entity;
import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * This class is the player of the game.
 * @author Kibo
 *
 */
public class Player extends AbstractEntity {
	/**
	 * Amount of lives.
	 */
	private int lives;
	/**
	 * Rotation in radians.
	 */
	private double rotation;
	/**
	 * Time of last shot.
	 */
	private long lastShot;
	/**
	 * Start time of invincibility.
	 */
	private long invincibleStart;
	/**
	 * Amount of invincible time in milliseconds.
	 */
	private int invincibleMS;
	/**
	 * Start time of hyperspace.
	 */
	private long hyperspaceStart;
	/**
	 * true if boost is active, else false.
	 */
	private boolean boost;
	/**
	 * Amount of lives at the start of a game.
	 */
	private static final int STARTING_LIVES = 3;
	/**
	 * Radius of Player.
	 */
	private static final float RADIUS = 5;
	/**
	 * Invincible time at the start of a game.
	 */
	private static final int INVINCIBILITY_START_TIME = 1000;
	/**
	 * Rotation in radians per tick.
	 */
	private static final double ROTATION_SPEED = .06;
	/**
	 * Acceleration in pixels per ticks squared.
	 */
	private static final double ACCELERATION = .04;
	/**
	 * Deceleration in pixels per ticks squared.
	 */
	private static final float DECELERATION = .01f;
	/**
	 * Time in milliseconds per hyperspace jump.
	 */
	private static final int HYPERSPACE_TIME = 1000;
	/**
	 * Time between shots in milliseconds.
	 */
	private static final long TIME_BETWEEN_SHOTS = 200;
	/**
	 * Speed of bullets relative to player in pixels per tick.
	 */
	private static final float BULLET_SPEED = 4;
	/**
	 * A quarter pi.
	 */
	private static final double QUARTER_PI = Math.PI / 4;
	/**
	 * One eighth pi.
	 */
	private static final double EIGHTH_PI = Math.PI / 8;
	/**
	 * Time of flicker while respawning.
	 */
	private static final int RESPAWN_FLICKER_TIME = 250;
	/**
	 * The number of corners of a triangle.
	 */
	private static final int TRIANGLE_CORNERS = 3;
	/**
	 * Draw size of Player.
	 */
	private static final double SIZE = RADIUS * 1.25;
	/**
	 * Maximum speed of Player in pixels per tick.
	 */
	private static final float MAX_SPEED = 4;
	/**
	 * Maximum amount of friendly bullets simultaneously in a game.
	 */
	private static final int MAX_BULLETS = 4;
	

	/**
	 * Constructor for the Player class.
	 * 
	 * @param x location of Player along the X-axis.
	 * @param y location of Player along the Y-axis.
	 * @param dX velocity of Player along the X-axis.
	 * @param dY velocity of Player along the Y-axis.
	 * @param thisGame Game the Player exists in.
	 */
	public Player(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		lives = STARTING_LIVES;
		setRadius(RADIUS);
		rotation = 0;
		makeInvincible(INVINCIBILITY_START_TIME);
	}

	/**
	 * Perform actions that happen when a player dies.
	 * <p>
	 * this is called when the player has
	 * <strong>no lives left</strong>
	 * you probably don't need to call this directly, instead call onHit()
	 */
	@Override
	public final void onDeath() {
		// no-op
	}

	/**
	 * handle the player taking a hit (and is not invincible)
	 *
	 * this happens when (for example) the player collides with an asteroid
	 * or is hit by the bullet of an saucer.
	 */
	public final void onHit() {
		lives--;
		if (lives <= 0) {
			// we are out of lives, call gameover
			getThisGame().over();
		} else {
			// we lose one live

			// respawn the player
			setX(getThisGame().getScreenX() / 2);
			setY(getThisGame().getScreenY() / 2);
			setDX(0);
			setDY(0);
			rotation = 0;
			makeInvincible(INVINCIBILITY_START_TIME);
		}
	}

	/**
	 * Achievement: get a life.
	 */
	public final void gainLife() {
		lives++;
	}

	/**
	 * Calculate new position of player.
	 * @param input - the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		slowDown();
		wrapAround();
		if (!invincible()) {
			keyHandler(input);
		}
	}

	/**
	 * Method that translates keyboard input into player character movement.
	 * 
	 * @param input List containing the keyboard input
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	private void keyHandler(final List<String> input) {
		if (input.contains("LEFT") || input.contains("A") || !(input.contains("RIGHT") || input.contains("D"))) {
			turnLeft();
		}

		if (input.contains("RIGHT") || input.contains("D") || !(input.contains("LEFT") || input.contains("A"))) {
			turnRight();
		}

		if (input.contains("UP") || input.contains("W")) {
			accelerate();
		}

		if (input.contains("DOWN") || input.contains("S")) {
			goHyperspace();
		}

		if (input.contains("SPACE")) {
			fire();
		}
	}

	/**
	 * Turn the player left.
	 */
	private void turnLeft() {
		rotation += ROTATION_SPEED;
	}

	/**
	 * Turn the player right.
	 */
	private void turnRight() {
		rotation -= ROTATION_SPEED;
	}

	/**
	 * Makes player move faster.
	 */
	private void accelerate() {
		setDX((float) (getDX() + Math.cos(rotation) * ACCELERATION));
		setDY((float) (getDY() - Math.sin(rotation) * ACCELERATION));
		if (speed() > MAX_SPEED) {
			setDX(getDX() * (MAX_SPEED / speed()));
			setDY(getDY() * (MAX_SPEED / speed()));
		}
		boost = true;
	}

	/**
	 * Makes player move slower.
	 */
	private void slowDown() {
		if (Float.compare(Math.abs(getDX()) + Math.abs(getDY()), 0) != 0) {
			setDX(getDX() - (DECELERATION * getDX())
					/ (Math.abs(getDX()) + Math.abs(getDY())));
			setDY(getDY() - (DECELERATION * getDY())
					/ (Math.abs(getDX()) + Math.abs(getDY())));
		}
	}

	/**
	 * Starts invincibility that lasts the given amount of milliseconds.
	 * 
	 * @param milliseconds amount of milliseconds the player should stay
	 *                        invincible.
	 */
	private void makeInvincible(final int milliseconds) {
		invincibleStart = System.currentTimeMillis();
		invincibleMS = milliseconds;
	}

	/**
	 * @return whether or not the player is invincible at this moment.
	 */
	public final boolean invincible() {
		return invincibleStart + invincibleMS > System.currentTimeMillis();
	}

	/**
	 * Method to handle hyperspace mechanic.
	 */
	private void goHyperspace() {
		setX((float) (getThisGame().getScreenX() * Math.random()));
		setY((float) (getThisGame().getScreenY() * Math.random()));
		setDX(0);
		setDY(0);
		makeInvincible(HYPERSPACE_TIME);
		hyperspaceStart = System.currentTimeMillis();
	}

	/**
	 * @return whether or not the player is in hyperspace at this moment.
	 */
	private boolean hyperspace() {
		return hyperspaceStart + invincibleMS > System.currentTimeMillis();
	}

	/**
	 * Method to handle firing bullets.
	 */
	private void fire() {
		if (System.currentTimeMillis() - lastShot > TIME_BETWEEN_SHOTS
				&& getThisGame().bullets() < MAX_BULLETS) {
			final Bullet b = new Bullet(getX(), getY(),
					(float) (getDX() / 2 + Math.cos(rotation) * BULLET_SPEED),
					(float) (getDY() / 2 - Math.sin(rotation) * BULLET_SPEED),
					getThisGame());
			getThisGame().create(b);
			lastShot = System.currentTimeMillis();
		}
	}

	/**
	 * Method to handle collisions of entities with the player.
	 * @param e2 - second AbstractEntity
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			if (invincible() && !hyperspace()) {
				invincibleStart = System.currentTimeMillis();
			} else if (!invincible()) {
				getThisGame().destroy(e2);
				onHit();
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			getThisGame().destroy(e2);
			onHit();
		}
	}

	/**
	 * Display Player on screen.
	 */
	@Override
	public final void draw(final GraphicsContext gc) {
		final double s1 = Math.sin(rotation);
		final double c1 = Math.cos(rotation);

		final double s2 = Math.sin(rotation + (Math.PI - QUARTER_PI));
		final double c2 = Math.cos(rotation + (Math.PI - QUARTER_PI));

		final double s3 = Math.sin(rotation + (Math.PI + QUARTER_PI));
		final double c3 = Math.cos(rotation + (Math.PI + QUARTER_PI));

		gc.setStroke(Color.WHITE);
		if (invincible()
				&& (System.currentTimeMillis() + invincibleMS) 
				% RESPAWN_FLICKER_TIME * 2 < RESPAWN_FLICKER_TIME) {
			gc.setStroke(Color.GREY);
		}
		if (hyperspace()) {
			gc.setStroke(Color.BLACK);
		}
		gc.setLineWidth(1);
		gc.strokePolygon(
				new double[] { getX() + SIZE * c1, 
				getX() + SIZE * c2, 
				getX() + SIZE * c3 },
				new double[] { getY() - SIZE * s1, 
				getY() - SIZE * s2, 
				getY() - SIZE * s3 }, TRIANGLE_CORNERS);

		if (boost) {
			final double s4 = Math.sin(rotation + (Math.PI - EIGHTH_PI));
			final double c4 = Math.cos(rotation + (Math.PI - EIGHTH_PI));

			final double s5 = Math.sin(rotation + (Math.PI + EIGHTH_PI));
			final double c5 = Math.cos(rotation + (Math.PI + EIGHTH_PI));

			final double s6 = Math.sin(rotation + (Math.PI));
			final double c6 = Math.cos(rotation + (Math.PI));
			gc.strokePolygon(new double[] { 
					getX() + (SIZE - 1) * c4, 
					getX() + (SIZE - 1) * c5, 
					getX() + (SIZE + 2) * c6 },
					new double[] { 
					getY() - (SIZE - 1) * s4, 
					getY() - (SIZE - 1) * s5, 
					getY() - (SIZE + 2) * s6 }, TRIANGLE_CORNERS);
			boost = false;
		}
	}

	/**
	 * True if more than 0 lives.
	 * @return true if alive
	 */
	public final boolean isAlive() {
		return lives > 0;
	}

	/**
	 * @return lives
	 */

	public final int getLives() {
		return lives;
	}
}