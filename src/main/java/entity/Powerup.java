package entity;
import display.DisplayEntity;
import display.DisplayText;
import game.Game;
import game.Logger;

import java.util.List;
import java.util.Random;

/**
 * Class that represents a Powerup.
 * @author Dario
 */
public class Powerup extends AbstractEntity {
	private int type;
	
	private long startTime;
	private long pickupTime;

	private Player player;

	private static final long PERISH_TIME = 10000;
	private static final int POWERUP_DURATION = 5000;
	
	private static final int TYPES = 6;
	private static final float RADIUS = 12;
	
	private static final int EXTRA_LIFE = 0;
	private static final int SHIELD = 1;
	private static final int BULLET_SIZE = 2;
	private static final int TRIPLE_SHOT = 3;
	private static final int PIERCING = 4;
	private static final int MINIGUN = 5;
	
	private static final String[] TYPE_STRING = {
			"an extra life", 
			"a shield", 
			"a bullet size increase", 
			"a tripleshot", 
			"a piercing bullet", 
			"a minigun"
	};

	private static final float NEW_BULLET_SIZE = 6;
	private static final int NEW_PIERCING_LEVEL = 3;
	private static final long NEW_FIRE_RATE = 50;
	private static final int TRIPLE_SHOT_BULLETS = Player.getMaxBullets() * 3;
	private static final int MINIGUN_BULLETS = Player.getMaxBullets() * 4;

	/**
	 * Constructor for the Powerup class.
	 * 
	 * @param x location of Powerup along the X-axis.
	 * @param y location of Powerup along the Y-axis.
	 * @param thisGame the game this particle belongs to
	 */
	public Powerup(final float x, final float y, final Game thisGame) {
		super(x, y, 0, 0, thisGame);
		final Random random = new Random();
		setRadius(RADIUS);
		type = random.nextInt(TYPES);
		startTime = System.currentTimeMillis();
		pickupTime = 0;
	}

	/**
	 * Behaviour when a Powerup is hit by an entity.
	 *
	 * @param e2 entity this Powerup collided with
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Player && pickupTime == 0) {
			pickup((Player) e2);
			Logger.getInstance().log("Player collected " + TYPE_STRING[type] + " powerup.");
		}
	}

	/**
	 * activate on pickup of player.
	 * @param p - the player
	 */
	private void pickup(final Player p) {
		player = p;
		pickupTime = System.currentTimeMillis();
		switch(type) {
			case EXTRA_LIFE: 
				p.gainLife(); 
				getThisGame().destroy(this);
				break;
			case SHIELD: 
				p.gainShield();
				getThisGame().destroy(this);
				break;
			case BULLET_SIZE: 
				p.setBulletSize(NEW_BULLET_SIZE);
				break;
			case TRIPLE_SHOT:
				p.setTripleShot(true);
				p.setMaxBullets(TRIPLE_SHOT_BULLETS);
				break;
			case PIERCING:
				p.setPiercing(NEW_PIERCING_LEVEL);
				break;
			case MINIGUN:
			default:
				p.setFireRate(NEW_FIRE_RATE);
				p.setMaxBullets(MINIGUN_BULLETS);
				break;
		}
	}
	
	@Override
	public final void onDeath() {
		//no-op
	}

	@Override
	public final void draw() {
		if (pickupTime == 0) {
			DisplayEntity.powerup(this);
		} else {
			DisplayText.powerup(this);
		}
	}

	@Override
	public final void update(final List<String> input) {
		if (pickupTime == 0) {
			if (PERISH_TIME < (System.currentTimeMillis() - startTime)) {
				getThisGame().destroy(this);
	 		} 
		} else if (POWERUP_DURATION < (System.currentTimeMillis() - pickupTime)) {
			runOut();
		}
	}
	
	/**
	 * Run out.
	 */
	private void runOut() {
		if (player == null) {
			Logger.getInstance().log("ERROR | No player was linked to this powerup for runOut().");
			getThisGame().destroy(this);
			return;
		}
		switch(type) {
			case BULLET_SIZE: 
				player.setBulletSize(Player.getBulletSize());
				break;
			case TRIPLE_SHOT:
				player.setTripleShot(false);
				player.setMaxBullets(Player.getMaxBullets());
				break;
			case PIERCING:
				player.setPiercing(1);
				break;
			case MINIGUN:
			default:
				player.setFireRate(Player.getFireRate());
				player.setMaxBullets(Player.getMaxBullets());
				break;
		}
		getThisGame().destroy(this);
	}

	/**
	 * @return the player
	 */
	public final Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public final void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * @param pickupTime the pickupTime to set
	 */
	public final void setPickupTime(final long pickupTime) {
		this.pickupTime = pickupTime;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final int type) {
		this.type = type;
	}

	/**
	 * @return the newBulletSize
	 */
	public static final float getNewBulletSize() {
		return NEW_BULLET_SIZE;
	}

	/**
	 * @return the newPiercingLevel
	 */
	public static final int getNewPiercingLevel() {
		return NEW_PIERCING_LEVEL;
	}

	/**
	 * @return the newFireRate
	 */
	public static final long getNewFireRate() {
		return NEW_FIRE_RATE;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public final void setStartTime(final long startTime) {
		this.startTime = startTime;
	}
}
