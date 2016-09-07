/**
 * Handles the timely creation and destruction of bullets.
 * 
 * @author Esmee
 *
 */
public class BulletTimer extends Thread {
	private float X;
	private float Y;
	private float dX;
	private float dY;
	private Game thisGame;

	public BulletTimer(float X, float Y, float dX, float dY, Game thisGame) {
		this.X = X;
		this.Y = Y;
		this.dX = dX;
		this.dY = dY;
		this.thisGame = thisGame;
	}

	public void run() {
		Bullet b = new Bullet(X, Y, dX, dY, thisGame);
		thisGame.create(b);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		thisGame.destroy(b);
	}

}
