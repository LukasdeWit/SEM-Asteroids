import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UFO extends Entity {
	private int toRight; // general direction 1 is to right 0 is to left
	private long dirChangeTime;
	private long shotTime;
	private final double[] XShape0 = { 1, 2, 4, 2, -2, -4, -2, -1 };
	private final double[] YShape0 = { -3, -1, 1, 3, 3, 1, -1, -3 };

	public UFO(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		radius = 10;
		dirChangeTime = System.currentTimeMillis();
		shotTime = dirChangeTime;
		setPath((X > (thisGame.getScreenX() / 2)) ? 1 : 0, (int) (Math.random() * 3));
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setPath(int toRight, int path) {
		this.toRight = toRight;
		setDirection((float) (toRight * Math.PI + (path - 1) * Math.PI / 4));
	}

	public void setPath(int path) {
		setPath(toRight, path);
	}

	public void setDirection(float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	@Override
	public void update(ArrayList<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		checkEnd();
		wrapAround();
		changeDirection();
		shoot();
	}

	private void shoot() {
		if (System.currentTimeMillis() - shotTime > 1000) {
			float randomDir = (float) (Math.random() * 2 * Math.PI);
			Bullet newBullet = new Bullet(getX(), getY(), getDX() + (float) Math.cos(randomDir) * 5,
					getDY() - (float) Math.sin(randomDir) * 5, getThisGame());
			newBullet.setFriendly(false);
			getThisGame().create(newBullet);
			shotTime = System.currentTimeMillis();
		}
	}

	private void checkEnd() {
		if (getX() > getThisGame().getScreenX() || getX() < 0) {
			getThisGame().destroy(this);
		}
	}

	private void changeDirection() {
		if (System.currentTimeMillis() - dirChangeTime > 2000) {
			dirChangeTime = System.currentTimeMillis();
			setPath((int) (Math.random() * 3));
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		double[] XShape = new double[8];
		double[] YShape = new double[8];
		for (int i = 0; i < 8; i++) {
			XShape[i] = XShape0[i] * (radius * 5 / 15) + getX();
			YShape[i] = YShape0[i] * (radius * 4 / 15) + getY();
		}
		gc.strokePolygon(XShape, YShape, 8);
		gc.strokeLine(XShape[1], YShape[1], XShape[6], YShape[6]);
		gc.strokeLine(XShape[2], YShape[2], XShape[5], YShape[5]);
	}

	@Override
	public void collide(Entity e2) {
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			((Player) e2).die();
			getThisGame().addScore((int) (200 + (radius % 2 * 800)));
			getThisGame().destroy(this);
		} else if (e2 instanceof Bullet && ((Bullet) e2).getFriendly()) {
			getThisGame().destroy(e2);
			getThisGame().addScore((int) (200 + (radius % 2 * 800)));
			getThisGame().destroy(this);
		} else if (e2 instanceof Asteroid) {
			((Asteroid) e2).split();
			getThisGame().addScore((int) (200 + (radius % 2 * 800)));
			getThisGame().destroy(this);
		}
	}
}
