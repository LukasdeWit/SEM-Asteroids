package entity.keyhandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Player;

/**
 * Handles key presses for player class.
 * @author Esmee
 *
 */
public class KeyHandler {
	private Player p;
	private Map<String, AbstractCommand> map;
	private static final String[] P1COMMANDS = {"W", "A", "S", "D", "SPACE"};
	private static final String[] P2COMMANDS = {"LEFT", "RIGHT", "UP", "DOWN", "ENTER"};
	
	/**
	 * Constructor for keyhandler.
	 * @param p player this belongs to
	 */
	public KeyHandler(final Player p) {
		this.p = p;
		ShootCommand sc = new ShootCommand(p);
		UpCommand uc = new UpCommand(p);
		DownCommand dc = new DownCommand(p);
		LeftCommand lc = new LeftCommand(p);
		RightCommand rc = new RightCommand(p);
		map = new HashMap<String, AbstractCommand>();
		map.put("SPACE", sc);
		map.put("ENTER", sc);
		map.put("LEFT", lc);
		map.put("A", lc);
		map.put("RIGHT", rc);
		map.put("D", rc);
		map.put("DOWN", dc);
		map.put("S", dc);
		map.put("UP", uc);
		map.put("W", uc);
	}
	
	/**
	 * Convert a list of inputs to a set of commands.
	 * @param input the list that contains keyboard input
	 * @return a set of abstractcommands.
	 */
	private Set<AbstractCommand> convert(final List<String> input) {
		Set<AbstractCommand> commands = new HashSet<AbstractCommand>();
		List<String> actualinput = input;
		if (p.isPlayerTwo()) {
			actualinput.removeAll(Arrays.asList(P1COMMANDS));
		} else {
			actualinput.removeAll(Arrays.asList(P2COMMANDS));
		}
		for (String s : input) {
			AbstractCommand c = map.get(s);
			if (c != null) {
				commands.add(c);
			}
		}
		return commands;
	}
	
	/**
	 * Update game with string input.
	 * @param input list containing keyboard input
	 */
	public final void update(final List<String> input) {
		Set<AbstractCommand> commands = convert(input);
		
		for (AbstractCommand c : commands) {
			c.execute();
		}
	}
}
