package entity;

import java.util.List;
import java.util.Random;

import display.DisplayEntity;
import entity.builders.BulletBuilder;
import game.Game;
import game.Logger;

/**
 * Class that represents a Boss. Moves like a saucer from one
 * side of the screen to the other until it's killed.
 * 
 * @author Dario
 *
 */
public class Boss extends AbstractEntity {
	private int toRight;
	private long shotTime;
	private int currentLives;
	private final Random random;
	private long dirChangeTime;
	private final BulletBuilder bBuilder;
	private static final double PATHS = 3;
	private static final double PATH_ANGLE = Math.PI / 4;
	private static final long CHANGE_DIR_TIME = 2000;
	private static final float RADIUS = 50;
	private static final float BULLET_SPEED = 4;
	private static final long SHOT_TIME = 1000;
	private static final int BULLETNUMBER = 5;
	private static final int STARTING_LIVES = 10;
	private static final double MULTI_SHOT_ANGLE = .1;
	private static final int SCORE = 20000;
	private static final float ACCURACY = 3;

	/**
	 * Constructor for boss.
	 * @param x location of boss along x-axis
	 * @param y location of boss along y-axis
	 * @param dX speed of boss along x-axis
	 * @param dY speed of boss along y-axis
	 * @param thisGame game the boss exists in
	 */
	public Boss(final float x, final float y, final float dX, final float dY, final Game thisGame) {
		super(x, y, dX, dY, thisGame);
		random = new Random();
		setRadius(RADIUS);
		dirChangeTime = System.currentTimeMillis();
		shotTime = dirChangeTime;
		currentLives = STARTING_LIVES;
		int nextToRight = 0;
		if (x > (getThisGame().getScreenX() / 2)) {
			nextToRight = 1;
		}
		setPath(nextToRight, random.nextInt((int) PATHS));
		
		// Initialize the Bullet Builder
		bBuilder = new BulletBuilder();
		bBuilder.setPierce(0);
		bBuilder.setThisGame(getThisGame());
		bBuilder.setShooter(this);
		bBuilder.setFriendly(false);
	}

	/**
	 * Calculate new position of BossAngryAsteroid, get it to shoot, get it to
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
	 * Change the boss's direction randomly at certain times in a random
	 * direction.
	 */
	private void changeDirection() {
		if (System.currentTimeMillis() - dirChangeTime > CHANGE_DIR_TIME) {
			dirChangeTime = System.currentTimeMillis();
			setPath(random.nextInt((int) PATHS));
		}
	}

	/**
	 * Set BossAngryAsteroid path.
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
	 * Set BossAngryAsteroid path.
	 * 
	 * @param path
	 *            - Low, mid or high path
	 */
	public final void setPath(final int path) {
		setPath(toRight, path);
	}

	/**
	 * Causes the BossAngryAsteroid to turn around when it reaches the edge of
	 * the screen in the X-direction.
	 */
	public final void checkEdgeX() {
		if (getX() > getThisGame().getScreenX() && getDX() > 0 
				|| getX() < 0 && getDX() < 0) {
			setDX(-getDX());
		}
	}
	
	/**
	 * Causes the BossAngryAsteroid to turn around when it reaches the edge of
	 * the screen in the Y-direction.
	 */
	public final void checkEdgeY() {
		if (getY() > getThisGame().getScreenY() && getDY() > 0 
				|| getY() < 0 && getDY() < 0) {
			setDY(-getDY());
		}
	}

	/**
	 * Set the direction of the Saucer, so change the dX and dY using direction.
	 * 
	 * @param direction
	 *            - the direction in radians, 0 being right
	 */
	public final void setDirection(final float direction) {
		setDX((float) Math.cos(direction) * 2);
		setDY((float) -Math.sin(direction) * 2);
	}

	/**
	 * Makes the Saucer shoot.
	 */
	private void shoot() {
		Player player = getThisGame().getPlayer();
		if (getThisGame().getGamestate().isCoop()) {
			if (random.nextInt(1) == 0) {
				player = getThisGame().getPlayerTwo().get();
			}
		} 
		if (player == null) {
			return;
		}
		if (player.invincible()) {
			shotTime = System.currentTimeMillis();
		} else {
			if (System.currentTimeMillis() - shotTime > SHOT_TIME) {
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

				for (int i = 0; i < BULLETNUMBER; i++) {
					fireBullet(shotDir - i * MULTI_SHOT_ANGLE);
				}
				shotTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * fire a bullet in a direction.
	 * 
	 * @param direction
	 *            - the direction
	 */
	private void fireBullet(final double direction) {
		bBuilder.setX(getX());
		bBuilder.setY(getY());
		bBuilder.setDX((float) (getDX() / 2 + Math.cos(direction) * BULLET_SPEED));
		bBuilder.setDY((float) (getDY() / 2 - Math.sin(direction) * BULLET_SPEED));
		final Bullet b = (Bullet) bBuilder.getResult();

		getThisGame().create(b);
	}

	@Override
	public final void draw() {
		DisplayEntity.boss(this);
	}

	@Override
	public final void collide(final AbstractEntity e2) {
		if (e2 instanceof Player && !((Player) e2).invincible()) {
			((Player) e2).onHit();
			Logger.getInstance().log("Player hit a Boss.");
		} else if (e2 instanceof Bullet && ((Bullet) e2).isFriendly()) {
			getThisGame().destroy(e2);
			currentLives--;
			if (currentLives < 1) {
				getThisGame().destroy(this);
			}
			Logger.getInstance().log("Boss was hit.");
		}
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
	 * Gets the starting lives of the boss.
	 * @return the amount of starting lives
	 */
	public final int getStartingLives() {
		return STARTING_LIVES;
	}
}
