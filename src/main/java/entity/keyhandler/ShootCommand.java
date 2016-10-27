package entity.keyhandler;

import entity.Player;

/**
 * Command for if the player shoots.
 * @author Esmee
 *
 */
public class ShootCommand extends AbstractCommand {
	/**
	 * Constructor for shootcommand.
	 * @param p player this belongs to
	 */
	public ShootCommand(final Player p) {
		super(p);
	}

	@Override
	public final void execute() {
		getPlayer().fire();
	}

}
