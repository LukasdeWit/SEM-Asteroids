package entity.keyhandler;

import entity.Player;

/**
 * Command for if the player goes left.
 * @author Esmee
 *
 */
public class LeftCommand extends AbstractCommand {
	/**
	 * Constructor for leftcommand.
	 * @param p player this belongs to
	 */
	public LeftCommand(final Player p) {
		super(p);
	}

	@Override
	public final void execute() {
		getPlayer().turnLeft();
	}

}
