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
	 * margin between two lives in pixels.
	 */
	private double margin;
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
	private static final int INVINC_START_TIME = 1000;
	/**
	 * Rotation in radians per tick.
	 */
	private static final double ROTATION = .06;
	/**
	 * Acceleration in pixels per ticks squared.
	 */
	private static final double ACCELERATION = .04;
	/**
	 * Deceleration in pixels per ticks squared.
	 */
	private static final float DECELERATION = .01f;
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
	private static final float BULLETSPEED = 4;
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
	private static final double SIZE = RADIUS * 1.25;
	/**
	 * Maximum speed of Player in pixels per tick.
	 */
	private static final float MAXSPEED = 4;
	/**
	 * Maximum amount of friendly bullets simulatiously in a game.
	 */
	private static final int MAX_BULLETS = 4;
	/**
	 * Y ofset of drawn lives.
	 */
	private static final double LIVES_Y_OFSET = 40;
	/**
	 * The margin between two lives at the start.
	 */
	private static final double START_MARGIN = 10;
	/**
	 * Maximum amount of lives next to eachother with START_MARGIN as margin.
	 */
	private static final double FULL_LIVES = 20;
	

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
		makeInvincible(INVINC_START_TIME);
		margin = START_MARGIN;
	}

	/**
	 * Perform actions that happen when a player dies.
	 */
	@Override
	public final void onDeath() {
		lives--;
		updateMargin();
		if (lives == 0) {
			getThisGame().over();
			makeInvincible(INVINC_START_TIME); //TODO: Game over
		} else {
			setX(getThisGame().getScreenX() / 2);
			setY(getThisGame().getScreenY() / 2);
			setDX(0);
			setDY(0);
			rotation = 0;
			makeInvincible(INVINC_START_TIME);
		}
	}

	/**
	 * Achievement: get a life.
	 */
	public final void gainLife() {
		lives++;
		updateMargin();
	}

	/**
	 * Update the margin between lives to fit on screen.
	 */
	private void updateMargin() {
		if (lives >= FULL_LIVES) {
			margin = START_MARGIN * FULL_LIVES / lives;
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
	 * @param input List containing the keyboard input
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
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
		rotation += ROTATION;
	}

	/**
	 * Turn the player right.
	 */
	private void turnRight() {
		rotation -= ROTATION;
	}

	/**
	 * Makes player move faster.
	 */
	private void accelerate() {
		setDX((float) (getDX() + Math.cos(rotation) * ACCELERATION));
		setDY((float) (getDY() - Math.sin(rotation) * ACCELERATION));
		if (speed() > MAXSPEED) {
			setDX(getDX() * (MAXSPEED / speed()));
			setDY(getDY() * (MAXSPEED / speed()));
		}
		boost = true;
	}

	/**
	 * Makes player move slower.
	 */
	private void slowDown() {
		if (Math.abs(getDX()) + Math.abs(getDY()) != 0) {
			setDX(getDX() - (DECELERATION * getDX())
					/ (Math.abs(getDX()) + Math.abs(getDY())));
			setDY(getDY() - (DECELERATION * getDY())
					/ (Math.abs(getDX()) + Math.abs(getDY())));
		}
	}

	/**
	 * Starts invincibility that lasts the given amount of milliseconds.
	 * 
	 * @param milliseconds
	 *            amount of milliseconds the player should stay invicible.
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
					(float) (getDX() / 2 + (Math.cos(rotation) * BULLETSPEED)),
					(float) (getDY() / 2 - (Math.sin(rotation) * BULLETSPEED)),
					getThisGame());
			getThisGame().create(b);
			lastShot = System.currentTimeMillis();
		}
	}

	/**
	 * Method to handle collisions of entities with the player.
	 * @param e2 - second AbstractEntity
	 */
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			if (invincible() && !hyperspace()) {
				invincibleStart = System.currentTimeMillis();
			} else if (!invincible()) {
				getThisGame().destroy(e2);
				this.onDeath();
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			getThisGame().destroy(e2);
			this.onDeath();
		}
	}

	/**
	 * Display Player on screen.
	 */
	@Override
	public final void draw(final GraphicsContext gc) {
		drawLives(gc);

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
			final double s4 = Math.sin(rotation + (Math.PI - EIGTH_PI));
			final double c4 = Math.cos(rotation + (Math.PI - EIGTH_PI));

			final double s5 = Math.sin(rotation + (Math.PI + EIGTH_PI));
			final double c5 = Math.cos(rotation + (Math.PI + EIGTH_PI));

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
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	private void drawLives(final GraphicsContext gc) {
		for (int i = 0; i < lives; i++) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(1);
			gc.strokePolygon(new double[] { 
					margin * (i + 1),
					margin * (i + 1) - 2,
					margin * (i + 1) + 2},
					new double[] { 
					LIVES_Y_OFSET,
					LIVES_Y_OFSET + LIVES_SIZE  - 2,
					LIVES_Y_OFSET + LIVES_SIZE  - 2}, TRIANGLE_CORNERS);
		}
	}
}