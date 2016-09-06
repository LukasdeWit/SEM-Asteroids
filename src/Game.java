import java.util.ArrayList;

public class Game {
	private static ArrayList<Entity> entities; 
	private float screenX;
	private float screenY;
	
	public Game(){
		//TODO:ScreenX and ScreenY initialization.
		startGame();
	}
	
	public void startGame(){
		entities.add(new Player(screenX/2,screenY/2,0,0));
		//TODO: the rest of entities: entities.add(new Asteroid())
	}
	
	public void update(){
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public static void destroy(Entity e){
		entities.remove(e);
	}
}
