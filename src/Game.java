import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class represents the most high-level information of the Game.
 */
public class Game {
  private ArrayList<Entity> entities;
  private ArrayList<Entity> destroyList;
  private ArrayList<Entity> addList;
  private float screenX;
  private float screenY;
  private GraphicsContext gc;

  /**
   * Constructor for Game class.
   * @param graphicsContext graphicscontext
   */
  public Game(GraphicsContext graphicsContext) {
    this.gc = graphicsContext;
    screenX = 1024;
    screenY = 512;
    entities = new ArrayList<Entity>();
    addList = new ArrayList<Entity>();
    destroyList = new ArrayList<Entity>();
    startGame();
  }

  /**
   * Empties entity list and adds only the starting entities.
   */
  public final void startGame() {
    entities.clear();
    entities.add(new Player(screenX / 2, screenY / 2, 0, 0, this));
    addRandomAsteroid(4);
  }

  /**
   * Add asteroids at random positions at the edge of the map.
   * @param times how many times an asteroid should be added.
   */
  public void addRandomAsteroid(int times) {
    for (int i = 0; i < times; i++) {
      entities.add(new Asteroid(0, screenY * (float) Math.random(), (float) Math.random() * 4 - 2,
          (float) Math.random() * 4 - 2, this));
    }
  }

  /**
   * Add a new asteroid to the game with the given parameters.
   * @param X position of asteroid along the x-axis.
   * @param Y position of asteroid along the y-axis.
   * @param dX velocity of asteroid along the x-axis.
   * @param dY velocity of asteroid along the y-axis.
   * @param radius radius of asteroid.
   */
  public void addAsteroid(float X, float Y, float dX, float dY, float radius) {
    Asteroid newAsteroid = new Asteroid(X, Y, dX, dY, this);
    newAsteroid.setRadius(radius);
    addList.add(newAsteroid);

  }

  /**
   * Change the situation of the game.
   */
  public void update(ArrayList<String> input) {
    if (input.contains("R")) {
      startGame();
    }
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, screenX, screenY);
    for (Entity e : entities) {
      e.update(input);
      checkCollision(e);
      e.draw(gc);
    }
    for (Entity destroyEntity : destroyList) {
      entities.remove(destroyEntity);
    }
    for (Entity addEntity : addList) {
      entities.add(addEntity);
    }
    addList.clear();
    destroyList.clear();
  }

  /**
   * Check if entities are colliding, and if so perform the appropriate collide function.
   * @param e1 Entity to be checked for collisions.
   */
  public void checkCollision(Entity e1) {
    for (Entity e2 : entities) {
      if (!e1.equals(e2) && Entity.collision(e1, e2)) {
        e1.collide(e2);
      }
    }
  }

  /**
   * ???.
   * @param e Entity
   */
  public void draw(Entity e) {
    // TODO: draw e
  }

  /**
   * Add an entity to be destroyed.
   * @param e Entity to be destroyed
   */
  public void destroy(Entity e) {
    destroyList.add(e);
  }

  /**
   * Retrieve the list of entities in the game.
   * @return ArrayList containing all entities currently in the game.
   */
  public ArrayList<Entity> getEntities() {
    return entities;
  }

  /**
   * Return length of screen in pixels along the x-axis.
   * @return float containing the amount of pixels.
   */
  public float getScreenX() {
    return screenX;
  }

  /**
   * Return length of screen in pixels along the y-axis.
   * @return float containing the amount of pixels.
   */
  public float getScreenY() {
    return screenY;
  }

  /**
   * Destroy player in case of game over.
   */
  public void over() {
    for (Entity entity : entities) {
      if (entity instanceof Player) {
        destroy(entity);
      }
    }
  }
}
