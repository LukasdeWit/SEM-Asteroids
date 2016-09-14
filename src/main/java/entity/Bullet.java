package entity;
import java.util.List;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends AbstractEntity {
	/**
	 * Time of creation.
	 */
	private final long birthTime;
	/**
	 * true if this bullet is shot by the player, 
	 * false if it can hit the player.
	 */
	private boolean friendly;
	/**
	 * Lifetime of a bullet in miliseconds.
	 */
	private static final long LIFETIME = 2000;
	/**
	 * Draw size of bullet.
	 */
	private static final float SIZE = 1.5f;
	/**
	 * Radius of bullet.
	 */
	private static final float RADIUS = 2;

	/**
	 * Constructor for the bullet class.
	 *
	 * @param x position of bullet along the x-axis
	 * @param y position of bullet along the y-axis
	 * @param dX velocity of bullet along the x-axis
	 * @param dY velocity of bullet along the y-axis
	 * @param thisGame game the bullet exists in
	 */
	public Bullet(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(RADIUS);
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	/**
	 * Calculate new position of Bullet.
	 * @param input - the pressed keys
	 */
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
	 * @return boolean that is true when bullet is friendly
	 */
	public final boolean isFriendly() {
		return friendly;
	}

	/**
	 * Set whether the bullet is friendly.
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
			getThisGame().destroy(this);
			getThisGame().destroy(e2);
		}
	}

	@Override
	public final void onDeath() {
		//no-op
	}

	/**
	 * Display bullet on screen.
	 */
	@Override
	public final void draw(final GraphicsContext gc) {
		final float radius = getRadius();
		gc.setFill(Color.WHITE);
		gc.fillOval(getX() - radius / SIZE,
				getY() - radius / SIZE,
				radius * SIZE,
				radius * SIZE);
	}
}