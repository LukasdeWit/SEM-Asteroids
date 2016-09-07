import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity {
	private int lives;
	private double rotation;

	public Player(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		lives = 3;
		radius = 10;
		rotation = Math.PI / 2;
	}

	public void die() {
		lives--;
		if (lives == 0) {
			thisGame.over();
		} else {
			X = thisGame.getScreenX() / 2;
			Y = thisGame.getScreenY() / 2;
			dX = 0;
			dY = 0;
			rotation = Math.PI / 2;
		}
	}

	@Override
	public void update(ArrayList<String> input) {
		X = X + dX;
		Y = Y + dY;
		wrapAround();
		keyHandler(input);
	}

	public void keyHandler(ArrayList<String> input) {
		if (input.contains("LEFT") && !input.contains("RIGHT")) {
			turnLeft();
		}

		if (input.contains("RIGHT") && !input.contains("LEFT")) {
			turnRight();
		}

		if (input.contains("UP")) {
			accelerate();
		} else {
			slowDown();
		}

		if (input.contains("DOWN")) {
			hyperspace();
		}

		if (input.contains("SPACE")) {
			fire();
		}
	}

	private void turnLeft() {
		System.out.println(rotation);
		rotation += .2;
	}

	private void turnRight() {
		System.out.println(rotation);
		rotation -= .2;
	}

	private void accelerate() {
		dX += (Math.cos(rotation) / 10);
		dY -= (Math.sin(rotation) / 10);
	}

	private void slowDown() {
		if ((Math.abs(dX) + Math.abs(dY)) != 0) {
			dX -= (.02 * dX) / (Math.abs(dX) + Math.abs(dY));
			dY -= (.02 * dY) / (Math.abs(dX) + Math.abs(dY));
		}
	}

	private void hyperspace() {
		// TODO: hyperspace
	}

	private void fire() {
		if (System.currentTimeMillis() - Bullet.lastbullet > 500) {
			Bullet b = new Bullet(X, Y, (float) Math.cos(rotation) + dX, (float) Math.sin(rotation) + dY, thisGame);
			thisGame.create(b);
			final ScheduledExecutorService scheduler =
				     Executors.newScheduledThreadPool(1);
			final Runnable destroyer = new Runnable() {
				public void run() {
					thisGame.destroy(b);
				}
			};
			scheduler.schedule(destroyer, 1000, TimeUnit.MILLISECONDS);
		}
	}

	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			thisGame.destroy(e2);
			this.die();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		double s1 = Math.sin(rotation);
		double c1 = Math.cos(rotation);

		double s2 = Math.sin(rotation + (Math.PI * 3 / 4));
		double c2 = Math.cos(rotation + (Math.PI * 3 / 4));

		double s3 = Math.sin(rotation + (Math.PI * 5 / 4));
		double c3 = Math.cos(rotation + (Math.PI * 5 / 4));

		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.strokePolygon(new double[] { X + 10 * c1, X + 10 * c2, X + 10 * c3 },
				new double[] { Y - 10 * s1, Y - 10 * s2, Y - 10 * s3 }, 3);
		// gc.fillOval(X - radius / 2, Y - radius / 2, radius*2, radius*2);
	}
}
