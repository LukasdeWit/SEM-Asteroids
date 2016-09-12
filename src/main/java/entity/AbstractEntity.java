package entity;
import java.util.List;

import game.Game;
import javafx.scene.canvas.GraphicsContext;

/**
 * This is the superclass of all entities in the game.
 * 
 * @author Kibo
 *
 */
public abstract class AbstractEntity {
	/**
	 * X coordinate of AbstractEntity.
	 */
	private float x;
	/**
	 * Y coordinate of AbstractEntity.
	 */
	private float y;
	/**
	 * Horizontal speed.
	 */
	private float dX;
	/**
	 * Vertical speed.
	 */
	private float dY;
	/**
	 * Radius of AbstractEntity, used for collision.
	 */
	private float radius;
	

	/**
	 * The Game this AbstractEntity belongs to.
	 */
	private Game thisGame;

	/**
	 * Constructor for the AbstractEntity class.
	 * 
	 * @param x location of AbstractEntity along the X-axis.
	 * @param y location of AbstractEntity along the Y-axis.
	 * @param dX velocity of AbstractEntity along the X-axis.
	 * @param dY velocity of AbstractEntity along the Y-axis.
	 * @param thisGame Game the AbstractEntity exists in.
	 */
	public AbstractEntity(final float x, final float y,
                          final float dX, final float dY, final Game thisGame) {
		this.setX(x);
		this.setY(y);
		this.setDX(dX);
		this.setDY(dY);
		this.setThisGame(thisGame);
	}

	/**
	 * Method to calculate new position of entity.
	 * 
	 * @param input
	 *            the keyboard input of the player
	 */
	public abstract void update(List<String> input);

	/**
	 * Method that helps show the entity on the screen.
	 * 
	 * @param gc
	 *            graphicscontext
	 */
	public abstract void draw(GraphicsContext gc);

	/**
	 * Function that moves entities to the other side of the screen when they
	 * reach the edge.
	 */
	public final void wrapAround() {
		if (getX() < 0) {
			setX(getX() + getThisGame().getScreenX());
		}
		if (getX() > getThisGame().getScreenX()) {
			setX(getX() - getThisGame().getScreenX());
		}
		if (getY() < 0) {
			setY(getY() + getThisGame().getScreenY());
		}
		if (getY() > getThisGame().getScreenY()) {
			setY(getY() - getThisGame().getScreenY());
		}
	}

	/**
	 * Calculate distance between 2 Entities.
	 * 
	 * @param e1 first AbstractEntity
	 * @param e2 second AbstractEntity
	 * @return float containing the distance between the Entities.
	 */
	public static float distance(final AbstractEntity e1, final AbstractEntity e2) {
		return (float) Math.sqrt(Math.pow(e1.getX() - e2.getX(), 2) 
				+ Math.pow(e1.getY() - e2.getY(), 2));
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
	 * Function that describes how the AbstractEntity behaves when colliding with
	 * another.
	 * 
	 * @param e2
	 *            AbstractEntity to be collided with.
	 */
	public abstract void collide(AbstractEntity e2);
	
	/**
	 * dY getter.
	 * @return dY
	 */
	public final float getDY() {
		return dY;
	}
	
	/**
	 * dY setter.
	 * @param dY - dY
	 */
	public final void setDY(final float dY) {
		this.dY = dY;
	}
	
	/**
	 * dX getter.
	 * @return dX
	 */
	public final float getDX() {
		return dX;
	}
	
	/**
	 * dX setter.
	 * @param dX - dX
	 */
	public final void setDX(final float dX) {
		this.dX = dX;
	}
	
	/**
	 * Radius getter.
	 * @return radius
	 */
	public final float getRadius() {
		return radius;
	}

	/**
	 * Radius setter.
	 * @param radius - radius
	 */
	public final void setRadius(final float radius) {
		this.radius = radius;
	}
	
	/**
	 * thisGame getter.
	 * @return thisGame
	 */
	public final Game getThisGame() {
		return thisGame;
	}
	
	/**
	 * thisGame setter.
	 * @param thisGame - thisGame
	 */
	public final void setThisGame(final Game thisGame) {
		this.thisGame = thisGame;
	}

	/**
	 * @return the x
	 */
	public final float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(final float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public final float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(final float y) {
		this.y = y;
	}
}
