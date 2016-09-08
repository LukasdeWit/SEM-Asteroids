import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
	private ArrayList<Entity> entities;
	private ArrayList<Entity> destroyList;
	private ArrayList<Entity> createList;
	private ArrayList<Entity> addList;
	private float screenX;
	private float screenY;
	private GraphicsContext gc;
	private long restartTime;

	public Game(GraphicsContext gc) {
		this.gc = gc;
		screenX = 500;
		screenY = 500;
		entities = new ArrayList<Entity>();
		addList = new ArrayList<Entity>();
		destroyList = new ArrayList<Entity>();
		createList = new ArrayList<Entity>();
		startGame();
	}

	public void startGame() {
		restartTime=System.currentTimeMillis();
		entities.clear();
		entities.add(new Player(screenX / 2, screenY / 2, 0, 0, this));
		addRandomAsteroid(4);
		addRandomUFO();
	}
	
	public void addRandomUFO(){
		create(new UFO(((int)(Math.random()*2))*screenX,(float)Math.random()*screenY,0,0,this));
	}
	
	public void addRandomAsteroid(int times){
		for (int i = 0; i < times; i++) {
			entities.add(new Asteroid(0, screenY * (float) Math.random(), (float) Math.random() * 4 - 2,
				(float) Math.random() * 4 - 2, this));
		}
	}
	
	public void addAsteroid(float X, float Y, float dX, float dY, float radius){
		Asteroid newAsteroid = new Asteroid(X, Y, dX, dY, this);
		newAsteroid.setRadius(radius);
		addList.add(newAsteroid);
	}

	public void update(ArrayList<String> input) {
		if (input.contains("R")&&System.currentTimeMillis()-restartTime>300){
			startGame();
		}
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenX, screenY);
		for (Entity e : entities) {
			e.update(input);
			checkCollision(e);
			e.draw(gc);
		}
		entities.removeAll(destroyList);
		entities.addAll(createList);
		
		for (Entity destroyEntity : destroyList) {
			entities.remove(destroyEntity);
		}
		for (Entity addEntity : addList) {
			entities.add(addEntity);
		}
		addList.clear();
		destroyList.clear();
		createList.clear();
	}

	public void checkCollision(Entity e1){
		for (Entity e2 : entities) {
			if (!e1.equals(e2) && Entity.collision(e1, e2) && !destroyList.contains(e1) && !destroyList.contains(e2)){
				e1.collide(e2);
			}
		}
	}
	
	public void draw(Entity e) {
		// TODO: draw e
	}

	public void destroy(Entity e) {
		destroyList.add(e);
	}
	
	public void create(Entity e) {
		createList.add(e);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}

	public float getScreenX() {
		return screenX;
	}

	public float getScreenY() {
		return screenY;
	}

	public void over() {
		for (Entity entity : entities) {
			if (entity instanceof Player){
				destroy(entity);
			}
		}
	}
}
