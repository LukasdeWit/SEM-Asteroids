package entity;
import java.util.List;
import java.util.Random;

import abstractpowerup.AbstractPowerup;
import display.DisplayEntity;
import game.Game;
import game.Logger;

/**
 * Class that represents a Powerup.
 * @author Dario
 */
public class Powerup extends AbstractEntity {
	private final int type;
	private static final int TYPES = 4;
	private static final float RADIUS = 12;
	private static final int EXTRA_LIFE = 0;
	private static final int SHIELD = 1;
	private static final int BULLET_SIZE = 2;
	private static final int TRIPLE_SHOT = 3;
	private static final int PIERCING = 4;
	private static final int MINIGUN = 5;

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
		type = random.nextInt((int) TYPES);
	}

	/**
	 * Behaviour when a Powerup is hit by an entity.
	 *
	 * @param e2 entity this Powerup collided with
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Player) {
			if (type == 0) {
				((Player) e2).gainLife();
			} else {
				if (type == 1) {
					((Player) e2).giveShield();
				} else {
					((Player) e2).givePowerup(new AbstractPowerup(type));
				}
			}
			String poweruptype = "";
			switch(type) {
				case EXTRA_LIFE: poweruptype = "extra life"; 
				break;
				case SHIELD: poweruptype = "shield";
				break;
				case BULLET_SIZE: poweruptype = "bullet size increase";
				break;
				case TRIPLE_SHOT: poweruptype = "tripleshot";
				break;
				case PIERCING: poweruptype = "piercing bullet";
				break;
				case MINIGUN: poweruptype = "minigun";
				break;
			default:
				break;
				
			}
			Logger.getInstance().log("Player collected a" +	poweruptype + "powerup.");
			Game.getInstance().destroy(this);
		}
	}

	@Override
	public final void onDeath() {
		//no-op
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void draw() {
		DisplayEntity.powerup(this);
	}

	@Override
	public void update(final List<String> input) {
		//no-op
		
	}
}
