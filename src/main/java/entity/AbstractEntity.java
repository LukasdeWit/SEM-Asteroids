package entity;
import game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This is the superclass of all entities in the game.
 *
 * @author Kibo
 */
@Setter
@Getter
public abstract class AbstractEntity {
	private float x;
	private float y;
	private float dX;
	private float dY;
	private float radius;
	/**
	 * The Game this Entity belongs to.
	 */
	private Game thisGame;

	/**
	 * Constructor for the Entity class.
	 *
	 * @param x        location of AbstractEntity along the X-axis.
	 * @param y        location of AbstractEntity along the Y-axis.
	 * @param dX       velocity of AbstractEntity along the X-axis.
	 * @param dY       velocity of AbstractEntity along the Y-axis.
	 * @param thisGame Game the AbstractEntity exists in.
	 */
	public AbstractEntity(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		this.setX(x);
		this.setY(y);
		this.setDX(dX);
		this.setDY(dY);
		this.setThisGame(thisGame);
	}
	
	/**
	 * Constructor that leaves everything uninitialized.
	 */
	public AbstractEntity() {
		this.setX(0);
		this.setY(0);
		this.setDX(0);
		this.setDY(0);
		this.setThisGame(null);
	}

	/**
	 * Method to calculate new position of entity.
	 *
	 * @param input the keyboard input of the player
	 */
	public abstract void update(List<String> input);

	/**
	 * Method that helps show the entity on the screen.
	 */
	public abstract void draw();

	/**
	 * Function that moves entities to the other side of the screen when they
	 * reach the edge.
	 */
	public final void wrapAround() {
		setX((getX() + getThisGame().getScreenX()) % getThisGame().getScreenX());
		setY((getY() + getThisGame().getScreenY()) % getThisGame().getScreenY());
	}

	/**
	 * returns speed of entity.
	 *
	 * @return the speed
	 */
	public final float speed() {
		return (float) Math.sqrt(Math.pow(Math.abs(getDX()), 2) + Math.pow(Math.abs(getDY()), 2));
	}

	/**
	 * Calculate distance between 2 Entities.
	 *
	 * @param e1 first AbstractEntity
	 * @param e2 second AbstractEntity
	 * @return float containing the distance between the Entities.
	 */
	public static float distance(final AbstractEntity e1, final AbstractEntity e2) {
		return (float) Math.sqrt(Math.pow(e1.getX() - e2.getX(), 2) + Math.pow(e1.getY() - e2.getY(), 2));
	}

	/**
	 * Check whether or not Entities are colliding.
	 *
	 * @param e1 first AbstractEntity
	 * @param e2 second AbstractEntity
	 * @return boolean that is true when entities collide
	 */
	public static boolean collision(final AbstractEntity e1, final AbstractEntity e2) {
		return (e1.radius + e2.radius) > distance(e1, e2);
	}

	/**
	 * Function that describes how the AbstractEntity
	 * behaves when colliding with another.
	 *
	 * @param e2 AbstractEntity to be collided with.
	 */
	public abstract void collide(AbstractEntity e2);

	/**
	 * Describes what this entity does when it dies.
	 */
	public abstract void onDeath();

}
