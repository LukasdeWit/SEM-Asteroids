package entity.keyhandler;

import entity.Player;

/**
 * Abstract class for handling commands.
 * @author Esmee
 *
 */
public abstract class AbstractCommand {
	private Player player;
	
	/**
	 * Constructor.
	 * @param p player this belongs to
	 */
	public AbstractCommand(final Player p) {
		setPlayer(p);
	}
	
	/**
	 * @return player this belongs to
	 */
	public final Player getPlayer() {
		return player;
	}
	
	/**
	 * Setter for player.
	 * @param p player to set to
	 */
	public final void setPlayer(final Player p) {
		player = p;
	}
	
	/**
	 * Method that explains what a command should do when executed.
	 */
	public abstract void execute();
}
