import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends Entity {
	/**
	 * Time of creation.
	 */
	private long birthTime;
	/**
	 * true if this bullet is shot by the player, 
	 * false if it can hit the player.
	 */
	private boolean friendly;
	/**
	 * Lifetime of a bullet in miliseconds.
	 */
	private static final long LIFETIME = 0;

	/**
	 * Constructor for the bullet class.
	 * 
	 * @param x
	 *            position of bullet along the x-axis
	 * @param y
	 *            position of bullet along the y-axis
	 * @param dX
	 *            velocity of bullet along the x-axis
	 * @param dY
	 *            velocity of bullet along the y-axis
	 * @param thisGame
	 *            game the bullet exists in
	 */
	public Bullet(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(2);
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	/**
	 * Calculate new position of Bullet.
	 * @param input - the pressed keys
	 */
	public final void update(final ArrayList<String> input) {
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
	public final boolean getFriendly() {
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
	public final void collide(final Entity e2) {
		if (e2 instanceof Asteroid) {
			getThisGame().destroy(this);
			((Asteroid) e2).split();
		}
	}

	@Override
	public final void draw(final GraphicsContext gc) {
		float radius = getRadius();
		gc.setFill(Color.WHITE);
		gc.fillOval(getX() - radius / 2, 
				getY() - radius / 2, 
				radius * 2, 
				radius * 2);
	}
}