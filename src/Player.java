
public class Player extends Entity{
	private int lives;
	
	
	public Player(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		lives=3;
		radius=10;
	}
	
	public void die(){
		lives--;
		if (lives==0){
			thisGame.over();
		} else {
			super.X=thisGame.getScreenX()/2;
			super.Y=thisGame.getScreenY()/2;
		}
	}
	
	@Override
	public void update(){
		X=X+dX;
		Y=Y+dY;
		wrapAround();
		//TODO: a lot (turn, shoot, die, respawn, hyperspace)
	}
}
