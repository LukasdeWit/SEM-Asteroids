import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Asteroid extends Entity{
	private int shape; //either 0, 1 or 2.
	private int size=5; //either 5, 3 or 1.
	
	private final int[] XShape0={-2,  0,  2,  4,  3,  4,  1,  0, -2, -4, -4, -4};
	private final int[] YShape0={-4, -2, -4, -2,  0,  2,  4,  4,  4,  2,  0, -2};
	private final int[] XShape1={-2,  0,  2,  4,  2,  4,  2, -1, -2, -4, -3, -4};
	private final int[] YShape1={-4, -3, -4, -2, -1,  0,  3,  2,  3,  1,  0, -2};
	private final int[] XShape2={-2,  1,  4,  4,  2,  4,  2,  1, -2, -4, -4, -1};
	private final int[] YShape2={-4, -4, -2, -1,  0,  2,  4,  3,  4,  1, -2, -2};
	
	public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=20;
		shape=(int) (Math.random()*3);
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
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		double[] XShape = new double[12];
		double[] YShape = new double[12];
		if (shape==0){
			for (int i = 0; i < 12; i++) {
				XShape[i]=XShape0[i]*size+X;
				YShape[i]=YShape0[i]*size+Y;
			}
		} else if (shape==1) {
			for (int i = 0; i < 12; i++) {
				XShape[i]=XShape1[i]*size+X;
				YShape[i]=YShape1[i]*size+Y;
			}
		} else if (shape==2) {
			for (int i = 0; i < 12; i++) {
				XShape[i]=XShape2[i]*size+X;
				YShape[i]=YShape2[i]*size+Y;
			}
		}
		gc.strokePolygon(XShape, YShape, 12);
	}
}
