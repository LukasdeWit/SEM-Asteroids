package game;
/**
 * This class takes care of spawning in new Asteroids and Saucer's.
 * @author Kibo
 *
 */
public class Spawner {
	/**
	 * The Game this spawner belongs to.
	 */
	private Game thisGame;
	/**
	 * Start time of saucer timer in ms.
	 */
	private long startSaucerTime;
	/**
	 * start time of rest in ms.
	 */
	private long startRest;
	/**
	 * The spawn level of the game.
	 */
	private int level;
	/**
	 * Time between saucers in ms.
	 */
	private static final long SAUCER_TIME = 20000;
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
	 * Constructor of Spawner.
	 * @param game - the game this spawner belongs to
	 */
	public Spawner(final Game game) {
		thisGame = game;
		startSaucerTime = System.currentTimeMillis();
		startRest = 0;
		level = 1;
	}

	/**
	 * This method is called every tick.
	 */
	public final void update() {
		if (System.currentTimeMillis() - startSaucerTime > SAUCER_TIME) {
			thisGame.addRandomSaucer();
			startSaucerTime = System.currentTimeMillis();
		}
		if (thisGame.enemies() != 0) {
			startRest = System.currentTimeMillis();
		}
		if (startRest == 0) {
			thisGame.addRandomAsteroid(STARTING_ASTEROIDS);
			startRest = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - startRest > REST) {
			int extra = level * 2;
			if (extra > MAX_EXTRA) {
				extra = MAX_EXTRA;
			}
			thisGame.addRandomAsteroid(STARTING_ASTEROIDS + extra);
			level++;
			startRest = System.currentTimeMillis();
		}
	}
	
	/**
	 * reset.
	 */
	public final void reset() {
		level = 1;
		startSaucerTime = System.currentTimeMillis();
		startRest = 0;
	}
	
}
