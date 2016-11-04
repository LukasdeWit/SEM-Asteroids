package entity;
import display.DisplayEntity;
import display.DisplayHud;
import entity.cannons.PlayerCannon;
import game.Audio;
import game.Game;
import game.Logger;
import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

/**
 * Class that represents a Powerup.
 *
 * @author Dario
 */
@Setter
@Getter
public class Powerup extends AbstractEntity {
	private int type;
	
	private long startTime;
	private long pickupTime;

	private Player player;

	private long glitterTime;

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

	private static final float NEW_BULLET_SIZE = 10;
	private static final int NEW_PIERCING_LEVEL = 3;
	private static final long NEW_FIRE_RATE = 50;
	private static final int TRIPLE_SHOT_BULLETS = PlayerCannon.getMaxBullets() * 3;
	private static final int MINIGUN_BULLETS = PlayerCannon.getMaxBullets() * 4;

	private static final String[] TYPE_STRING = {
			"an extra life",
			"a shield",
			"a bullet size increase",
			"a tripleshot",
			"a piercing bullet",
			"a minigun"
	};

	private static final long GLITTER_TIME = 500;
	
	/**
	 * Constructor for the Powerup class.
	 *
	 * @param x        location of Powerup along the X-axis.
	 * @param y        location of Powerup along the Y-axis.
	 * @param thisGame the game this particle belongs to
	 */
	public Powerup(final float x, final float y, final Game thisGame) {
		super(x, y, 0, 0, thisGame);
		final Random random = new Random();
		setRadius(RADIUS);
		type = random.nextInt(TYPES);
		startTime = System.currentTimeMillis();
		pickupTime = 0;
		glitterTime = 0;
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
			Logger.getInstance().log(player.getPlayerString() + " collected " + TYPE_STRING[type] + " powerup.");
        }
	}

	/**
	 * activate on pickup of player.
	 *
	 * @param p the player
	 */
	private void pickup(final Player p) {
		player = p;
		final PlayerCannon ps = p.getShooter();
		pickupTime = System.currentTimeMillis();
		getThisGame().getAudio().play(Audio.POWERUP);
		switch (type) {
			case EXTRA_LIFE:
				p.gainLife();
				break;
			case SHIELD:
				p.gainShield();
				break;
			case BULLET_SIZE:
				ps.setBulletSize(NEW_BULLET_SIZE);
				break;
			case TRIPLE_SHOT:
				ps.setTripleShot(true);
				ps.setMaxBullets(TRIPLE_SHOT_BULLETS);
				break;
			case PIERCING:
				ps.setPiercing(NEW_PIERCING_LEVEL);
				break;
			case MINIGUN:
			default:
				ps.setFireRate(NEW_FIRE_RATE);
				ps.setMaxBullets(MINIGUN_BULLETS);
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
			DisplayEntity.draw(this);
		} else {
			DisplayHud.powerup(this);
		}
	}

	@Override
	public final void update(final List<String> input) {
		if (GLITTER_TIME < (System.currentTimeMillis() - glitterTime) && pickupTime == 0) {
			Particle.explosion(getX(), getY(), getThisGame());
			glitterTime = System.currentTimeMillis();
		}
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
		final PlayerCannon ps = player.getShooter();
		switch(type) {
			case BULLET_SIZE: 
				ps.setBulletSize(PlayerCannon.getBulletSize());
				break;
			case TRIPLE_SHOT:
				ps.setTripleShot(false);
				ps.setMaxBullets(PlayerCannon.getMaxBullets());
				break;
			case PIERCING:
				ps.setPiercing(1);
				break;
			case MINIGUN:
			default:
				ps.setFireRate(PlayerCannon.getFireRate());
				ps.setMaxBullets(PlayerCannon.getMaxBullets());
				break;
		}
		getThisGame().destroy(this);
	}
    
	/**
	 * makes every type of powerup into a group for the hud.
	 * @return the group
	 */
    public final Group getPowerupShape() {
    	switch (getType()) {
		case EXTRA_LIFE:
			return DisplayHud.extraLifeGroup();
		case SHIELD:
			return DisplayHud.shieldGroup();
		case BULLET_SIZE:
			return DisplayHud.bulletSizeGroup();
		case TRIPLE_SHOT:
			return DisplayHud.tripleShotGroup();
		case PIERCING:
			return DisplayHud.piercingGroup();
		case MINIGUN:
		default:
			return DisplayHud.minigunGroup();
    	}
    }

	/**
	 * @return the newBulletSize
	 */
	public static float getNewBulletSize() {
		return NEW_BULLET_SIZE;
	}

	/**
	 * @return the newPiercingLevel
	 */
	public static int getNewPiercingLevel() {
		return NEW_PIERCING_LEVEL;
	}

	/**
	 * @return the newFireRate
	 */
	public static long getNewFireRate() {
		return NEW_FIRE_RATE;
	}

}
