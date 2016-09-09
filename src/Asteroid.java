import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends Entity {
	/**
	 * Shape of Asteroid, either 0, 1 or 2.
	 */
	private int shape;
	/**
	 * X coordinates of shape 0.
	 */
	private final int[] xShape0 = { -2, 0, 2, 4, 3, 4, 1, 0, -2, -4, -4, -4 };
	/**
	 * Y coordinates of shape 0.
	 */
	private final int[] yShape0 = { -4, -2, -4, -2, 0, 2, 4, 4, 4, 2, 0, -2 };
	/**
	 * X coordinates of shape 1.
	 */
	private final int[] xShape1 = { -2, 0, 2, 4, 2, 4, 2, -1, -2, -4, -3, -4 };
	/**
	 * Y coordinates of shape 1.
	 */
	private final int[] yShape1 = { -4, -3, -4, -2, -1, 0, 3, 2, 3, 1, 0, -2 };
	/**
	 * X coordinates of shape 2.
	 */
	private final int[] xShape2 = { -2, 1, 4, 4, 2, 4, 2, 1, -2, -4, -4, -1 };
	/**
	 * Y coordinates of shape 2.
	 */
	private final int[] yShape2 = { -4, -4, -2, -1, 0, 2, 4, 3, 4, 1, -2, -2 };

	/**
	 * Constructor for the Asteroid class.
	 * 
	 * @param X
	 *            location of Asteroid along the X-axis.
	 * @param Y
	 *            location of Asteroid along the Y-axis.
	 * @param dX
	 *            velocity of Asteroid along the X-axis.
	 * @param dY
	 *            velocity of Asteroid along the Y-axis.
	 * @param thisGame
	 *            Game the Asteroid exists in.
	 */
	public Asteroid(final float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius = 20;
		shape = (int) (Math.random() * 3);
	}

	/**
	 * Get radius of Asteroid.
	 * 
	 * @return float containing the Radius.
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Set a new radius for the Asteroid.
	 * 
	 * @param radius
	 *            float containing the new radius.
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * Calculate new position of Asteroid.
	 */
	public void update(ArrayList<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
	}

	/**
	 * Behaviour when Asteroid collides with entities.
	 */
	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			split();
			((Player) e2).die();
		} else if (e2 instanceof Bullet) {
			split();
			getThisGame().destroy(e2);
		}
	}

	/**
	 * Split asteroid into 2 small ones, or if it's too small destroy it.
	 */
	public void split() {
		if (radius == 20) {
			getThisGame().addAsteroid(getX(), getY(), (float) (getDX() + Math.random() - .5), (float) (getDY() + Math.random() - .5), 12);
			getThisGame().addAsteroid(getX(), getY(), (float) (getDX() + Math.random() - .5), (float) (getDY() + Math.random() - .5), 12);
			getThisGame().addScore(20);
			getThisGame().destroy(this);
		} else if (radius == 12) {
			getThisGame().addAsteroid(getX(), getY(), (float) (getDX() + Math.random() * 2 - 1), (float) (getDY() + Math.random() - .5), 4);
			getThisGame().addAsteroid(getX(), getY(), (float) (getDX() + Math.random() * 2 - 1), (float) (getDY() + Math.random() - .5), 4);
			getThisGame().addScore(50);
			getThisGame().destroy(this);
		} else {
			getThisGame().addScore(100);
			getThisGame().destroy(this);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		double[] XShape = new double[12];
		double[] YShape = new double[12];
		if (shape == 0) {
			for (int i = 0; i < 12; i++) {
				XShape[i] = xShape0[i] * (radius / 4) + getX();
				YShape[i] = yShape0[i] * (radius / 4) + getY();
			}
		} else if (shape == 1) {
			for (int i = 0; i < 12; i++) {
				XShape[i] = xShape1[i] * (radius / 4) + getX();
				YShape[i] = yShape1[i] * (radius / 4) + getY();
			}
		} else if (shape == 2) {
			for (int i = 0; i < 12; i++) {
				XShape[i] = xShape2[i] * (radius / 4) + getX();
				YShape[i] = yShape2[i] * (radius / 4) + getY();
			}
		}
		gc.strokePolygon(XShape, YShape, 12);
	}
}
