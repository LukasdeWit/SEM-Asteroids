package entity;

import display.DisplayEntity;
import game.Game;
import game.Logger;

import java.util.List;
import java.util.Random;

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
	private static final long FIRE_RATE = 200;
	private static final float BULLET_SPEED = 4;
	private static final float MAX_SPEED = 4;
	private static final int MAX_BULLETS = 4;
	private static final int CHANCE_OF_DYING = 25;
	private static final float BULLET_SIZE = 2;
	
	private int maxBullets;
	private double fireRate;
	private int piercing;
	private int shielding;
	private boolean tripleShot;
	private float bulletSize;
	private int changeOfDying;
	
	private static final float SPAWN_OFFSET = 40;
	
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	private static final double TRIPLE_SHOT_ANGLE = .1;
	
	/**
	 * Constructor for the Player class.
	 * 
	 * @param x location of Player along the X-axis.
	 * @param y location of Player along the Y-axis.
	 * @param dX velocity of Player along the X-axis.
	 * @param dY velocity of Player along the Y-axis.
	 * @param playerTwo true if playertwo
	 * @param thisGame the game this particle belongs to
	 */
	public Player(final float x, final float y,
			final float dX, final float dY, final Game thisGame, final boolean playerTwo) {
		super(x, y, dX, dY, thisGame);
		lives = STARTING_LIVES;
		setRadius(RADIUS);
		rotation = 0;
		this.playerTwo = playerTwo;
		makeInvincible(INVINCIBILITY_START_TIME);
		maxBullets = MAX_BULLETS;
		fireRate = FIRE_RATE;
		piercing = 1;
		shielding = 0;
		bulletSize = BULLET_SIZE;
		tripleShot = false;
		changeOfDying = CHANCE_OF_DYING;
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
				getThisGame().over();
			} else {
				// we lose one live
	
				// respawn the player
				if (isPlayerTwo()) {
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
		} else {
			shielding--;
			makeInvincible(INVINCIBILITY_START_TIME);
		}
	}

	/**
	 * Achievement: get a life.
	 */
	public final void gainLife() {
		lives++;
		if (lives == 1) {
			if (isPlayerTwo()) {
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
		if (input.contains(LEFT) || input.contains("A")) {
			turnLeft();
		}

		if (input.contains(RIGHT) || input.contains("D")) {
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
			if (input.contains(LEFT)) {
				turnLeft();
			}
	
			if (input.contains(RIGHT)) {
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
			if (input.contains("A")) {
				turnLeft();
			}
	
			if (input.contains("D")) {
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
		if (random.nextInt(changeOfDying) == 0) {
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
	 * Method to handle firing bullets.
	 */
	private void fire() {
		if (System.currentTimeMillis() - lastShot >	fireRate 
				&& getThisGame().bullets(this) < maxBullets) {
			fireBullet(rotation);
			if (tripleShot) {
				fireBullet(rotation - TRIPLE_SHOT_ANGLE);
				fireBullet(rotation + TRIPLE_SHOT_ANGLE);
			}
			lastShot = System.currentTimeMillis();
		}
	}
	
	/**
	 * fire a bullet in a direction.
	 * @param direction - the direction
	 */
	private void fireBullet(final double direction) {
		final Bullet b = new Bullet(getX(), getY(),
				(float) (getDX() / 2 + Math.cos(direction) * BULLET_SPEED),
				(float) (getDY() / 2 - Math.sin(direction) * BULLET_SPEED), piercing, getThisGame());
		getThisGame().create(b);
		b.setPlayer(this);
		b.setRadius(bulletSize);
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
	 * @return whether or not the player is in hyperspace at this moment.
	 */
	private boolean hyperspace() {
		return hyperspaceStart + invincibleMS > System.currentTimeMillis();
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
	 * @param lives the lives to set
	 */
	public final void setLives(final int lives) {
		this.lives = lives;
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
	 * @param invincibleStart the invincibleStart to set
	 */
	public final void setInvincibleStart(final long invincibleStart) {
		this.invincibleStart = invincibleStart;
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
	 * @return the rotationSpeed
	 */
	public static double getRotationSpeed() {
		return ROTATION_SPEED;
	}

	/**
	 * @return the shielding
	 */
	public final int getShielding() {
		return shielding;
	}

	/**
	 * @param shielding the shielding to set
	 */
	public final void setShielding(final int shielding) {
		this.shielding = shielding;
	}

	/**
	 * @param bulletSize the bulletSize to set
	 */
	public final void setBulletSize(final float bulletSize) {
		this.bulletSize = bulletSize;
	}

	/**
	 * @param tripleShot the tripleShot to set
	 */
	public final void setTripleShot(final boolean tripleShot) {
		this.tripleShot = tripleShot;
	}

	/**
	 * @param piercing the piercing to set
	 */
	public final void setPiercing(final int piercing) {
		this.piercing = piercing;
	}

	/**
	 * @param fireRate the fireRate to set
	 */
	public final void setFireRate(final double fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * @return the bulletSize
	 */
	public static float getBulletSize() {
		return BULLET_SIZE;
	}

	/**
	 * @return the fireRate
	 */
	public static long getFireRate() {
		return FIRE_RATE;
	}

	/**
	 * gain a shield level.
	 */
	public final void gainShield() {
		shielding++;		
	}

	/**
	 * @param maxBullets the maxBullets to set
	 */
	public final void setMaxBullets(final int maxBullets) {
		this.maxBullets = maxBullets;
	}

	/**
	 * @return the maxBullets
	 */
	public static int getMaxBullets() {
		return MAX_BULLETS;
	}

	/**
	 * @return the hyperspaceStart
	 */
	public final long getHyperspaceStart() {
		return hyperspaceStart;
	}

	/**
	 * @param hyperspaceStart the hyperspaceStart to set
	 */
	public final void setHyperspaceStart(final long hyperspaceStart) {
		this.hyperspaceStart = hyperspaceStart;
	}

	/**
	 * @return the lastShot
	 */
	public final long getLastShot() {
		return lastShot;
	}

	/**
	 * @return the maxSpeed
	 */
	public static float getMaxSpeed() {
		return MAX_SPEED;
	}

	/**
	 * @param changeOfDying the changeOfDying to set
	 */
	public final void setChangeOfDying(final int changeOfDying) {
		this.changeOfDying = changeOfDying;
	}
}