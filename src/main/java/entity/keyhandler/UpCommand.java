package entity.keyhandler;

import entity.Player;

/**
 * Command for if the player goes forward.
 * @author Esmee
 *
 */
public class UpCommand extends AbstractCommand {
	/**
	 * Constructor for upcommand.
	 * @param p player this belongs to
	 */
	public UpCommand(final Player p) {
		super(p);
	}

	@Override
	public final void execute() {
		getPlayer().accelerate();		
	}

}
