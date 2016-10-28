package entity.keyhandler;

import entity.Player;

/**
 * Command for if player presses down.
 * @author Esmee
 *
 */
public class DownCommand extends AbstractCommand {
	/**
	 * Constructor for downcommand.
	 * @param p player this belongs to
	 */
	public DownCommand(final Player p) {
		super(p);
	}

	@Override
	public final void execute() {
		getPlayer().goHyperspace();
	}

}
