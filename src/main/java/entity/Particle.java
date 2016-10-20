package entity;
import java.util.List;

import display.DisplayEntity;
import entity.builders.ParticleBuilder;
import game.Game;

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
	 * Empty constructor of a Particle.
	 */
	public Particle() {
		super();
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
		ParticleBuilder pBuilder = new ParticleBuilder();
		pBuilder.setX(x);
		pBuilder.setY(y);
		pBuilder.setDX((float) (Math.random() - .5) * SPEED);
		pBuilder.setDY((float) (Math.random() - .5) * SPEED);
		pBuilder.setThisGame(thisGame);
		return (Particle) pBuilder.getResult();
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
	
	/**
	 * @return a shallow copy of the Particle, useful for making two copies
	 */
	public final Particle shallowCopy() {
		final Particle temp = new Particle();
		temp.setX(this.getX());
		temp.setY(this.getY());
		temp.setDX(this.getDX());
		temp.setDY(this.getDY());
		temp.setThisGame(this.getThisGame());
		temp.setBirthTime(this.birthTime);
		return temp;
	}
}
