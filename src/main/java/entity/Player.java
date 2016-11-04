package entity;

import display.DisplayEntity;
import game.Audio;
import game.Logger;
import entity.cannons.PlayerCannon;
import entity.keyhandler.KeyHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

/**
 * This class is the player of the game.
 *
 * @author Kibo
 */
@Setter
@Getter
public class Player extends AbstractEntity {
	private int lives;
	private double rotation;
	private long invincibleStart;
	private int invincibleMS;
	private long hyperspaceStart;
	private boolean boost;
	private boolean playerTwo;
	private int shielding;
	private int chanceOfDying;
	private String playerString;
	private final PlayerCannon shooter;
	private final KeyHandler keyhandler;

	private static final int STARTING_LIVES = 3;
	private static final float RADIUS = 5;
	private static final int INVINCIBILITY_START_TIME = 1000;
	private static final double ROTATION_SPEED = .06;
	private static final double ACCELERATION = .04;
	private static final float DECELERATION = .01f;
	private static final int HYPERSPACE_TIME = 1000;
	private static final float MAX_SPEED = 4;
	private static final int CHANCE_OF_DYING = 25;
	private static final float SPAWN_OFFSET = 40;
    
    /**
     * create an uninitialized player with only the default player.
     */
    public Player() {
    	super();
    	lives = STARTING_LIVES;
		setRadius(RADIUS);
		rotation = 0;
    	playerTwo = false;
    	playerString = "The player";
    	makeInvincible(INVINCIBILITY_START_TIME);
		shielding = 0;
		chanceOfDying = CHANCE_OF_DYING;
		keyhandler = new KeyHandler(this);
		shooter = new PlayerCannon(this);
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
		getThisGame().getAudio().rocketBoost(this);
	}

	/**
	 * handle the player taking a hit (and is not invincible).
	 *
	 * this happens when (for example) the player collides with an asteroid
	 * or is hit by the bullet of an saucer.
	 */
	public final void onHit() {
		if (shielding < 1) {
			loseLife();		
		} else {
			shielding--;
			makeInvincible(INVINCIBILITY_START_TIME);
		}
	}
	
	/**
	 * Handles what happens when a player loses a life.
	 */
	private void loseLife() {
		lives--;
		if (lives <= 0) {
			getThisGame().over();
		} else {
			respawnThePlayer();
		}
	}

    /**
     * respawn the current player, accounts for multiplayer offsets.
     */
	private void respawnThePlayer() {
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

	/**
	 * add an additional live to the current player, if the player is dead they are respawned.
	 */
	public final void gainLife() {
		lives++;
		getThisGame().getAudio().play(Audio.LIFEUP);
		if (lives == 1) {
			getThisGame().create(this);
			respawnThePlayer();
			Logger.getInstance().log(playerString + " was resurrected.");
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
			keyhandler.update(input);
		}
		getThisGame().getAudio().rocketBoost(this);
	}

	/**
	 * Turn the player left.
	 */
	public final void turnLeft() {
		rotation += ROTATION_SPEED;
	}

	/**
	 * Turn the player right.
	 */
	public final void turnRight() {
		rotation -= ROTATION_SPEED;
	}

	/**
	 * Makes player move faster.
	 */
	public final void accelerate() {
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
			setDX(getDX() - (DECELERATION * getDX()) / (Math.abs(getDX()) + Math.abs(getDY())));
			setDY(getDY() - (DECELERATION * getDY()) / (Math.abs(getDX()) + Math.abs(getDY())));
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
	public final void goHyperspace() {
		final Random random = new Random();
		if (random.nextInt(chanceOfDying) == 0) {
			onHit();
			Logger.getInstance().log(playerString + " died in hyperspace.");
		} else {
			Logger.getInstance().log(playerString + " went into hyperspace.");
			setX((float) (getThisGame().getScreenX() * Math.random()));
			setY((float) (getThisGame().getScreenY() * Math.random()));
			setDX(0);
			setDY(0);
			makeInvincible(HYPERSPACE_TIME);
			hyperspaceStart = System.currentTimeMillis();
			getThisGame().getAudio().play(Audio.TELEPORT);
		}
	}

	/**
	 * Method to handle collisions of entities with the player.
	 * @param e2 - second AbstractEntity
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			hitByAsteroid((Asteroid) e2);
		} else if (e2 instanceof Bullet && !((Bullet) e2).isFriendly()) {
			hitByBullet((Bullet) e2);
		}
	}
	
	/**
	 * Handles player collision with bullet.
	 * @param e2 - bullet
	 */
	private void hitByBullet(final Bullet e2) {
		getThisGame().destroy(e2);
		onHit();
		Logger.getInstance().log(playerString + " was hit by a bullet.");
	}
	
	/**
	 * Handles player collision with asteroid.
	 * @param e2 - asteroid
	 */
	private void hitByAsteroid(final Asteroid e2) {
		if (invincible() && !hyperspace()) {
			invincibleStart = System.currentTimeMillis();
		} else if (!invincible()) {
			getThisGame().destroy(e2);
			onHit();
			Logger.getInstance().log(playerString + " was hit by an asteroid.");
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
		DisplayEntity.draw(this);
	}

	/**
	 * True if more than 0 lives.
	 * @return true if alive
	 */
	public final boolean isAlive() {
		return lives > 0;
	}
	
	/**
	 * @param playerTwo - true if the player is player two, false otherwise.
	 */
	public final void setPlayerTwo(final boolean playerTwo) {
		this.playerTwo = playerTwo;
		if (getThisGame().getGamestate().isCoop()) {
			if (playerTwo) {
				this.playerString = "Player 2";
			} else {
				this.playerString = "Player 1";
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
	 * @return the rotationSpeed
	 */
	public static double getRotationSpeed() {
		return ROTATION_SPEED;
	}

	/**

	 * gain a shield level.
	 */
	public final void gainShield() {
		shielding++;
	}

	/**

	 * @return the maxSpeed
	 */
	public static float getMaxSpeed() {
		return MAX_SPEED;
	}

	/**
	 * @return a shallow copy of the current player, useful for making two entities.
	 */
	public final Player shallowCopy() {
		final Player newPlayer = new Player();
		newPlayer.setX(this.getX());
		newPlayer.setY(this.getY());
		newPlayer.setDX(this.getDX());
		newPlayer.setDY(this.getDY());
		newPlayer.setThisGame(this.getThisGame());
		newPlayer.setPlayerTwo(this.isPlayerTwo());
		return newPlayer;
	}
}