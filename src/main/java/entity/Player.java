package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import abstractpowerup.AbstractPowerup;
import display.DisplayEntity;
import game.Game;
import game.Gamestate;
import game.Logger;

/**
 * This class is the player of the game.
 * @author Kibo
 *
 */
public class Player extends AbstractEntity {
	private int lives;
	private double rotation;
	private long lastShot;
	private long invincibleStart;
	private int invincibleMS;
	private long hyperspaceStart;
	private boolean boost;
	private final boolean playerTwo;
	
	private static final int STARTING_LIVES = 3;
	private static final float RADIUS = 5;
	private static final int INVINCIBILITY_START_TIME = 1000;
	private static final double ROTATION_SPEED = .06;
	private static final double ACCELERATION = .04;
	private static final float DECELERATION = .01f;
	private static final int HYPERSPACE_TIME = 1000;
	private static final long TIME_BETWEEN_SHOTS = 200;
	private static final float BULLET_SPEED = 4;
	private static final float MAX_SPEED = 4;
	private static final int MAX_BULLETS = 4;
	private static final int CHANCE_OF_DYING = 25;
	
	private final List<AbstractPowerup> powerups;
	private int bullets;
	private double fireRate;
	private int piercing;
	private int shielding;
	private final boolean multishot;
	
	private static final float SPAWN_OFFSET = 40;
	
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	
	/**
	 * Constructor for the Player class.
	 * 
	 * @param x location of Player along the X-axis.
	 * @param y location of Player along the Y-axis.
	 * @param dX velocity of Player along the X-axis.
	 * @param dY velocity of Player along the Y-axis.
	 * @param playerTwo - true if playertwo
	 */
	public Player(final float x, final float y, 
			final float dX, final float dY, final boolean playerTwo) {
		super(x, y, dX, dY);
		lives = STARTING_LIVES;
		setRadius(RADIUS);
		rotation = 0;
		this.playerTwo = playerTwo;
		makeInvincible(INVINCIBILITY_START_TIME);
		powerups = new ArrayList<AbstractPowerup>();
		bullets = 1;
		fireRate = 1;
		piercing = 1;
		shielding = 0;
		multishot = false;
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
		if (shielding < 1) {
			lives--;
			if (lives <= 0) {
				// we are out of lives, call gameover
				Game.getInstance().over();
			} else {
				// we lose one live
	
				// respawn the player
				if (isPlayerTwo()) {
					setX(Game.getInstance().getScreenX() / 2 + SPAWN_OFFSET);
				} else if (Gamestate.getInstance().isCoop()) {
					setX(Game.getInstance().getScreenX() / 2 - SPAWN_OFFSET);
				} else {
					setX(Game.getInstance().getScreenX() / 2);
				}
				setY(Game.getInstance().getScreenY() / 2);
				setDX(0);
				setDY(0);
				rotation = 0;
				makeInvincible(INVINCIBILITY_START_TIME);
			} 
		} else {
			shielding--;
		}
	}

	/**
	 * Achievement: get a life.
	 */
	public final void gainLife() {
		lives++;
		if (lives == 1) {
			if (isPlayerTwo()) {
				setX(Game.getInstance().getScreenX() / 2 + SPAWN_OFFSET);
			} else if (Gamestate.getInstance().isCoop()) {
				setX(Game.getInstance().getScreenX() / 2 - SPAWN_OFFSET);
			} else {
				setX(Game.getInstance().getScreenX() / 2);
			}
			setY(Game.getInstance().getScreenY() / 2);
			setDX(0);
			setDY(0);
			rotation = 0;
			makeInvincible(INVINCIBILITY_START_TIME);
			Game.getInstance().create(this);
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
		handlePowerups();
		if (!invincible()) {
			if (Gamestate.getInstance().isCoop()) {
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
		if (input.contains(LEFT) || input.contains("A")
				|| !(input.contains(RIGHT) || input.contains("D"))) {
			turnLeft();
		}

		if (input.contains(RIGHT) || input.contains("D")
				|| !(input.contains(LEFT) || input.contains("A"))) {
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
		if (isPlayerTwo()) {
			if (input.contains(LEFT) || !(input.contains(RIGHT))) {
				turnLeft();
			}
	
			if (input.contains(RIGHT) || !(input.contains("LEFT"))) {
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
		setDX((float) (getDX() + Math.cos(getRotation()) * ACCELERATION));
		setDY((float) (getDY() - Math.sin(getRotation()) * ACCELERATION));
		if (speed() > MAX_SPEED) {
			setDX(getDX() * (MAX_SPEED / speed()));
			setDY(getDY() * (MAX_SPEED / speed()));
		}
		setBoost(true);
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
		return getInvincibleStart() + invincibleMS > System.currentTimeMillis();
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
		setX((float) (Game.getInstance().getScreenX() * Math.random()));
		setY((float) (Game.getInstance().getScreenY() * Math.random()));
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
		if (System.currentTimeMillis() - lastShot >	(TIME_BETWEEN_SHOTS * fireRate) 
				&& Game.getInstance().bullets(this) < (MAX_BULLETS * bullets)) {
			final Bullet b = new Bullet(getX(), getY(),
					(float) (getDX() / 2 + Math.cos(rotation) * BULLET_SPEED),
					(float) (getDY() / 2 - Math.sin(rotation) * BULLET_SPEED), piercing);
			Game.getInstance().create(b);
			b.setPlayer(this);
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
				Game.getInstance().destroy(e2);
				onHit();
				Logger.getInstance().log("Player was hit by an asteroid.");
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			Game.getInstance().destroy(e2);
			onHit();
			Logger.getInstance().log("Player was hit by a bullet.");
		}
	}

	/**
	 * draw the player.
	 */
	@Override
	public final void draw() {
		DisplayEntity.player(this);
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
	 * @return powerups the list of powerups
	 */
	public final List<AbstractPowerup> getPowerups() {
		return powerups;
	}
	
	/**
	 * Gives a powerup to the player.
	 * @param powerToAdd the power given to the player.
	 */
	public final void givePowerup(final AbstractPowerup powerToAdd) {
		this.powerups.add(powerToAdd);
	}
	
	/**
	 * Gives the player one unit of shield.
	 */
	public final void giveShield() {
		shielding++;
	}
	
	/**
	 * Is used to process powerups.
	 */
	public final void handlePowerups() {
		fireRate = 1;
		bullets = 1;
		piercing = 1;
		float bulletSize = 1;
		for (final AbstractPowerup x : powerups) {
			if (x.powerupOver()) {
				powerups.remove(x);
				break;
			} else {
				fireRate = fireRate
				* x.getRateMult();
				bullets = bullets
				* x.getNumbMult();
				piercing += x.getPierceRateBoost();
				bulletSize = bulletSize * x.getSizeBoost();
			}
		}
	}
	
	/**
	 * @return the spawnOffset
	 */
	public static float getSpawnOffset() {
		return SPAWN_OFFSET;
	}

	/**
	 * @return the invincibleStart
	 */
	public final long getInvincibleStart() {
		return invincibleStart;
	}

	/**
	 * @return the playerTwo
	 */
	public final boolean isPlayerTwo() {
		return playerTwo;
	}

	/**
	 * @return the boost
	 */
	public final boolean isBoost() {
		return boost;
	}

	/**
	 * @param boost the boost to set
	 */
	public final void setBoost(final boolean boost) {
		this.boost = boost;
	}

	/**
	 * @return the rotation
	 */
	public final double getRotation() {
		return rotation;
	}

	/**
	 * @return the multishot
	 */
	public final boolean isMultishot() {
		return multishot;
	}
}