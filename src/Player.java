import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity{
	private int lives;
	private int rotation;
	
	
	public Player(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		lives=3;
		radius=10;
		rotation=90;
	}
	
	public void die(){
		lives--;
		if (lives==0){
			thisGame.over();
		} else {
			X=thisGame.getScreenX()/2;
			Y=thisGame.getScreenY()/2;
			dX=0;
			dY=0;
			rotation=90;
		}
	}
	
	@Override
	public void update(ArrayList<String> input){
		X=X+dX;
		Y=Y+dY;
		wrapAround();
		keyHandler(input);		
	}
	
	public void keyHandler(ArrayList<String> input){
		if (input.contains("LEFT")&&!input.contains("RIGHT")){
			turnLeft();
		}
		
		if (input.contains("RIGHT")&&!input.contains("LEFT")){
			turnRight();
		}
		
		if (input.contains("UP")){
			accelerate();
		} else {
			slowDown();
		}
		
		if (input.contains("DOWN")){
			hyperspace();
		}
		
		if (input.contains("SPACE")){
			fire();
		}
	}

	private void turnLeft() {
		rotation+=.2;
		if (rotation>360) rotation-=360; 
	}

	private void turnRight() {
		rotation-=.2;
		if (rotation<0) rotation+=360; 
	}

	private void accelerate() {
		dX+=(Math.sin(rotation)/10);
		dY+=(Math.cos(rotation)/10);
	}

	private void slowDown(){
		//dX
	}
	
	private void hyperspace() {
		// TODO: hyperspace
	}

	private void fire() {
		// TODO: fire
	}

	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			thisGame.destroy(e2);
			this.die();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		double s=Math.sin(rotation);
		double c=Math.cos(rotation);
		gc.setStroke(Color.WHITE);
	    gc.setLineWidth(2);
		gc.strokePolygon(new double[]{X+10*s, X-8*s, X-8*s} , new double[]{Y, Y-6, Y+6} , 3);
		//gc.fillOval(X - radius / 2, Y - radius / 2, radius*2, radius*2);	
	}
}
