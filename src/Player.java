import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity {
	private int lives;
	private double rotation;
	private long lastShot;

	private long invincibleStart;
	private int invincibleMS;
	private long hyperspaceStart;
	private boolean boost;

	/**
	 * Constructor for the Player class.
	 * 
	 * @param X
	 *            location of Player along the X-axis.
	 * @param Y
	 *            location of Player along the Y-axis.
	 * @param dX
	 *            velocity of Player along the X-axis.
	 * @param dY
	 *            velocity of Player along the Y-axis.
	 * @param thisGame
	 *            Game the Player exists in.
	 */
	public Player(float X, float Y, float dX, float dY, Game thisGame) {
		super(X, Y, dX, dY, thisGame);
		lives = 3;
		radius = 5;
		rotation = 0;
		invincibleStart(500);
	}

	/**
	 * Perform actions that happen when a player dies.
	 */
	public void die() {
		lives--;
		if (lives == 0) {
			thisGame.over();
			invincibleStart(500);
		} else {
			X = thisGame.getScreenX() / 2;
			Y = thisGame.getScreenY() / 2;
			dX = 0;
			dY = 0;
			rotation = 0;
			invincibleStart(500);
		}
	}

	@Override
	public void update(ArrayList<String> input) {
		X = X + dX;
		Y = Y + dY;
		slowDown();
		wrapAround();
		if (!invincible()) {
			keyHandler(input);
		}
	}

	/**
	 * Method that translates keyboard input into player character movement.
	 * 
	 * @param input
	 *            arraylist containing the keyboard input
	 */
	public void keyHandler(ArrayList<String> input) {
		if (input.contains("LEFT") && !input.contains("RIGHT")) {
			turnLeft();
		}

		if (input.contains("RIGHT") && !input.contains("LEFT")) {
			turnRight();
		}

		if (input.contains("UP")) {
			accelerate();
		}

		if (input.contains("DOWN")) {
			goHyperspace();
		}

		if (input.contains("SPACE")) {
			fire();
		}
	}

	/**
	 * Turn the player left.
	 */
	private void turnLeft() {
		rotation += .1;
	}

	/**
	 * Turn the player right.
	 */
	private void turnRight() {
		rotation -= .1;
	}

	/**
	 * Makes player move faster.
	 */
	private void accelerate() {
		dX += Math.cos(rotation) / 10;
		dY -= Math.sin(rotation) / 10;
		boost = true;
	}

	/**
	 * Makes player move slower.
	 */
	private void slowDown() {
		if (Math.abs(dX) + Math.abs(dY) != 0) {
			dX -= (.02 * dX) / (Math.abs(dX) + Math.abs(dY));
			dY -= (.02 * dY) / (Math.abs(dX) + Math.abs(dY));
		}
	}

	/**
	 * Starts invincibility that lasts the given amount of milliseconds.
	 * 
	 * @param milliseconds
	 *            amount of milliseconds the player should stay invicible.
	 */
	private void invincibleStart(int milliseconds) {
		invincibleStart = System.currentTimeMillis();
		invincibleMS = milliseconds;
	}

	/**
	 * @return whether or not the player is invincible at this moment.
	 */
	public boolean invincible() {
		return (invincibleStart + invincibleMS > System.currentTimeMillis());
	}

	/**
	 * Method to handle hyperspace mechanic.
	 */
	private void goHyperspace() {
		X = (float) (thisGame.getScreenX() * Math.random());
		Y = (float) (thisGame.getScreenY() * Math.random());
		dX = 0;
		dY = 0;
		invincibleStart(2000);
		hyperspaceStart = System.currentTimeMillis();
	}

	/**
	 * @return whether or not the player is in hyperspace at this moment.
	 */
	private boolean hyperspace() {
		return hyperspaceStart + invincibleMS > System.currentTimeMillis();
	}

	/**
	 * Method to handle firing bullets.
	 */
	private void fire() {
		if (System.currentTimeMillis() - lastShot > 200) {
			Bullet b = new Bullet(X, Y, dX + ((float) Math.sin(rotation + Math.PI / 2)) * 10,
					dY + ((float) Math.cos(rotation + Math.PI / 2)) * 10, thisGame);
			thisGame.create(b);
			lastShot = System.currentTimeMillis();
		}
	}

	/**
	 * Method to handle collisions of entities with the player.
	 */
	public void collide(Entity e2) {
		if (e2 instanceof Asteroid) {
			if (invincible() && !hyperspace()) {
				invincibleStart = System.currentTimeMillis();
			} else if (!invincible()) {
				((Asteroid) e2).split();
				this.die();
			}
		} else if (e2 instanceof Bullet && !((Bullet) e2).getFriendly()) {
			thisGame.destroy(e2);
			this.die();
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		drawLives(gc);

		double s1 = Math.sin(rotation);
		double c1 = Math.cos(rotation);

		double s2 = Math.sin(rotation + (Math.PI * 3 / 4));
		double c2 = Math.cos(rotation + (Math.PI * 3 / 4));

		double s3 = Math.sin(rotation + (Math.PI * 5 / 4));
		double c3 = Math.cos(rotation + (Math.PI * 5 / 4));

		gc.setStroke(Color.WHITE);
		if (invincible() && (System.currentTimeMillis() + invincibleMS) % 500 < 250) {
			gc.setStroke(Color.GREY);
		}
		if (hyperspace()) {
			gc.setStroke(Color.BLACK);
		}
		gc.setLineWidth(2);
		gc.strokePolygon(new double[] { X + 10 * c1, X + 10 * c2, X + 10 * c3 },
				new double[] { Y - 10 * s1, Y - 10 * s2, Y - 10 * s3 }, 3);

		if (boost) {
			double s4 = Math.sin(rotation + (Math.PI * 7 / 8));
			double c4 = Math.cos(rotation + (Math.PI * 7 / 8));

			double s5 = Math.sin(rotation + (Math.PI * 9 / 8));
			double c5 = Math.cos(rotation + (Math.PI * 9 / 8));

			double s6 = Math.sin(rotation + (Math.PI));
			double c6 = Math.cos(rotation + (Math.PI));
			gc.strokePolygon(new double[] { X + 9 * c4, X + 9 * c5, X + 12 * c6 },
					new double[] { Y - 9 * s4, Y - 9 * s5, Y - 12 * s6 }, 3);
			boost = false;
		}
	}

	/**
	 * Method to help display the amount of lives the player has left.
	 * 
	 * @param gc
	 *            graphicscontext
	 */
	private void drawLives(GraphicsContext gc) {
		for (int i = 0; i < lives; i++) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2);
			gc.strokePolygon(new double[] { 10 + 10 * i, 8 + 10 * i, 12 + 10 * i }, new double[] { 10, 18, 18 }, 3);
		}
	}
}