package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 * This class will log every action.
 * @author Kibo
 *
 */
public class Logger {
	/**
	 * The log file.
	 */
	private final File file;
	/**
	 * The file output stream.
	 */
	private FileOutputStream fos;
	/**
	 * The Date format.
	 */
	private final SimpleDateFormat sdf;
	/**
	 * This Game.
	 */
	private final Game game;
	
	/**
	 * Constructor of Logger.
	 *@param game - this game
	 */
	public Logger(final Game game) {
		file = new File("src/main/resources/log.txt");
		sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss.SSS");
		try {
			fos = new FileOutputStream(file.getAbsoluteFile());
		} catch (FileNotFoundException e) {
			fos = null;
			game.getLog().log(Level.ALL, "unable to write log to file", e
					);
		}
		this.game = game;
	}
	
	/**
	 * This method logs a message with the current time to a file.
	 * @param message - the message
	 */
	public final void log(final String message) {		
		String string = sdf.format(new Date(System.currentTimeMillis())) 
				+ " | " + message + "\n";
		try {
			fos.write(string.getBytes(StandardCharsets.UTF_8));
			fos.flush();
		} catch (IOException e) {
			game.getLog().log(Level.ALL, "unable to write log to file", e
					);
		}
	}
}
