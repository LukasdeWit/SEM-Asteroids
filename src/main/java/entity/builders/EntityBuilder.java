package entity.builders;

import entity.AbstractEntity;
import game.Game;

/**
 * Interface that describes the functionality for different entity builders.
 * 
 * @author Lukas
 *
 */
public interface EntityBuilder {

	/**
	 * @param x - value to set x as.
	 */
	void setX(float x);
	/**
	 * @param y - value to set y as.
	 */
	void setY(float y);
	/**
	 * @param dX - value to set dX as.
	 */
	void setDX(float dX);
	/**
	 * @param dY - value to set dY as.
	 */
	void setDY(float dY);
	/**
	 * @param thisGame - game this entity belongs to
	 */
	void setThisGame(Game thisGame);
	/**
	 * @return the entity built by the builder
	 */
	AbstractEntity getResult();
	
}
