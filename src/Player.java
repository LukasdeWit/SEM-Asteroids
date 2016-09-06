
public class Player extends Entity{
	private int lives;
	
	public Player(float X, float Y, float dX, float dY) {
		super(X, Y, dX, dY);
		lives=3;
	}
	
	//TODO: a lot (turn, shoot, die, respawn, hyperspace)
}
