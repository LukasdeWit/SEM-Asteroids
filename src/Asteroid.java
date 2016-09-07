
public class Asteroid extends Entity{
	
	public Asteroid(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius=20;
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Player) {
			thisGame.destroy(this);
			((Player) e2).die();
		} else if (e2 instanceof Bullet) {
			thisGame.destroy(this);
			thisGame.destroy(e2);
		}
	}
}
