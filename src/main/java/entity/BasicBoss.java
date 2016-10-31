package entity;

import display.DisplayEntity;
import game.Game;

import java.util.List;
import java.util.Random;

/**
 * Class that represents a BasicBoss. Moves like a saucer from one
 * side of the screen to the other until it's killed.
 * 
 * @author Dario
 *
 */
public class BasicBoss extends AbstractBoss {
	private int toRight;
	private final Random random;
	private long dirChangeTime;
	private long shotSpeed;
	private long shotTime;
	private static final double PATHS = 3;
	private static final double PATH_ANGLE = Math.PI / 4;
	private static final long CHANGE_DIR_TIME = 2000;
	private static final float RADIUS = 50;
	private static final float BULLET_SPEED = 4;
	private static final long SHOT_TIME = 1000;
	private static final int BULLETNUMBER = 5;
	private static final int STARTING_LIVES = 10;
	private static final double MULTI_SHOT_ANGLE = .1;
	private static final float ACCURACY = 3;
	private static final int SCORE = 20000;

	/**
	 * Constructor for BasicBoss.
	 * @param x location of BasicBoss along x-axis
	 * @param y location of BasicBoss along y-axis
	 * @param dX speed of BasicBoss along x-axis
	 * @param dY speed of BasicBoss along y-axis
	 * @param thisGame game the BasicBoss exists in
	 */
	public BasicBoss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		random = new Random();
		setRadius(RADIUS);
		dirChangeTime = System.currentTimeMillis();
		shotTime = dirChangeTime;
		setCurrentLives(STARTING_LIVES);
		int nextToRight = 0;
		if (x > (getThisGame().getScreenX() / 2)) {
			nextToRight = 1;
		}
		setPath(nextToRight, random.nextInt((int) PATHS));
		this.shotSpeed = SHOT_TIME;
		setBullets(BULLETNUMBER);
	}

	/**
	 * Calculate new position of BasicBoss, get it to shoot, get it to
	 * change direction and if it hits the wall get it to turn back.
	 * 
	 * @param input
	 *            - the pressed keys
	 */
	@Override
	public final void update(final List<String> input) {
		setX(getX() + getDX());
		setY(getY() + getDY());
		checkEdgeX();
		checkEdgeY();
		changeDirection();
		shoot();
	}

	/**
	 * Change the BasicBoss's direction randomly at certain times in a random
	 * direction.
	 */
	private void changeDirection() {
		if (System.currentTimeMillis() - dirChangeTime > CHANGE_DIR_TIME) {
			dirChangeTime = System.currentTimeMillis();
			setPath(random.nextInt((int) PATHS));
		}
	}

	/**
	 * Set BasicBoss path.
	 * 
	 * @param toRight
	 *            - Direction of Saucer
	 * @param path
	 *            - Low, mid or high path
	 */
	public final void setPath(final int toRight, final int path) {
		this.toRight = toRight;
		setDirection((float) (toRight * Math.PI + (path - 1) * PATH_ANGLE));
	}

	/**
	 * Set BasicBoss path.
	 * 
	 * @param path
	 *            - Low, mid or high path
	 */
	public final void setPath(final int path) {
		setPath(toRight, path);
	}

	/**
	 * Causes the BasicBoss to turn around when it reaches the edge of
	 * the screen in the X-direction.
	 */
	public final void checkEdgeX() {
		if (getX() > getThisGame().getScreenX() && getDX() > 0 
				|| getX() < 0 && getDX() < 0) {
			setDX(-getDX());
		}
	}
	
	/**
	 * Causes the BasicBoss to turn around when it reaches the edge of
	 * the screen in the Y-direction.
	 */
	public final void checkEdgeY() {
		if (getY() > getThisGame().getScreenY() && getDY() > 0 
				|| getY() < 0 && getDY() < 0) {
			setDY(-getDY());
		}
	}

	/**
	 * Set the direction of the BasicBoss, so change the dX and dY using direction.
	 * 
	 * @param direction
	 *            - the direction in radians, 0 being right
	 */
	public final void setDirection(final float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	/**
	 * Makes the BasicBoss shoot.
	 */
	@Override
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
	@Override
	public final void fireBullet(final double direction) {
		this.getbBuilder().setX(getX());
		this.getbBuilder().setY(getY());
		this.getbBuilder().setDX((float) (getDX() / 2 + Math.cos(direction) * BULLET_SPEED));
		this.getbBuilder().setDY((float) (getDY() / 2 - Math.sin(direction) * BULLET_SPEED));
		this.getbBuilder().setThisGame(getThisGame());
		this.getbBuilder().setShooter(this);
		this.getbBuilder().setFriendly(false);
		final Bullet b = (Bullet) getbBuilder().getResult();

		getThisGame().create(b);
	}

	@Override
	public final void draw() {
		DisplayEntity.boss(this);
	}

	@Override
	public final void onDeath() {
		getThisGame().getSpawner().setStartRest(System.currentTimeMillis());
		getThisGame().addScore(SCORE);
		Particle.explosion(getX(), getY(), getThisGame());
	}
	
	/**
	 * Gets the toRight of the class.
	 * @return the toRight
	 */
	public final int getToRight() {
		return toRight;
	}
	
	/**
	 * Gets the starting lives of the BasicBoss.
	 * @return the amount of starting lives
	 */
	public final int getStartingLives() {
		return STARTING_LIVES;
	}
	
	/**
	 * Gets the shot time of the BasicBoss.
	 * @return the shot time
	 */
	public final long getShotTime() {
		return shotTime;
	}
	
	/**
	 * Sets the shot time of the BasicBoss.
	 * @param time the shotTime to set
	 */
	public final void setShotTime(final long time) {
		this.shotTime = time;
	}
	
	/**
	 * Gets the shot speed of the BasicBoss.
	 * @return the shot speed
	 */
	public final long getShotSpeed() {
		return shotSpeed;
	}
	
	/**
	 * Sets the shot speed of the BasicBoss.
	 * @param speed the speed to set
	 */
	public final void setShotSpeed(final long speed) {
		this.shotSpeed = speed;
	}
}
