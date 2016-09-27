package abstractpowerup;
/**
 * This class represents a powerup as held by a player.
 * @author Dario
 *
 */
public class AbstractPowerup {
	/**
	 * The type of the powerup. Can be 0 (additional life), 1 (shield), 2 (bullet size increase), 3 (triple-shot), 4 (piercing bullets) or 5 (minigun).
	 */
	int powerupType;
	/**
	 * The remaining duration of the powerup.
	 */
	final int powerupDuration = 5000;
	/**
	 * When the powerup started.
	 */
	long startTime;
	
	public AbstractPowerup(int type){
		powerupType = type;
		startTime = System.currentTimeMillis();
	}
	
	public boolean powerupOver(){
		return(powerupDuration<(System.currentTimeMillis()-startTime));
	}

}