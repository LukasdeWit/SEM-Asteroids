import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
	private ArrayList<Entity> entities;
	private ArrayList<Entity> destroyList;
	private float screenX;
	private float screenY;
	private GraphicsContext gc;

	public Game(GraphicsContext gc) {
		this.gc = gc;
		screenX = 1024;
		screenY = 512;
		entities = new ArrayList<Entity>();
		destroyList = new ArrayList<Entity>();
		startGame();
	}

	public void startGame() {
		entities.clear();
		entities.add(new Player(screenX / 2, screenY / 2, 0, 0, this));
		addRandomAsteroid(4);
	}
	
	public void addRandomAsteroid(int times){
		for (int i = 0; i < times; i++) {
			entities.add(new Asteroid(0, screenY * (float) Math.random(), (float) Math.random() * 4 - 2,
				(float) Math.random() * 4 - 2, this));
		}
	}

	public void update(ArrayList<String> input) {
		if (input.contains("R")){
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
		destroyList.clear();
	}

	public void checkCollision(Entity e1){
		for (Entity e2 : entities) {
			if (!e1.equals(e2) && Entity.collision(e1, e2)){
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
