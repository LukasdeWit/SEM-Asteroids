import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that represents an Asteroid.
 */
public class Asteroid extends Entity {
  private int shape; // either 0, 1 or 2.

  private final int[] XShape0 = { -2, 0, 2, 4, 3, 4, 1, 0, -2, -4, -4, -4 };
  private final int[] YShape0 = { -4, -2, -4, -2, 0, 2, 4, 4, 4, 2, 0, -2 };
  private final int[] XShape1 = { -2, 0, 2, 4, 2, 4, 2, -1, -2, -4, -3, -4 };
  private final int[] YShape1 = { -4, -3, -4, -2, -1, 0, 3, 2, 3, 1, 0, -2 };
  private final int[] XShape2 = { -2, 1, 4, 4, 2, 4, 2, 1, -2, -4, -4, -1 };
  private final int[] YShape2 = { -4, -4, -2, -1, 0, 2, 4, 3, 4, 1, -2, -2 };

  /**
   * Constructor for the Asteroid class.
   * 
   * @param X
   *          location of Asteroid along the X-axis.
   * @param Y
   *          location of Asteroid along the Y-axis.
   * @param dX
   *          velocity of Asteroid along the X-axis.
   * @param dY
   *          velocity of Asteroid along the Y-axis.
   * @param thisGame
   *          Game the Asteroid exists in.
   */
  public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
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
   *          float containing the new radius.
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  /**
   * Calculate new position of Asteroid.
   */
  public void update(ArrayList<String> input) {
    X = X + dX;
    Y = Y + dY;
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
      thisGame.destroy(e2);
    }
  }

  /**
   * Split asteroid into 2 small ones, or if it's too small destroy it.
   */
  public void split() {
    if (radius == 20) {
      thisGame.addAsteroid(X, Y, (float) (dX + Math.random() - .5), (float) (dY + Math.random() - .5), 12);
      thisGame.addAsteroid(X, Y, (float) (dX + Math.random() - .5), (float) (dY + Math.random() - .5), 12);
      thisGame.addScore(20);
      thisGame.destroy(this);
    } else if (radius == 12) {
      thisGame.addAsteroid(X, Y, (float) (dX + Math.random() * 2 - 1), (float) (dY + Math.random() - .5), 4);
      thisGame.addAsteroid(X, Y, (float) (dX + Math.random() * 2 - 1), (float) (dY + Math.random() - .5), 4);
      thisGame.addScore(50);
      thisGame.destroy(this);
    } else {
      thisGame.addScore(100);
      thisGame.destroy(this);
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
        XShape[i] = XShape0[i] * (radius / 4) + X;
        YShape[i] = YShape0[i] * (radius / 4) + Y;
      }
    } else if (shape == 1) {
      for (int i = 0; i < 12; i++) {
        XShape[i] = XShape1[i] * (radius / 4) + X;
        YShape[i] = YShape1[i] * (radius / 4) + Y;
      }
    } else if (shape == 2) {
      for (int i = 0; i < 12; i++) {
        XShape[i] = XShape2[i] * (radius / 4) + X;
        YShape[i] = YShape2[i] * (radius / 4) + Y;
      }
    }
    gc.strokePolygon(XShape, YShape, 12);
  }
}
