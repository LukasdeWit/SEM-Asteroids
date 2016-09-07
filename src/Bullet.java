
public class Bullet extends Entity{
	
	public Bullet(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=5;
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			thisGame.destroy(this);
			thisGame.destroy(e2);
		}
	}
}