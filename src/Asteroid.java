
public class Asteroid extends Entity{
	
	public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=20;
	}

	public float getRadius() {
		return radius;
	}
}
