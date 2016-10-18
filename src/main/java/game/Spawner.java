package game;

import java.util.Random;

import entity.Asteroid;
import entity.Boss;
import entity.DoubleBoss;
import entity.Powerup;
import entity.Saucer;
import entity.TeleBoss;

/**
 * This class takes care of spawning in new Asteroids and Saucer's.
 *
 * @author Kibo
 */
public final class Spawner {
	private long startSaucerTime;
	private long startPowerupTime;
	private long startRest;
	private int wave;
	private final double telebossratio;
	private final double doublebossratio;
	private final Random random;
	/**
	 * The Game this spawner belongs to.
	 */
	private final Game thisGame;

	private static final long SAUCER_TIME = 20000;
	private static final double TELE_RATIO = 0.3;
	private static final double DOUBLE_RATIO = 0.2;
	private static final long POWERUP_TIME = 10000;
	private static final long REST = 4000;
	private static final int STARTING_ASTEROIDS = 4;
	private static final int MAX_EXTRA = 7;
	private static final long DIFFICULTY_STEP = 10000;
	private static final long MAX_DIFFICULTY_SCORE = 10 * DIFFICULTY_STEP;
	private static final float ASTEROID_SPEED = 1;
	private static final float WAVES_BETWEEN_BOSSES = 5;

	/**
	 * Constructor of Spawner.
	 *
	 * @param game the game this particle belongs to
	 */
	public Spawner(final Game game) {
		thisGame = game;
		startSaucerTime = System.currentTimeMillis();
		startPowerupTime = System.currentTimeMillis();
		startRest = 0;
		wave = 0;
		random = new Random();
		telebossratio = TELE_RATIO;
		doublebossratio = DOUBLE_RATIO;
	}

	/**
	 * This method is called every tick.
	 */
	public void update() {
		if (thisGame.getGamestate().isBoss() && thisGame.enemies() < 1 
				&& System.currentTimeMillis() - startRest > REST) {
			if (Math.random() < (telebossratio)) {
				spawnTeleBoss();
			} else {
				if (Math.random() < (telebossratio + doublebossratio)) {
					spawnDoubleBoss();
					spawnDoubleBoss();
				} else {
					spawnBoss();
				}
			}
		} else {
			updateSaucer();
			updatePowerup();
			if (thisGame.enemies() != 0) {
				startRest = System.currentTimeMillis();
			}
			updateWave();
		}
	}
	
	/**
	 * Checks if a saucer should be added and does so if needed.
	 */
	private void updateSaucer() {
		if (System.currentTimeMillis() - startSaucerTime > SAUCER_TIME) {
			spawnSaucer();
			Logger.getInstance().log("Saucer was spawned");
			startSaucerTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * Checks if a powerup should be added and does so if needed.
	 */
	private void updatePowerup() {
		if (System.currentTimeMillis() - startPowerupTime > POWERUP_TIME) {
			spawnPowerup();
			Logger.getInstance().log("Powerup was spawned");
			startPowerupTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * Checks if the wave should be updated and does so if needed.
	 */
	private void updateWave() {
		if ((startRest == 0 || System.currentTimeMillis() - startRest > REST) 
				&& wave != 0 && wave % WAVES_BETWEEN_BOSSES == 0) {
			if (Math.random() < (telebossratio)) {
				spawnTeleBoss();
			} else {
				if (Math.random() < (telebossratio + doublebossratio)) {
					spawnDoubleBoss();
					spawnDoubleBoss();
				} else {
					spawnBoss();
				}
			}
			startRest = System.currentTimeMillis();
			wave++;
		} else if (startRest == 0) {
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
		final Saucer newSaucer = new Saucer(random.nextInt(1)
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
						* (float) Math.random(), thisGame));
	}

	/**
	 * Calculates the ratio of small saucers.
	 *
	 * @return the ratio
	 */
	private double smallSaucerRatio() {
		if (thisGame.getScoreCounter().getScore() < DIFFICULTY_STEP) {
			return .5;
		} else if (thisGame.getScoreCounter().getScore() < MAX_DIFFICULTY_SCORE) {
			return .5 + ((thisGame.getScoreCounter().getScore() / (double) DIFFICULTY_STEP)
					* .5 / (double) (MAX_DIFFICULTY_SCORE / DIFFICULTY_STEP));
		} else {
			return 1;
		}
	}

	/**
	 * adds asteroids with random Y, side of screen and direction, but with
	 * radius 20.
	 *
	 * @param times the number of asteroids
	 */
	private void spawnAsteroid(final int times) {
		for (int i = 0; i < times; i++) {
			thisGame.create(new Asteroid(0, thisGame.getScreenY() * (float) Math.random(),
					(float) (Math.random() - .5) * ASTEROID_SPEED, (float) (Math.random() - .5) * ASTEROID_SPEED,
					thisGame));
		}
		Logger.getInstance().log(times + " asteroids were spawned.");
	}
	
	/**
	 * 
	 */
	private void spawnBoss() {
		final Boss boss =
				new Boss(random.nextInt(1)
				* 2 * thisGame.getScreenX(), (float) Math.random()
				* thisGame.getScreenY(), 0, 0, thisGame);
		thisGame.create(boss);
	}

	/**
	 * Spawn Teleporting Boss.
	 */
	private void spawnTeleBoss() {
		final TeleBoss telboss =
				new TeleBoss(random.nextInt(1)
				* 2 * thisGame.getScreenX(), (float) Math.random()
				* thisGame.getScreenY(), 0, 0, thisGame);
		thisGame.create(telboss);
	}
	
	/**
	 * Spawn one of a pair of bosses.
	 */
	private void spawnDoubleBoss() {
		final DoubleBoss doubboss =
				new DoubleBoss(random.nextInt(1)
				* 2 * thisGame.getScreenX(), (float) Math.random()
				* thisGame.getScreenY(), 0, 0, thisGame);
		thisGame.create(doubboss);
	}
	
	/**
	 * reset.
	 */
	public void reset() {
		wave = 0;
		startSaucerTime = System.currentTimeMillis();
		startPowerupTime = System.currentTimeMillis();
		startRest = 0;
	}

	/**
	 * Getter for difficultyStep.
	 *
	 * @return the difficultyStep
	 */
	public static long getDifficultyStep() {
		return DIFFICULTY_STEP;
	}

	/**
	 * getter for wave.
	 *
	 * @return the wave
	 */
	public int getWave() {
		return wave;
	}

	/**
	 * @param wave the wave to set
	 */
	public void setWave(final int wave) {
		this.wave = wave;
	}

	/**
	 * @param startSaucerTime the startSaucerTime to set
	 */
	public void setStartSaucerTime(final long startSaucerTime) {
		this.startSaucerTime = startSaucerTime;
	}

	/**
	 * @param startPowerupTime the startPowerupTime to set
	 */
	public void setStartPowerupTime(final long startPowerupTime) {
		this.startPowerupTime = startPowerupTime;
	}

	/**
	 * @param startRest the startRest to set
	 */
	public void setStartRest(final long startRest) {
		this.startRest = startRest;
	}
}
