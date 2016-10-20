package entity.builders;

import entity.AbstractEntity;
import entity.Particle;
import game.Game;

/**
 * A builder class that can build Particles.
 * 
 * @author Lukas
 *
 */
public class ParticleBuilder implements EntityBuilder {

	private final Particle particle;
	
	/**
	 * Constructor for the ParticleBuilder class.
	 */
	public ParticleBuilder() {
		particle = new Particle();
	}
	
	@Override
	public final void setX(final float x) {
		particle.setX(x);
	}

	@Override
	public final void setY(final float y) {
		particle.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		particle.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		particle.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		particle.setThisGame(thisGame);
	}

	@Override
	public final AbstractEntity getResult() {
		final Particle temp = particle.shallowCopy();
		temp.setBirthTime(System.currentTimeMillis());
		return temp;
	}

}
