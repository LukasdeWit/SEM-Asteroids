import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends Entity {
	private long birthTime;
	private boolean friendly;

	public Bullet(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius = 2;
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	public void update(ArrayList<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > 1000) {
			getThisGame().destroy(this);
		}
	}

	public boolean getFriendly() {
		return friendly;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			getThisGame().destroy(this);
			((Asteroid) e2).split();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillOval(getX() - radius / 2, getY() - radius / 2, radius * 2, radius * 2);
	}
}