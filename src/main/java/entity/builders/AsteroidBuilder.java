package entity.builders;

import entity.AbstractEntity;
import entity.Asteroid;
import game.Game;

/**
 * A builder class that can build Asteroids.
 * 
 * @author Lukas
 *
 */
public class AsteroidBuilder implements EntityBuilder {

	private final Asteroid asteroid;
	
	/**
	 * Constructor for the AstroidBuilder class.
	 */
	public AsteroidBuilder() {
		asteroid = new Asteroid();
	}
	
	@Override
	public final void setX(final float x) {
		asteroid.setX(x);
	}

	@Override
	public final void setY(final float y) {
		asteroid.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		asteroid.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		asteroid.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		asteroid.setThisGame(thisGame);
	}

	/**
	 * @param radius the radius of the asteroid
	 */
	public final void setRadius(final float radius) {
		asteroid.setRadius(radius);
	}
	
	/**
	 * @param shape the shape of the asteroid
	 */
	public final void setShape(final int shape) {
		asteroid.setShape(shape);
	}
	
	@Override
	public final AbstractEntity getResult() {
		return asteroid.shallowCopy();
	}

}
