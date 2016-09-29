package game;

import entity.Asteroid;
import entity.Powerup;
import entity.Saucer;

/**
 * This class takes care of spawning in new Asteroids and Saucer's.
 * @author Kibo
 *
 */
public class Spawner {
	/**
	 * The Game this spawner belongs to.
	 */
	private final Game thisGame;
	/**
	 * Start time of saucer timer in ms.
	 */
	private long startSaucerTime;
	/**
	 * Start time of powerup timer in ms.
	 */
	private long startPowerupTime;
	/**
	 * start time of rest in ms.
	 */
	private long startRest;
	/**
	 * The spawn wave of the game.
	 */
	private int wave;
	/**
	 * Time between saucers in ms.
	 */
	private static final long SAUCER_TIME = 20000;
	/**
	 * Time between powerups in ms.
	 */
	private static final long POWERUP_TIME = 10000;
	/**
	 * Rest time in ms.
	 */
	private static final long REST = 4000;
	/**
	 * Number of starting asteroids.
	 */
	private static final int STARTING_ASTEROIDS = 4;
	/**
	 * The maximum number of extra (other than starting) asteroids.
	 */
	private static final int MAX_EXTRA = 7;
	/**
	 * Step per difficulty level of score.
	 */
	private static final long DIFFICULTY_STEP = 10000;
	/**
	 * Max difficulty score.
	 */
	private static final long MAX_DIFFICULTY_SCORE = 10 * DIFFICULTY_STEP;
	/**
	 * Speed multiplier of initial Asteroids.
	 */
	private static final float ASTEROID_SPEED = 1;
	
	
	/**
	 * Constructor of Spawner.
	 * @param game - the game this spawner belongs to
	 */
	public Spawner(final Game game) {
		thisGame = game;
		startSaucerTime = System.currentTimeMillis();
		startPowerupTime = System.currentTimeMillis();
		startRest = 0;
		wave = 0;
	}

	/**
	 * This method is called every tick.
	 */
	public final void update() {
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
		if (thisGame.enemies() != 0) {
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
	 * adds a Saucer with random Y, side of screen, path and size.
	 */
	private void spawnSaucer() {
		final Saucer newSaucer = new Saucer(thisGame.getRandom().nextInt(1)
				* 2 * thisGame.getScreenX(), (float) Math.random()
				* thisGame.getScreenY(), 0, 0, thisGame);
		if (Math.random() < smallSaucerRatio()) {
			newSaucer.setRadius(Saucer.getSmallRadius());
		}
		thisGame.create(newSaucer);
	}
	
	/**
	 * adds a Powerup with random X and Y and type.
	 */
	private void spawnPowerup() {
		thisGame.create(new Powerup(thisGame.getScreenY()
				* (float) Math.random(),
				thisGame.getScreenY() 
				* (float) Math.random(), 
				thisGame));
	}

	/**
	 * Calculates the ratio of small saucers.
	 * @return the ratio
	 */
	private double smallSaucerRatio() {
		if (thisGame.getScore() < DIFFICULTY_STEP) {
			return .5;
		} else if (thisGame.getScore() < MAX_DIFFICULTY_SCORE) {
			return .5 + ((thisGame.getScore() / (double) DIFFICULTY_STEP)
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
			thisGame.create(new Asteroid(0, thisGame.getScreenY() 
					* (float) Math.random(), 
					(float) (Math.random() - .5) * ASTEROID_SPEED, 
					(float) (Math.random() - .5) * ASTEROID_SPEED, thisGame));
		}
		Logger.getInstance().log(times + " asteroids were spawned.");
	}

	/**
	 * reset.
	 */
	public final void reset() {
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
	public final int getWave() {
		return wave;
	}
}
