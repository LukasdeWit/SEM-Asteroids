package entity.builders;

import entity.AbstractEntity;
import entity.Boss;
import game.Game;

/**
 * A builder class that can build Bosses.
 * 
 * @author Lukas
 *
 */
public class BossBuilder implements EntityBuilder {

	private final Boss boss;
	
	/**
	 * Constructor for the boss builder.
	 */
	public BossBuilder() {
		boss = new Boss();
	}
	
	@Override
	public final void setX(final float x) {
		boss.setX(x);
	}

	@Override
	public final void setY(final float y) {
		boss.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		boss.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		boss.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		boss.setThisGame(thisGame);
	}
	
	/**
	 * @param radius the radius of the boss
	 */
	public final void setRadius(final float radius) {
		boss.setRadius(radius);
	}
	
	/**
	 * @param toRight the toRight of the boss
	 */
	public final void setToRight(final int toRight) {
		boss.setToRight(toRight);
	}
	
	/**
	 * @param currentLives the current amount of lives of the boss
	 */
	public final void setCurrentLives(final int currentLives) {
		boss.setCurrentLives(currentLives);
	}

	@Override
	public final AbstractEntity getResult() {
		return boss.shallowCopy();
	}

}
