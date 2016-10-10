package game;

import entity.Asteroid;
import entity.Powerup;
import entity.Saucer;

/**
 * This class takes care of spawning in new Asteroids and Saucer's.
 * @author Kibo
 *
 */
public final class Spawner {
	private long startSaucerTime;
	private long startPowerupTime;
	private long startRest;
	private int wave;
	
	private static final long SAUCER_TIME = 20000;
	private static final long POWERUP_TIME = 10000;
	private static final long REST = 4000;
	private static final int STARTING_ASTEROIDS = 4;
	private static final int MAX_EXTRA = 7;
	private static final long DIFFICULTY_STEP = 10000;
	private static final long MAX_DIFFICULTY_SCORE = 10 * DIFFICULTY_STEP;
	private static final float ASTEROID_SPEED = 1;
	/**
	 * The amount of points needed to spawn an additional asteroid in survival.
	 */
	private static final long SURVIVAL_POINTS_PER_ASTEROID = 10000;
	private static final Spawner INSTANCE = new Spawner();
	
	
	/**
	 * Constructor of Spawner.
	 */
	private Spawner() {
		startSaucerTime = System.currentTimeMillis();
		startPowerupTime = System.currentTimeMillis();
		startRest = 0;
		wave = 0;
	}
	
	/**
	 * getInstance of singleton.
	 * @return the gamestate
	 */
	public static Spawner getInstance() {
		return INSTANCE;
	}

	/**
	 * This method is called every tick of an arcade game.
	 */
	public void updateArcade() {
		if (System.currentTimeMillis() - startSaucerTime > SAUCER_TIME) {
			spawnSaucer();
			Logger.getInstance().log("Saucer was spawned");
			startSaucerTime = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - startPowerupTime > POWERUP_TIME) {
			spawnPowerup();
			Logger.getInstance().log("Powerup was spawned");
			startPowerupTime = System.currentTimeMillis();
		}
		if (Game.getInstance().enemies() != 0) {
			startRest = System.currentTimeMillis();
		}
		if (startRest == 0) {
			Logger.getInstance().log("Wave: " + (wave + 1) + ".");
			spawnAsteroid(STARTING_ASTEROIDS);
			startRest = System.currentTimeMillis();
			wave++;
		} else if (System.currentTimeMillis() - startRest > REST) {
			int extra = wave * 2;
			if (extra > MAX_EXTRA) {
				extra = MAX_EXTRA;
			}
			Logger.getInstance().log("Wave: " + (wave + 1) + ".");
			spawnAsteroid(STARTING_ASTEROIDS + extra);
			wave++;
			startRest = System.currentTimeMillis();
		}
	}
	
	/**
	 * This method is called every tick of a survival game.
	 */
	public void updateSurvival() {
		if (System.currentTimeMillis() - startSaucerTime > SAUCER_TIME) {
			spawnSaucer();
			startSaucerTime = System.currentTimeMillis();
		}
		if (Game.getInstance().enemies() != 0) {
			startRest = System.currentTimeMillis();
		}
		final int extra = (int) (Game.getInstance().getScore() 
				/ SURVIVAL_POINTS_PER_ASTEROID);
		final int enemies = Game.getInstance().convertedBigEnemies();
		spawnAsteroid(STARTING_ASTEROIDS + extra - enemies);
	}
	
	/**
	 * adds a Saucer with random Y, side of screen, path and size.
	 */
	private void spawnSaucer() {
		final Saucer newSaucer = new Saucer(Game.getInstance().getRandom().nextInt(1)
				* 2 * Game.getInstance().getScreenX(), (float) Math.random()
				* Game.getInstance().getScreenY(), 0, 0);
		if (Math.random() < smallSaucerRatio()) {
			newSaucer.setRadius(Saucer.getSmallRadius());
		}
		Game.getInstance().create(newSaucer);
	}
	
	/**
	 * adds a Powerup with random X and Y and type.
	 */
	private void spawnPowerup() {
		Game.getInstance().create(new Powerup(Game.getInstance().getScreenY()
				* (float) Math.random(),
				Game.getInstance().getScreenY() 
				* (float) Math.random()));
	}

	/**
	 * Calculates the ratio of small saucers.
	 * @return the ratio
	 */
	private double smallSaucerRatio() {
		if (Game.getInstance().getScore() < DIFFICULTY_STEP) {
			return .5;
		} else if (Game.getInstance().getScore() < MAX_DIFFICULTY_SCORE) {
			return .5 + ((Game.getInstance().getScore() / (double) DIFFICULTY_STEP)
					* .5 / (double) (MAX_DIFFICULTY_SCORE / DIFFICULTY_STEP));
		} else {
			return 1;
		}
	}

	/**
	 * adds asteroids with random Y, side of screen and direction, but with
	 * radius 20.
	 * 
	 * @param times
	 *            - the number of asteroids
	 */
	private void spawnAsteroid(final int times) {
		for (int i = 0; i < times; i++) {
			Game.getInstance().create(new Asteroid(0, Game.getInstance().getScreenY() 
					* (float) Math.random(), 
					(float) (Math.random() - .5) * ASTEROID_SPEED, 
					(float) (Math.random() - .5) * ASTEROID_SPEED));
		}
		Logger.getInstance().log(times + " asteroids were spawned.");
	}

	/**
	 * reset.
	 */
	public void reset() {
		wave = 0;
		startSaucerTime = System.currentTimeMillis();
		startRest = 0;
	}

	/**
	 * Getter for difficultyStep.
	 * @return the difficultyStep
	 */
	public static long getDifficultyStep() {
		return DIFFICULTY_STEP;
	}

	/**
	 * getter for wave.
	 * @return the wave
	 */
	public int getWave() {
		return wave;
	}
}
