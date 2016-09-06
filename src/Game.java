import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
	private ArrayList<Entity> entities; 
	private float screenX;
	private float screenY;
	private GraphicsContext gc;
		
	public Game(GraphicsContext gc){
		this.gc=gc;
		screenX=1024;
		screenY=512;
		entities=new ArrayList<Entity>();
		startGame();
	}
	
	public void startGame(){
		entities.add(new Player(screenX/2,screenY/2,0,0, this));
		entities.add(new Asteroid(0,screenY*(float)Math.random(),(float)Math.random()*2-1,(float)Math.random()*2-1, this));
		entities.add(new Asteroid(0,screenY*(float)Math.random(),(float)Math.random()*2-1,(float)Math.random()*2-1, this));
		entities.add(new Asteroid(0,screenY*(float)Math.random(),(float)Math.random()*2-1,(float)Math.random()*2-1, this));
		entities.add(new Asteroid(0,screenY*(float)Math.random(),(float)Math.random()*2-1,(float)Math.random()*2-1, this));
	}
	
	public void update(){
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenX, screenY);
		for (Entity e : entities) {
			e.update();
			checkCollision(e);
			
			gc.setFill(Color.WHITE);
			gc.fillOval(e.X-e.radius/2, e.Y-e.radius/2, e.radius, e.radius);
			
		}
	}
	
	public void checkCollision(Entity e1){
		for (Entity e2 : entities) {
			if (!e1.equals(e2)){
				if (e1 instanceof Player) {
					if (e1 instanceof Player) {
						//should never happen
					} else if (e1 instanceof Asteroid) {
						if (Entity.distance(e1,e2)<e1.radius+e2.radius){
							destroy(e2);
							((Player)e1).die();
						}
					} else if (e1 instanceof Bullet) {
						//nothing
					} else {
						//should never happen.
					}
				} else if (e1 instanceof Asteroid) {
					if (e1 instanceof Player) {
						if (Entity.distance(e1,e2)<e1.radius+e2.radius){
							destroy(e1);
							((Player)e2).die();
						}
					} else if (e1 instanceof Asteroid) {
						//nothing
					} else if (e1 instanceof Bullet) {
						if (Entity.distance(e1,e2)<e1.radius+e2.radius){
							destroy(e1);
							destroy(e2);
						}
					} else {
						//should never happen.
					}
				} else if (e1 instanceof Bullet) {
					if (e1 instanceof Player) {
						//nothing
					} else if (e1 instanceof Asteroid) {
						if (Entity.distance(e1,e2)<e1.radius+e2.radius){
							destroy(e1);
							destroy(e2);
						}
					} else if (e1 instanceof Bullet) {
						//nothing
					} else {
						//should never happen.
					}
				} else {
					//should never happen.
				}
			}
		}
	}
	
	public void draw(Entity e){
		//TODO: draw e
	}	
	
	public void destroy(Entity e){
		entities.remove(e);
	}

	public float getScreenX() {
		return screenX;
	}
	
	public float getScreenY() {
		return screenY;
	}

	public void over() {
		//game over
	}
}
