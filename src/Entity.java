import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

/**
 * This is the superclass of all entities in the game.
 * 
 * @author Kibo
 *
 */
public abstract class Entity {
  // Location
  protected float X;
  protected float Y;
  // Speed
  protected float dX;
  protected float dY;

  protected float radius;
  protected Game thisGame;

  /**
   * Constructor for the Entity class.
   * 
   * @param X
   *          location of Entity along the X-axis.
   * @param Y
   *          location of Entity along the Y-axis.
   * @param dX
   *          velocity of Entity along the X-axis.
   * @param dY
   *          velocity of Entity along the Y-axis.
   * @param thisGame
   *          Game the Entity exists in.
   */
  public Entity(float X, float Y, float dX, float dY, Game thisGame) {
    this.X = X;
    this.Y = Y;
    this.dX = dX;
    this.dY = dY;
    this.thisGame = thisGame;
  }

  public abstract void update(ArrayList<String> input);

  public abstract void draw(GraphicsContext gc);

  /**
   * Function that moves entities to the other side of the screen when they
   * reach the edge.
   */
  public void wrapAround() {
    if (X < 0) {
      X += thisGame.getScreenX();
    }
    if (X > thisGame.getScreenX()) {
      X -= thisGame.getScreenX();
    }
    if (Y < 0) {
      Y += thisGame.getScreenY();
    }
    if (Y > thisGame.getScreenY()) {
      Y -= thisGame.getScreenY();
    }
  }

  /**
   * Calculate distance between 2 Entities.
   * 
   * @param e1
   *          first Entity
   * @param e2
   *          second Entity
   * @return float containing the distance between the Entities.
   */
  public static float distance(Entity e1, Entity e2) {
    return (float) Math.sqrt(Math.pow(e1.X - e2.X, 2) + Math.pow(e1.Y - e2.Y, 2));
  }

  /**
   * Check whether or not Entities are colliding.
   * 
   * @param e1
   *          first Entity
   * @param e2
   *          second Entity
   * @return boolean that is true when entities collide
   */
  public static boolean collision(Entity e1, Entity e2) {
    return (e1.radius + e2.radius) > distance(e1, e2);
  }

  /**
   * Function that describes how the Entity behaves when colliding with another.
   * 
   * @param e2
   *          Entity to be collided with.
   */
  public abstract void collide(Entity e2);
}
