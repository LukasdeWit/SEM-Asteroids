package entity;
import game.Game;
import game.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

import abstractpowerup.AbstractPowerup;

/**
 * Class that represents a Powerup.
 * @author Dario
 */
public class Powerup extends AbstractEntity {
	/**
	 * Type of powerup, either 0 (additional life), 1 (shield), 2 (bullet size increase), 3 (triple-shot), 4 (piercing bullets) or 5 (minigun).
	 */
	private final int type;
	/**
	 * Number of powerup types.
	 */
	private final int TYPES = 4;
	/**
	 * Radius of a Powerup in pixels.
	 */
	private static final float RADIUS = 12;
	/**
	 * Size multiplier.
	 */
	private static final float SIZE = .25f;

	/**
	 * Constructor for the Powerup class.
	 * 
	 * @param x location of Powerup along the X-axis.
	 * @param y location of Powerup along the Y-axis.
	 * @param thisGame Game the Powerup exists in.
	 */
	public Powerup(final float x, final float y, final Game thisGame) {
		super(x, y, 0, 0, thisGame);
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
			if(type==0){
				((Player) e2).gainLife();
			}
			else{
				if(type==1){
					((Player) e2).giveShield();
				}
				else{
					((Player) e2).givePowerup(new AbstractPowerup(type));
				}
			}
			String poweruptype = "";
			switch(type){
				case 0: poweruptype = "extra life"; 
				break;
				case 1: poweruptype = "shield";
				break;
				case 2: poweruptype = "bullet size increase";
				break;
				case 3: poweruptype = "tripleshot";
				break;
				case 4: poweruptype = "piercing bullet";
				break;
				case 5: poweruptype = "minigun";
				break;
				
			}
			Logger.getInstance().log("Player collected a" + poweruptype + "powerup.");
			getThisGame().destroy(this);
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
	public final void draw(final GraphicsContext gc) {
		//simply drawn as a static circle
		final float radius = getRadius();
		gc.setFill(Color.WHITE);
		gc.fillOval(getX() - radius / SIZE,
				getY() - radius / SIZE,
				radius * SIZE,
				radius * SIZE);
	}

	@Override
	public void update(List<String> input) {
		//no-op
		
	}
}
