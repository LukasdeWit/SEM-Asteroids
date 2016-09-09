import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends Entity {
	private long birthTime;
	private boolean friendly;

	/**
	 * Constructor for the bullet class.
	 * 
	 * @param X
	 *            position of bullet along the x-axis
	 * @param Y
	 *            position of bullet along the y-axis
	 * @param dX
	 *            velocity of bullet along the x-axis
	 * @param dY
	 *            velocity of bullet along the y-axis
	 * @param thisGame
	 *            game the bullet exists in
	 */
	public Bullet(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		setRadius(2);
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	/**
	 * Calculate new position of Bullet.
	 */
	public void update(ArrayList<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > 1000) {
			getThisGame().destroy(this);
		}
	}

	/**
	 * Get whether the bullet is friendly.
	 * @return boolean that is true when bullet is friendly
	 */
	public boolean getFriendly() {
		return friendly;
	}

	/**
	 * Set whether the bullet is friendly
	 * @param friendly value that is true when the bullet is friendly
	 */
	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * Describes what happens when the bullet collides with entities.
	 */
	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			getThisGame().destroy(this);
			((Asteroid) e2).split();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		float radius = getRadius();
		gc.setFill(Color.WHITE);
		gc.fillOval(getX() - radius / 2, getY() - radius / 2, radius * 2, radius * 2);
	}
}