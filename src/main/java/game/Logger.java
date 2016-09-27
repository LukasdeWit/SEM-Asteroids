package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class will log every action.
 *
 * @author Kibo
 */
@SuppressWarnings({"PMD.SystemPrintln", "PMD.AvoidPrintStackTrace"})
public final class Logger {
	/**
	 * The file output stream.
	 */
	private FileOutputStream fos;
	/**
	 * The Date format.
	 */
	private final SimpleDateFormat sdf;
	/**
	 * the singleton instance.
	 */
	private static Logger instance = null;

	/**
	 * Private constructor of Logger.
	 */
	private Logger() {
		final File file = new File("log.txt");
		sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss.SSS", Locale.ENGLISH);
		try {
			fos = new FileOutputStream(file.getAbsoluteFile());
		} catch (FileNotFoundException e) {
			fos = null;
			e.printStackTrace();
			System.out.println("unable to write log to file");
		}
	}

	/**
	 * getter for the instance.
	 *
	 * @return the logger.
	 */
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	/**
	 * This method logs a message with the current time to a file.
	 *
	 * @param message - the message
	 */
	public void log(final String message) {
		final String string = sdf.format(new Date(System.currentTimeMillis()))
				+ " | " + message + "\n";
		try {
			fos.write(string.getBytes(StandardCharsets.UTF_8));
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("unable to write log to file");
		}
	}
}
