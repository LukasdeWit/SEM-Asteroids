package entity;
import display.DisplayEntity;
import game.Game;
import game.Logger;

import java.util.List;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends AbstractEntity {
	private long birthTime;
	private boolean friendly;
	private Player player;
	private int piercing = 1;

	private static final long LIFETIME = 2000;
	private static final float RADIUS = 2;

	/**
	 * Constructor for the bullet class.
	 *
	 * @param x        position of bullet along the x-axis
	 * @param y        position of bullet along the y-axis
	 * @param dX       velocity of bullet along the x-axis
	 * @param dY       velocity of bullet along the y-axis
	 * @param thisGame Game the bulelt exists in.
	 */
	public Bullet(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(RADIUS);
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	/**
	 * Constructor for the bullet class.
	 *
	 * @param x        position of bullet along the x-axis
	 * @param y        position of bullet along the y-axis
	 * @param dX       velocity of bullet along the x-axis
	 * @param dY       velocity of bullet along the y-axis
	 * @param pierce   the number of asteroids this
	 * @param thisGame Game the bullet exists in.
	 *                 bullet should be capable of piercing
	 */
	public Bullet(final float x, final float y, final float dX, final float dY, final int pierce,
				  final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(RADIUS);
		birthTime = System.currentTimeMillis();
		friendly = true;
		piercing = pierce;
	}

	/**
	 * Calculate new position of Bullet.
	 *
	 * @param input the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > LIFETIME) {
			getThisGame().destroy(this);
		}
	}

	/**
	 * Get whether the bullet is friendly.
	 *
	 * @return boolean that is true when bullet is friendly
	 */
	public final boolean isFriendly() {
		return friendly;
	}

	/**
	 * Set whether the bullet is friendly.
	 *
	 * @param friendly value that is true when the bullet is friendly
	 */
	public final void setFriendly(final boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * Describes what happens when the bullet collides with entities.
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			if (piercing < 2) {
				getThisGame().destroy(this);
			} else {
				piercing--;
			}
			getThisGame().destroy(e2);
			Logger.getInstance().log("Asteroid was hit by a bullet.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onDeath() {
		//no-op
	}

	/**
	 * DisplayText bullet on screen.
	 */
	@Override
	public final void draw() {
		DisplayEntity.bullet(this);
	}

	/**
	 * @return the player
	 */
	public final Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public final void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * @param birthTime the birthTime to set
	 */
	public final void setBirthTime(final long birthTime) {
		this.birthTime = birthTime;
	}
}