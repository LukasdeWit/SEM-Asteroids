package entity;
import java.util.List;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is the player of the game.
 * @author Kibo
 *
 */
public class Player extends Entity {
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
	 * Amount of invincible time in miliseconds.
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
	private static final float PLAYER_RADIUS = 5;
	/**
	 * Invincible time at the start of a game.
	 */
	private static final int INVINC_START_TIME = 500;
	/**
	 * Rotation in radians per tick.
	 */
	private static final double ROTATION_MULTIPLIER = .1;
	/**
	 * Acceleration in pixels per ticks squared.
	 */
	private static final double ACCELERATION_MULTIPLIER = .1;
	/**
	 * Deceleration in pixels per ticks squared.
	 */
	private static final float DECELERATION_MULTIPLIER = .02f;
	/**
	 * Time in miliseconds per hyperspace jump.
	 */
	private static final int HYPERSPACE_TIME = 1000;
	/**
	 * Time between shots in miliseconds.
	 */
	private static final long TIME_BETWEEN_SHOTS = 200;
	/**
	 * Speed of bullets relative to player in pixels per tick.
	 */
	private static final float BULLETSPEED = 2;
	/**
	 * A quarter pi.
	 */
	private static final double QUARTER_PI = Math.PI / 4;
	/**
	 * One eigth pi.
	 */
	private static final double EIGTH_PI = Math.PI / 8;
	/**
	 * Time of flicker while respawning.
	 */
	private static final int RESPAWN_FLICKER_TIME = 250;
	/**
	 * Size of lives in pixels.
	 */
	private static final int LIVES_SIZE = 10;
	/**
	 * The number of corners of a triangle.
	 */
	private static final int TRIANGLE_CORNERS = 3;
	/**
	 * Draw size of Player.
	 */
	private static final double SIZE = PLAYER_RADIUS * 1.25;
	

	/**
	 * Constructor for the Player class.
	 * 
	 * @param x
	 *            location of Player along the X-axis.
	 * @param y
	 *            location of Player along the Y-axis.
	 * @param dX
	 *            velocity of Player along the X-axis.
	 * @param dY
	 *            velocity of Player along the Y-axis.
	 * @param thisGame
	 *            Game the Player exists in.
	 */
	public Player(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		lives = STARTING_LIVES;
		this.setRadius(PLAYER_RADIUS);
		rotation = 0;
		invincibleStart(INVINC_START_TIME);
	}

	/**
	 * Perform actions that happen when a player dies.
	 */
	public final void die() {
		lives--;
		if (lives == 0) {
			getThisGame().over();
			invincibleStart(INVINC_START_TIME); //TODO: Game over
		} else {
			setX(getThisGame().getScreenX() / 2);
			setY(getThisGame().getScreenY() / 2);
			setDX(0);
			setDY(0);
			rotation = 0;
			invincibleStart(INVINC_START_TIME);
		}
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
	 * @param input
	 *            arraylist containing the keyboard input
	 */
	public final void keyHandler(final List<String> input) {
		if ((input.contains("LEFT") || input.contains("A"))
				&& !(input.contains("RIGHT") || input.contains("D"))) {
			turnLeft();
		}

		if ((input.contains("RIGHT") || input.contains("D")) 
				&& !(input.contains("LEFT") || input.contains("A"))) {
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
		rotation += ROTATION_MULTIPLIER;
	}

	/**
	 * Turn the player right.
	 */
	private void turnRight() {
		rotation -= ROTATION_MULTIPLIER;
	}

	/**
	 * Makes player move faster.
	 */
	private void accelerate() {
		setDX((float) (getDX() + Math.cos(rotation) * ACCELERATION_MULTIPLIER));
		setDY((float) (getDY() - Math.sin(rotation) * ACCELERATION_MULTIPLIER));
		boost = true;
	}

	/**
	 * Makes player move slower.
	 */
	private void slowDown() {
		if (Math.abs(getDX()) + Math.abs(getDY()) != 0) {
			setDX((float) (getDX() - (DECELERATION_MULTIPLIER * getDX()) 
					/ (Math.abs(getDX()) + Math.abs(getDY()))));
			setDY((float) (getDY() - (DECELERATION_MULTIPLIER * getDY()) 
					/ (Math.abs(getDX()) + Math.abs(getDY()))));
		}
	}

	/**
	 * Starts invincibility that lasts the given amount of milliseconds.
	 * 
	 * @param milliseconds
	 *            amount of milliseconds the player should stay invicible.
	 */
	private void invincibleStart(final int milliseconds) {
		invincibleStart = System.currentTimeMillis();
		invincibleMS = milliseconds;
	}

	/**
	 * @return whether or not the player is invincible at this moment.
	 */
	public final boolean invincible() {
		return (invincibleStart + invincibleMS > System.currentTimeMillis());
	}

	/**
	 * Method to handle hyperspace mechanic.
	 */
	private void goHyperspace() {
		setX((float) (getThisGame().getScreenX() * Math.random()));
		setY((float) (getThisGame().getScreenY() * Math.random()));
		setDX(0);
		setDY(0);
		invincibleStart(HYPERSPACE_TIME);
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
		if (System.currentTimeMillis() - lastShot > TIME_BETWEEN_SHOTS) {
			Bullet b = new Bullet(getX(), getY(), getDX() 
					+ ((float) Math.sin(rotation + Math.PI / 2)) * BULLETSPEED, 
					getDY() 
					+ ((float) Math.cos(rotation + Math.PI / 2)) * BULLETSPEED, 
					getThisGame());
			getThisGame().create(b);
			lastShot = System.currentTimeMillis();
		}
	}

	/**
	 * Method to handle collisions of entities with the player.
	 * @param e2 - second Entity
	 */
	public final void collide(final Entity e2) {
		if (e2 instanceof Asteroid) {
			if (invincible() && !hyperspace()) {
				invincibleStart = System.currentTimeMillis();
			} else if (!invincible()) {
				((Asteroid) e2).split();
				this.die();
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			getThisGame().destroy(e2);
			this.die();
		}
	}

	/**
	 * Display Player on screen.
	 */
	@Override
	public final void draw(final GraphicsContext gc) {
		drawLives(gc);

		double s1 = Math.sin(rotation);
		double c1 = Math.cos(rotation);

		double s2 = Math.sin(rotation + (Math.PI - QUARTER_PI));
		double c2 = Math.cos(rotation + (Math.PI - QUARTER_PI));

		double s3 = Math.sin(rotation + (Math.PI + QUARTER_PI));
		double c3 = Math.cos(rotation + (Math.PI + QUARTER_PI));

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
			double s4 = Math.sin(rotation + (Math.PI - EIGTH_PI));
			double c4 = Math.cos(rotation + (Math.PI - EIGTH_PI));

			double s5 = Math.sin(rotation + (Math.PI + EIGTH_PI));
			double c5 = Math.cos(rotation + (Math.PI + EIGTH_PI));

			double s6 = Math.sin(rotation + (Math.PI));
			double c6 = Math.cos(rotation + (Math.PI));
			gc.strokePolygon(new double[] { 
					getX() + (SIZE - 1) * c4, 
					getX() + (SIZE - 1) * c5, 
					getX() + (SIZE + 2) * c6 },
					new double[] { 
					getY() - (SIZE - 1) * s4, 
					getY() - (SIZE - 1) * s5, 
					getY() - (SIZE + 2) * s6 }, TRIANGLE_CORNERS);
			//TODO: PLEASE MAKE THIS BETTER
			boost = false;
		}
	}

	/**
	 * Method to help display the amount of lives the player has left.
	 * 
	 * @param gc
	 *            graphicscontext
	 */
	private void drawLives(final GraphicsContext gc) {
		for (int i = 0; i < lives; i++) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(1);
			gc.strokePolygon(new double[] { 
					LIVES_SIZE * (i + 1), 
					LIVES_SIZE * (i + 1) - 2, 
					LIVES_SIZE * (i + 1) + 2}, 
					new double[] { 
					LIVES_SIZE, 
					LIVES_SIZE * 2 - 2, 
					LIVES_SIZE * 2 - 2}, TRIANGLE_CORNERS);
		}
	}
}