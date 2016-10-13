package entity;
import display.DisplayEntity;
import game.Game;

import java.util.List;

/**
 * This class is a particle used in explosions.
 *
 * @author Kibo
 */
public class Particle extends AbstractEntity {
	private long birthTime;
	
	private static final long LIFETIME = 750;
	private static final int EXPLOSION_PARTICLES = 10;
	private static final float SPEED = .75f;

	/**
	 * Constructor of a particle.
	 *
	 * @param x        x coordinate
	 * @param y        y coordinate
	 * @param dX       horizontal speed
	 * @param dY       vertical speed
	 * @param thisGame the game this particle belongs to
	 */
	public Particle(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		setRadius(1);
		birthTime = System.currentTimeMillis();
	}

	/**
	 * This function makes an explosion of particles.
	 *
	 * @param x        x coordinate of explosion
	 * @param y        y coordinate of explosion
	 * @param thisGame the game this explosion is added to
	 */
	public static void explosion(final float x, final float y, final Game thisGame) {
		for (int i = 0; i < EXPLOSION_PARTICLES; i++) {
			thisGame.create(randomParticle(x, y, thisGame));
		}
	}

	/**
	 * This method creates a random particle.
	 *
	 * @param x        x coordinate
	 * @param y        y coordinate
	 * @param thisGame the game this particle belongs to
	 * @return the random particle
	 */
	private static Particle randomParticle(final float x, final float y, final Game thisGame) {
		return new Particle(x, y, (float) (Math.random() - .5) * SPEED, (float) (Math.random() - .5) * SPEED, thisGame);
	}

	/**
	 * draw a particle on the screen.
	 */
	@Override
	public final void draw() {
		DisplayEntity.particle(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void collide(final AbstractEntity e2) {
		//no-op
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onDeath() {
		//no-op
	}

	/**
	 * update the location of this particle, called every tick.
	 *
	 * @param input list of current key inputs this tick (not used)
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		wrapAround();
		if (System.currentTimeMillis() - birthTime > LIFETIME) {
			getThisGame().destroy(this);
		}
	}

	/**
	 * @return the explosionParticles
	 */
	public static int getExplosionParticles() {
		return EXPLOSION_PARTICLES;
	}

	/**
	 * @param birthTime the birthTime to set
	 */
	public final void setBirthTime(final long birthTime) {
		this.birthTime = birthTime;
	}
}
