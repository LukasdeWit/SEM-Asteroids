package abstractpowerup;

/**
 * This class represents a powerup as held by a player.
 * @author Dario
 *
 */
public class AbstractPowerup {
	private final long startTime;
	
	private double fireRateBoost;
	private int pierceRateBoost;
	private int sizeBoost;
	private int numbBoost;

	private static final int POWERUP_DURATION = 5000;
	private static final double FIRE_RATE_BOOST_MINIGUN = 0.3;
	private static final int PIERCE_RATE_BOOST_PIERCING = 3;
	private static final int SIZE_BOOST_BIG = 5;
	private static final int NUMB_BOOST_MINIGUN = 3;
	
	private static final int SIZE_BOOST = 2;
	private static final int PIERCE_BOOST = 3;
	private static final int FIRE_RATE_BOOST = 4;
	
	/**
	 * Constructor for the AbstractPowerup class.
	 * @param type the type of powerup it is.
	 */
	public AbstractPowerup(final int type) {
		startTime = System.currentTimeMillis();
		fireRateBoost = 1;
		pierceRateBoost = 0;
		sizeBoost = 1;
		numbBoost = 1;
		switch(type) {
			case SIZE_BOOST:
				sizeBoost = SIZE_BOOST_BIG;
				break;
			case PIERCE_BOOST:
				pierceRateBoost = PIERCE_RATE_BOOST_PIERCING;
				break;
			case FIRE_RATE_BOOST:
				fireRateBoost = FIRE_RATE_BOOST_MINIGUN;
				numbBoost = NUMB_BOOST_MINIGUN;
				break;
			default:
				break;
		}
	}
	
	/**
	 * Checks whether the powerup has expired or not.
	 * @return true if expired
	 */
	public final boolean powerupOver() {
		return POWERUP_DURATION < (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * Getter for the fire rate boost of the powerup.
	 * @return the fire rate boost
	 */
	public final double getRateMult() {
		return fireRateBoost;
	}
	
	/**
	 * Getter for the number of bullets boost of the powerup.
	 * @return the numb boost
	 */
	public final int getNumbMult() {
		return numbBoost;
	}
	
	/**
	 * Getter for the pierce boost of the powerup.
	 * @return the pierce rate boost
	 */
	public final int getPierceRateBoost() {
		return pierceRateBoost;
	}

	/**
	 * Getter for the size boost of the powerup.
	 * @return the size boost
	 */
	public final int getSizeBoost() {
		return sizeBoost;
	}

}