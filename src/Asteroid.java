import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Asteroid extends Entity{
	
	public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=20;
	}

	public float getRadius() {
		return radius;
	}

	public void update(ArrayList<String> input){
		X=X+dX;
		Y=Y+dY;
		wrapAround();
	}
	
	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Player) {
			thisGame.destroy(this);
			((Player) e2).die();
		} else if (e2 instanceof Bullet) {
			thisGame.destroy(this);
			thisGame.destroy(e2);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillOval(X - radius / 2, Y - radius / 2, radius*2, radius*2);	
	}
}
