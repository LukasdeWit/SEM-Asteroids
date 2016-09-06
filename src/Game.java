import java.util.ArrayList;

public class Game {
	private ArrayList<Entity> entities; 
	private float screenX;
	private float screenY;
		
	public Game(){
		//TODO:ScreenX and ScreenY initialization.
		startGame();
	}
	
	public void startGame(){
		entities.add(new Player(screenX/2,screenY/2,0,0, this));
		entities.add(new Asteroid(0,screenY/3,2,3, this));
		entities.add(new Asteroid(0,2*screenY/3,3,-3, this));
		entities.add(new Asteroid(0,screenY/3,-3,2, this));
		entities.add(new Asteroid(0,2*screenY/3,-2,-2, this));
	}
	
	public void update(){
		for (Entity e : entities) {
			e.update();
			checkCollision(e);
			draw(e);
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
