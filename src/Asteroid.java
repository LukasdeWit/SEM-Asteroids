
public class Asteroid extends Entity{
	private float radius = 10;
	
	public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=10;
	}

	public float getRadius() {
		return radius;
	}
}
