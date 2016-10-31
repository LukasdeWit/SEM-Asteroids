package game;

import entity.Asteroid;
import entity.BasicBoss;
import entity.DoubleBoss;
import entity.Powerup;
import entity.Saucer;
import entity.TeleBoss;
import java.util.Random;

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
	private final Game thisGame;

	private static final long SAUCER_TIME = 20000;
	private static final double TELE_RATIO = 0.3;
	private static final double DOUBLE_RATIO = 0.2;
	private static final long POWERUP_TIME = 15000;
	private static final long REST = 4000;
	private static final int STARTING_ASTEROIDS = 4;
	private static final int MAX_EXTRA = 7;
	private static final long DIFFICULTY_STEP = 10000;
	private static final long MAX_DIFFICULTY_SCORE = 10 * DIFFICULTY_STEP;
	private static final float ASTEROID_SPEED = 1;
	private static final long SURVIVAL_POINTS_PER_ASTEROID = 10000;
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
	 * This method is called every tick of an arcade game.
	 */
	public void updateArcade() {
		updateSaucer();
		updatePowerup();
		if (thisGame.enemies() != 0) {
			startRest = System.currentTimeMillis();
		}
		updateWave();
	}

	/**
	 * This method is called every tick of a boss game.
	 */
	public void updateBoss() {
		if (thisGame.getGamestate().isBoss() && thisGame.enemies() < 1 
				&& System.currentTimeMillis() - startRest > REST) {
			if (Math.random() < (telebossratio)) {
				spawnTeleBoss();
			} else {
				if (Math.random() < (telebossratio + doublebossratio)) {
					spawnDoubleBoss();
					spawnDoubleBoss();
				} else {
					spawnBasicBoss();
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
	 * This method is called every tick of a survival game.
	 */
	public void updateSurvival() {
		updateSaucer();
		updatePowerup();
		final int extra = (int) (thisGame.getScorecounter().getScore() / SURVIVAL_POINTS_PER_ASTEROID);
		final int enemies = thisGame.convertedBigEnemies();
		if (STARTING_ASTEROIDS + extra - enemies > 0) {
			spawnAsteroid(STARTING_ASTEROIDS + extra - enemies);
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
			spawnBoss();
		} else if (startRest == 0) {
			Logger.getInstance().log("Wave: " + (wave + 1) + ".");
			spawnAsteroid(STARTING_ASTEROIDS);
			startRest = System.currentTimeMillis();
			wave++;
		} else if (System.currentTimeMillis() - startRest > REST) {
			nextWave();
		}
	}
	
	/**
	 * Checks if and which boss should be spawned.
	 */
	private void spawnBoss() {
		if (Math.random() < (telebossratio)) {
			spawnTeleBoss();
		} else {
			if (Math.random() < (telebossratio + doublebossratio)) {
				spawnDoubleBoss();
				spawnDoubleBoss();
			} else {
				spawnBasicBoss();
			}
		}
		startRest = System.currentTimeMillis();
		wave++;
	}

	/**
	 * Spawns the next wave of asteroids.
	 */
	private void nextWave() {
		int extra = wave * 2;
		if (extra > MAX_EXTRA) {
			extra = MAX_EXTRA;
		}
		Logger.getInstance().log("Wave: " + (wave + 1) + ".");
		spawnAsteroid(STARTING_ASTEROIDS + extra);
		wave++;
		startRest = System.currentTimeMillis();
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
		if (thisGame.getScorecounter().getScore() < DIFFICULTY_STEP) {
			return .5;
		} else if (thisGame.getScorecounter().getScore() < MAX_DIFFICULTY_SCORE) {
			return .5 + ((thisGame.getScorecounter().getScore() / (double) DIFFICULTY_STEP)
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
		if (times == 1) {
			Logger.getInstance().log("1 asteroid was spawned.");
		} else {
			Logger.getInstance().log(times + " asteroids were spawned.");
		}
	}
	
	/**
	 * Spawns a boss.
	 */
	private void spawnBasicBoss() {
		final BasicBoss boss =
				new BasicBoss(random.nextInt(1)
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
