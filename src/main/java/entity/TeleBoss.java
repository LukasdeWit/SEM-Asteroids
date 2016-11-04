package entity;

import display.DisplayEntity;
import game.Game;

import java.util.List;
import java.util.Random;

/**
 * Class representing a randomly teleporting boss variant.
 * 
 * @author Dario
 *
 */
public class TeleBoss extends AbstractBoss {
	private long teleTime;
	private final Random random;
	private final long shotSpeed;
	private long shotTime;
	private static final long TELEPORT_TIME = 1800;
	private static final float BULLET_SPEED = 4;
	private static final long SHOT_TIME = 1000;
	private static final float RADIUS = 50;
	private static final int BULLETNUMBER = 5;
	private static final int STARTING_LIVES = 10;
	private static final double MULTI_SHOT_ANGLE = .1;
	private static final float ACCURACY = 3;
	private static final int SCORE = 20000;

	/**
	 * The constructor for DoubleBoss.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param dX speed in x-direction
	 * @param dY speed in y-direction
	 * @param thisGame the game
	 */
	public TeleBoss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		teleTime = System.currentTimeMillis();
		random = new Random();
		setRadius(RADIUS);
		this.shotTime = System.currentTimeMillis();
		setCurrentLives(STARTING_LIVES);
		this.shotSpeed = SHOT_TIME;
		setBullets(BULLETNUMBER);
	}

	/**
	 * The update method for TeleBoss.
	 */
	@Override
	public final void update(final List<String> input) {
		teleport();
		shoot();
	}

	/**
	 * The method that teleports a TeleBoss.
	 */
	private void teleport() {
		if (System.currentTimeMillis() - teleTime > TELEPORT_TIME) {
			teleTime = System.currentTimeMillis();
			Particle.explosion(getX(), getY(), getThisGame());
			setX((float) Math.random() * this.getThisGame().getScreenX());
			setY((float) Math.random() * this.getThisGame().getScreenY());
		}
	}
	
	/**
	 * Makes the TeleBoss shoot.
	 */
	protected final void shoot() {
		if (getThisGame().getPlayer() == null) {
			return;
		}
		if (getThisGame().getPlayer().invincible()) {
			this.shotTime = System.currentTimeMillis();
		} else {
			if (System.currentTimeMillis() - this.shotTime > this.shotSpeed) {
				final float playerX = getThisGame().getPlayer().getX();
				final float playerY = getThisGame().getPlayer().getY();
				final float randomRange = (float) (Math.PI * (Math.random() / ACCURACY));
				float straightDir;
				if (playerX > getX()) {
					straightDir = (float) Math.atan((playerY - getY()) / (playerX - getX()));
				} else {
					straightDir = (float) (Math.PI + Math.atan((playerY - getY()) / (playerX - getX())));
				}
				final float errorRight = (float) (random.nextInt(2) * 2 - 1);

				final float shotDir = straightDir + errorRight * randomRange;

				for (int i = 0; i < getBullets(); i++) {
					fireBullet(shotDir - i * MULTI_SHOT_ANGLE);
				}
				this.shotTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * fire a bullet in a direction.
	 * 
	 * @param direction
	 *            - the direction
	 */
	public final void fireBullet(final double direction) {
		this.getBBuilder().setX(getX());
		this.getBBuilder().setY(getY());
		this.getBBuilder().setDX((float) (getDX() / 2 + Math.cos(direction) * BULLET_SPEED));
		this.getBBuilder().setDY((float) (getDY() / 2 - Math.sin(direction) * BULLET_SPEED));
		this.getBBuilder().setThisGame(getThisGame());
		this.getBBuilder().setShooter(this);
		this.getBBuilder().setFriendly(false);
		final Bullet b = (Bullet) getBBuilder().getResult();

		getThisGame().create(b);
	}

	@Override
	public final void draw() {
		DisplayEntity.draw(this);
	}

	@Override
	public final void onDeath() {
		getThisGame().getSpawner().setStartRest(System.currentTimeMillis());
		getThisGame().addScore(SCORE);
		Particle.explosion(getX(), getY(), getThisGame());
	}
	
}
