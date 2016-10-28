package entity.keyhandler;

import entity.Player;

/**
 * Command for if they player goes right.
 * @author Esmee
 *
 */
public class RightCommand extends AbstractCommand {
	/**
	 * Constructor for rightcommand.
	 * @param p player this belongs to
	 */
	public RightCommand(final Player p) {
		super(p);
	}

	@Override
	public final void execute() {
		getPlayer().turnRight();
	}

}
