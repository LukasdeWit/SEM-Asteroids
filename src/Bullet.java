import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Entity {
	static long lastbullet = 0;
	private long birthTime;

	public Bullet(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius = 5;
		lastbullet = System.currentTimeMillis();
		birthTime=System.currentTimeMillis();
	}

	public void update(ArrayList<String> input) {
		X = X + dX;
		Y = Y + dY;
		wrapAround();
		if (System.currentTimeMillis()-birthTime>1000){
			thisGame.destroy(this);
		}
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			thisGame.destroy(this);
			thisGame.destroy(e2);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillOval(X - radius / 2, Y - radius / 2, radius * 2, radius * 2);
	}
}