package entity;
import java.util.List;
import java.util.Random;

import game.Game;
import game.Launcher;
import game.Logger;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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
	private boolean playerTwo;
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
	 * Player has a chance of 1 in this number of dying in hyperspace.
	 */
	private static final int CHANCE_OF_DYING = 25;
	private static final float SPAWN_OFFSET = 40;
	private static final float[] PLAYER_TWO_CIRCLE = {-11, 0, 9};
	private static final float[][] PLAYER_TWO_LINES = {
			{-11,  0,  7,  0},
			{  2,  0,  8,  6},
			{  2,  0,  8, -6},
			{  0,  6, 18,  6},
			{  0, -6, 18, -6}
	};
	private static final float PLAYER_TWO_SIZE = .5f;
	private static final float[][] PLAYER_ONE_LINES = {
			{10, 0, -8, 8},
			{-8, 8, -8, -8},
			{-8, -8, 10, 0}
	};
	private static final float PLAYER_ONE_SIZE = .5f;
	
	

	/**
	 * Constructor for the Player class.
	 * 
	 * @param x location of Player along the X-axis.
	 * @param y location of Player along the Y-axis.
	 * @param dX velocity of Player along the X-axis.
	 * @param dY velocity of Player along the Y-axis.
	 * @param thisGame Game the Player exists in.
	 * @param playerTwo - true if playertwo
	 */
	public Player(final float x, final float y, 
			final float dX, final float dY, final Game thisGame, final boolean playerTwo) {
		super(x, y, dX, dY, thisGame);
		lives = STARTING_LIVES;
		setRadius(RADIUS);
		rotation = 0;
		this.playerTwo = playerTwo;
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
			if (playerTwo) {
				setX(getThisGame().getScreenX() / 2 + SPAWN_OFFSET);
			} else if (getThisGame().getGamestate().isCoop()) {
				setX(getThisGame().getScreenX() / 2 - SPAWN_OFFSET);
			} else {
				setX(getThisGame().getScreenX() / 2);
			}
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
		if (lives == 1) {
			if (playerTwo) {
				setX(getThisGame().getScreenX() / 2 + SPAWN_OFFSET);
			} else if (getThisGame().getGamestate().isCoop()) {
				setX(getThisGame().getScreenX() / 2 - SPAWN_OFFSET);
			} else {
				setX(getThisGame().getScreenX() / 2);
			}
			setY(getThisGame().getScreenY() / 2);
			setDX(0);
			setDY(0);
			rotation = 0;
			makeInvincible(INVINCIBILITY_START_TIME);
			getThisGame().create(this);
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
			if (getThisGame().getGamestate().isCoop()) {
				keyHandlerTwo(input);
			} else {
				keyHandler(input);
			}
		}
	}

	/**
	 * Method that translates keyboard input into player character movement.
	 * 
	 * @param input List containing the keyboard input
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	private void keyHandler(final List<String> input) {
		if (input.contains("LEFT") || input.contains("A")
				|| !(input.contains("RIGHT") || input.contains("D"))) {
			turnLeft();
		}

		if (input.contains("RIGHT") || input.contains("D")
				|| !(input.contains("LEFT") || input.contains("A"))) {
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
	 * key handler for coop.
	 * 
	 * @param input List containing the keyboard input
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	private void keyHandlerTwo(final List<String> input) {
		if (playerTwo) {
			if (input.contains("LEFT") || !(input.contains("RIGHT"))) {
				turnLeft();
			}
	
			if (input.contains("RIGHT") || !(input.contains("LEFT"))) {
				turnRight();
			}
	
			if (input.contains("UP")) {
				accelerate();
			}
	
			if (input.contains("DOWN")) {
				goHyperspace();
			}
	
			if (input.contains("ENTER")) {
				fire();
			}
		} else {
			if (input.contains("A") || !(input.contains("D"))) {
				turnLeft();
			}
	
			if (input.contains("D") || !(input.contains("A"))) {
				turnRight();
			}
	
			if (input.contains("W")) {
				accelerate();
			}
	
			if (input.contains("S")) {
				goHyperspace();
			}
	
			if (input.contains("SPACE")) {
				fire();
			}
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
		final Random random = new Random();
		if (random.nextInt(CHANCE_OF_DYING) == 0) {
			onHit();
			Logger.getInstance().log("Player died in hyperspace.");
		} else {
		Logger.getInstance().log("Player went into hyperspace.");
		setX((float) (getThisGame().getScreenX() * Math.random()));
		setY((float) (getThisGame().getScreenY() * Math.random()));
		setDX(0);
		setDY(0);
		makeInvincible(HYPERSPACE_TIME);
		hyperspaceStart = System.currentTimeMillis();
		}
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
				&& getThisGame().bullets(this) < MAX_BULLETS) {
			final Bullet b = new Bullet(getX(), getY(),
					(float) (getDX() / 2 + Math.cos(rotation) * BULLET_SPEED),
					(float) (getDY() / 2 - Math.sin(rotation) * BULLET_SPEED),
					getThisGame());
			b.setPlayer(this);
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
				Logger.getInstance().log("Player was hit by an asteroid.");
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			getThisGame().destroy(e2);
			onHit();
			Logger.getInstance().log("Player was hit by a bullet.");
		}
	}

	/**
	 * draw the player.
	 * @param root - Group
	 */
	@Override
	public final void draw(final Group root) {
		if (playerTwo) {
			drawTwo(root);
		} else {
			drawOne(root);
		}
	}
	
	/**
	 * Display Player two on screen.
	 * @param root - Group
	 */
	public final void drawTwo(final Group root) {
		Group group = new Group();
		for (float[] f : PLAYER_TWO_LINES) {
			Line l = new Line(f[0] * PLAYER_TWO_SIZE, f[1] * PLAYER_TWO_SIZE, 
					f[2] * PLAYER_TWO_SIZE, f[1 + 2] * PLAYER_TWO_SIZE);
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(2 * PLAYER_TWO_SIZE);
			group.getChildren().add(l);
		}
		Circle c = new Circle(PLAYER_TWO_CIRCLE[0] * PLAYER_TWO_SIZE, 
				PLAYER_TWO_CIRCLE[1] * PLAYER_TWO_SIZE, PLAYER_TWO_CIRCLE[2] * PLAYER_TWO_SIZE);
		c.setFill(Color.WHITE);
		group.getChildren().add(c);
		group.setRotate(Math.toDegrees(-rotation) + 180);
		group.setTranslateX(getX());
		group.setTranslateY(getY());
		Launcher.getRoot().getChildren().add(group);
	}
	
	/**
	 * Display Player one on screen.
	 * @param root - Group
	 */
	public final void drawOne(final Group root) {
		Group group = new Group();
		for (float[] f : PLAYER_ONE_LINES) {
			Line l = new Line(f[0] * PLAYER_ONE_SIZE, f[1] * PLAYER_ONE_SIZE, 
					f[2] * PLAYER_ONE_SIZE, f[1 + 2] * PLAYER_ONE_SIZE);
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(2 * PLAYER_ONE_SIZE);
			group.getChildren().add(l);
		}
		group.setRotate(Math.toDegrees(-rotation));
		group.setTranslateX(getX());
		group.setTranslateY(getY());
		
		getThisGame().getRoot().getChildren().add(group);
		
		/*if (boost) {
			final double s4 = Math.sin(rotation + (Math.PI - EIGHTH_PI));
			final double c4 = Math.cos(rotation + (Math.PI - EIGHTH_PI));

			final double s5 = Math.sin(rotation + (Math.PI + EIGHTH_PI));
			final double c5 = Math.cos(rotation + (Math.PI + EIGHTH_PI));

			final double s6 = Math.sin(rotation + (Math.PI));
			final double c6 = Math.cos(rotation + (Math.PI));
			root.strokePolygon(new double[] { 
					getX() + (SIZE - 1) * c4, 
					getX() + (SIZE - 1) * c5, 
					getX() + (SIZE + 2) * c6 },
					new double[] { 
					getY() - (SIZE - 1) * s4, 
					getY() - (SIZE - 1) * s5, 
					getY() - (SIZE + 2) * s6 }, TRIANGLE_CORNERS);
			boost = false;
		}*/
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

	/**
	 * @return the spawnOffset
	 */
	public static float getSpawnOffset() {
		return SPAWN_OFFSET;
	}
}