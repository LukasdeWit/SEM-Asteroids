package entity;
import java.util.List;
import java.util.Random;

import display.DisplayEntity;
import display.DisplayText;
import game.Game;
import game.Logger;

/**
 * Class that represents a Powerup.
 * @author Dario
 */
public class Powerup extends AbstractEntity {
	private final int type;
	
	private final long startTime;
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
	 */
	public Powerup(final float x, final float y) {
		super(x, y, 0, 0);
		final Random random = new Random();
		setRadius(RADIUS);
		type = 3; //random.nextInt(TYPES);
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
				Game.getInstance().destroy(this);
				break;
			case SHIELD: 
				p.gainShield();
				Game.getInstance().destroy(this);
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
				p.setFireRate(NEW_FIRE_RATE);
				p.setMaxBullets(MINIGUN_BULLETS);
				break;
			default:
				Game.getInstance().destroy(this);
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
		if (pickupTime == 0 && PERISH_TIME < (System.currentTimeMillis() - startTime)) {
			Game.getInstance().destroy(this);
 		} else if (pickupTime != 0 && POWERUP_DURATION < (System.currentTimeMillis() - pickupTime)) {
			runOut();
		}
	}
	
	/**
	 * Run out.
	 */
	private void runOut() {
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
				player.setFireRate(Player.getFireRate());
				player.setMaxBullets(Player.getMaxBullets());
				break;
			default:
				break;
		}
		Game.getInstance().destroy(this);
	}

	/**
	 * @return the player
	 */
	public final Player getPlayer() {
		return player;
	}
}
