import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UFO extends Entity{
	private int toRight; //general direction 1 is to right 0 is to left
	private long dirChangeTime;
	private final double[] XShape0={ 1,  2,  4,  2, -2, -4, -2, -1};
	private final double[] YShape0={-3, -1,  1,  3,  3,  1, -1, -3};
	
	public UFO(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=10;
		dirChangeTime=System.currentTimeMillis();
		setPath((X>(thisGame.getScreenX()/2))?1:0, (int)(Math.random()*3));
	}
	
	public void setPath(int toRight, int path){
		this.toRight=toRight;
		setDirection((float)(toRight*Math.PI+(path-1)*Math.PI/4));
	}
	
	public void setPath(int path){
		setPath(toRight,path);
	}
	
	public void setDirection(float direction){
		dX=(float) Math.cos(direction)*2;
		dY=(float) -Math.sin(direction)*2;
	}

	@Override
	public void update(ArrayList<String> input) {
		X=X+dX;
		Y=Y+dY;
		checkEnd();
		wrapAround();
		changeDirection();
	}
	
	private void checkEnd(){
		if (X>thisGame.getScreenX() || X<0){
			thisGame.destroy(this);
		}
	}

	private void changeDirection() {
		if (System.currentTimeMillis()-dirChangeTime>2000){
			dirChangeTime=System.currentTimeMillis();
			setPath((int)(Math.random()*3));
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		double[] XShape = new double[8];
		double[] YShape = new double[8];
		for (int i = 0; i < 8; i++) {
			XShape[i]=XShape0[i]*(radius*5/15)+X;
			YShape[i]=YShape0[i]*(radius*4/15)+Y;
		}
		gc.strokePolygon(XShape, YShape, 8);
		gc.strokeLine(XShape[1], YShape[1], XShape[6], YShape[6]);
		gc.strokeLine(XShape[2], YShape[2], XShape[5], YShape[5]);
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Player && !((Player)e2).invincable()) {
			((Player)e2).die();
			thisGame.destroy(this);
		} else if (e2 instanceof Bullet) {
			thisGame.destroy(e2);
			thisGame.destroy(this);
		} else if (e2 instanceof Asteroid) {
			((Asteroid)e2).split();
			thisGame.destroy(this);
		}
	}
}
