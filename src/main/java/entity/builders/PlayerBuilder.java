package entity.builders;

import entity.AbstractEntity;
import entity.Player;
import game.Game;

/**
 * A builder that builds new Player objects.
 * 
 * @author Lukas
 *
 */
public class PlayerBuilder implements EntityBuilder {

	private Player player;
	
	/**
	 * Constructor for the PlayerBuilder.
	 */
	public PlayerBuilder() {
		this.player = new Player();
	}
	
	@Override
	public final void setX(final float x) {
		player.setX(x);
	}

	@Override
	public final void setY(final float y) {
		player.setY(y);
	}

	@Override
	public final void setDX(final float dX) {
		player.setDX(dX);
	}

	@Override
	public final void setDY(final float dY) {
		player.setDY(dY);
	}

	@Override
	public final void setThisGame(final Game thisGame) {
		player.setThisGame(thisGame);
	}
	
	/**
	 * @param playerTwo - true if the player is playerTwo, false otherwise.
	 */
	public final void setPlayerTwo(final boolean playerTwo) {
		player.setPlayerTwo(playerTwo);
	}

	@Override
	public final AbstractEntity getResult() {
		/*
		 *  Return a shallow copy of the player, so the factory can be used
		 *  multiple times with the same settings.
		 */
		return player.shallowCopy();
	}

}
