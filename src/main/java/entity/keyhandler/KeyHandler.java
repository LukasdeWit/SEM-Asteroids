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
	//private Map<String, AbstractCommand> map;
	private Map<String, AbstractCommand> player1map;
	private Map<String, AbstractCommand> player2map;

	//private static final String[] P1COMMANDS = {"W", "A", "S", "D", "SPACE"};
	//private static final String[] P2COMMANDS = {"LEFT", "RIGHT", "UP", "DOWN", "ENTER"};
	
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
		//map = new HashMap<String, AbstractCommand>();
		
		player1map = new HashMap<String, AbstractCommand>();
		player2map = new HashMap<String, AbstractCommand>();

		player1map.put("SPACE", sc);
		player2map.put("ENTER", sc);
		player1map.put("LEFT", lc);
		player2map.put("A", lc);
		player1map.put("RIGHT", rc);
		player2map.put("D", rc);
		player1map.put("DOWN", dc);
		player2map.put("S", dc);
		player1map.put("UP", uc);
		player2map.put("W", uc);
		}
	
	/**
	 * Convert a list of inputs to a set of commands.
	 * @param input the list that contains keyboard input
	 * @return a set of abstractcommands.
	 */
	private Set<AbstractCommand> convert(final List<String> input) {
		Set<AbstractCommand> commands = new HashSet<AbstractCommand>();
		if (p.getThisGame().getGamestate().isCoop()) {
			for (String s : input) {
				AbstractCommand c = null;
				if (p.isPlayerTwo()) {
					c = player2map.get(s);
				} else {
					c = player1map.get(s);
				}
				if (c != null) {
					commands.add(c);
				}
			}
		} else {
			for (String s : input) {
				AbstractCommand c = player1map.get(s);
				AbstractCommand d = player2map.get(s);

				if (c != null) {
					commands.add(c);
				}
				if (d != null) {
					commands.add(d);
				}
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
