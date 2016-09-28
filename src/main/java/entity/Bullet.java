package entity;
import java.util.List;

import game.Game;
import game.Launcher;
import game.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class that stores the information for a bullet.
 */
public class Bullet extends AbstractEntity {
	private final long birthTime;
	private boolean friendly;
	private Player player;
	
	private static final long LIFETIME = 2000;
	private static final float SIZE = .5f;
	private static final float RADIUS = 2;

	/**
	 * Constructor for the bullet class.
	 *
	 * @param x position of bullet along the x-axis
	 * @param y position of bullet along the y-axis
	 * @param dX velocity of bullet along the x-axis
	 * @param dY velocity of bullet along the y-axis
	 */
	public Bullet(final float x, final float y, 
			final float dX, final float dY) {
		super(x, y, dX, dY);
		setRadius(RADIUS);
		birthTime = System.currentTimeMillis();
		friendly = true;
	}

	/**
	 * Calculate new position of Bullet.
	 * @param input - the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > LIFETIME) {
			Game.getInstance().destroy(this);
		}
	}

	/**
	 * Get whether the bullet is friendly.
	 * @return boolean that is true when bullet is friendly
	 */
	public final boolean isFriendly() {
		return friendly;
	}

	/**
	 * Set whether the bullet is friendly.
	 * @param friendly value that is true when the bullet is friendly
	 */
	public final void setFriendly(final boolean friendly) {
		this.friendly = friendly;
	}

	/**
	 * Describes what happens when the bullet collides with entities.
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Asteroid) {
			Game.getInstance().destroy(this);
			Game.getInstance().destroy(e2);
			Logger.getInstance().log("Asteroid was hit by a bullet.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onDeath() {
		//no-op
	}

	/**
	 * Display bullet on screen.
	 */
	@Override
	public final void draw() {
		final float radius = getRadius();
		final Circle c = new Circle(0, 0, radius * SIZE);
		c.setFill(Color.WHITE);
		c.setTranslateX(getX());
		c.setTranslateY(getY());
		Launcher.getRoot().getChildren().add(c);
	}

	/**
	 * @return the player
	 */
	public final Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public final void setPlayer(final Player player) {
		this.player = player;
	}
}