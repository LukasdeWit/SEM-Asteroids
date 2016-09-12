package entity;
import java.util.List;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is a particle used in explosions.
 * @author Kibo
 *
 */
public class Particle extends Entity {
	/**
	 * The time of birth of this particle in miliseconds.
	 */
	private long birthTime;
	/**
	 * The draw size of the particle.
	 */
	private static final float SIZE = 1.5f;
	/**
	 * The lifetime of a particle.
	 */
	private static final long LIFETIME = 750;
	/**
	 * number of particles per explosion.
	 */
	private static final int EXPLOSION_PARTICLES = 10;
	/**
	 * Speed multiplier of particle.
	 */
	private static final float SPEED = .75f;
	

	/**
	 * Constructor of a particle.
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param dX - horizontal speed
	 * @param dY - verticle speed
	 * @param thisGame - the game this particle belongs to
	 */
	public Particle(final float x, final float y, 
			final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(1);
		birthTime = System.currentTimeMillis();
	}
	
	/**
	 * This function makes an explosion of particles.
	 * @param x - x coordinate of explosion
	 * @param y - y coordinate of explosion
	 * @param thisGame - the game this explosion is added to
	 */
	public static void explosion(final float x, final float y, 
			final Game thisGame) {
		for (int i = 0; i < EXPLOSION_PARTICLES; i++) {
			thisGame.create(randomParticle(x, y, thisGame));
		}
	}

	/**
	 * This method creates a random particle.
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param thisGame - the game this particle is added to
	 * @return the random particle
	 */
	private static Particle randomParticle(final float x, final float y, 
			final Game thisGame) {
		Particle p = new Particle(x, y,
				(float) (Math.random() - .5) * SPEED, 
				(float) (Math.random() - .5) * SPEED,
				thisGame);
		return p;
	}

	@Override
	public final void draw(final GraphicsContext gc) {
		float radius = getRadius();
		gc.setFill(Color.GREY);
		gc.fillOval(getX() - radius / SIZE, 
				getY() - radius / SIZE, 
				radius * SIZE, 
				radius * SIZE);
		
	}

	@Override
	public void collide(final Entity e2) {
		//doesn't collide
	}

	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > LIFETIME) {
			getThisGame().destroy(this);
		}
	}
}
