package entity.builders;

import entity.AbstractEntity;
import entity.Saucer;
import game.Game;

/**
 * A builder that builds new Saucer objects.
 * 
 * @author Lukas
 *
 */
public class SaucerBuilder implements EntityBuilder {

	private final Saucer saucer;
	
	/**
	 * Constructor for the SaucerBuilder.
	 */
	public SaucerBuilder() {
		this.saucer = new Saucer();
	}
	
	@Override
	public final void setX(final float x) {
		saucer.setX(x);
	}

	@Override
	public final void setY(final float y) {
		saucer.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		saucer.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		saucer.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		saucer.setThisGame(thisGame);
	}

	@Override
	public final AbstractEntity getResult() {
		Saucer temp = saucer.shallowCopy();
		return temp;
	}

}
