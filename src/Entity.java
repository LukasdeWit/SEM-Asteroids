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

	public Entity(float X, float Y, float dX, float dY, Game thisGame) {
		this.X = X;
		this.Y = Y;
		this.dX = dX;
		this.dY = dY;
		this.thisGame = thisGame;
	}

	public abstract void update(ArrayList<String> input);
	
	public abstract void draw(GraphicsContext gc);

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

	public static float distance(Entity e1, Entity e2) {
		return (float) Math.sqrt(Math.pow((e1.X - e2.X), 2) + Math.pow((e1.Y - e2.Y), 2));
	}

	public static boolean collision(Entity e1, Entity e2) {
		return (e1.radius + e2.radius) > distance(e1, e2);
	}

  public abstract void collide(Entity e2);
}
