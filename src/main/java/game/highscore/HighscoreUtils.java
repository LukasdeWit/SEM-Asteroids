package game.highscore;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by douwe on 19-9-16.
 */
public final class HighscoreUtils {
    /**
     * class logger.
     */
    private static final Logger LOG =
            Logger.getLogger(HighscoreUtils.class.getName());

    /**
     * the app name to use for creating our config folder.
     */
    public static final String APP_NAME = "SEM-Asteroids";

    /**
     * this is a utility class so it can't be instantiated.
     */
    private HighscoreUtils() { }

    /**
     * get the directory to store our configurations.
     * @return the directory path.
     */
    public static String getAppDirectory() {
        String os = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
        if (os.contains("WIN")) {
            return System.getenv("APPDATA") + "/local/" + APP_NAME + '/';
        } else if (os.contains("MAC")) {
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support" + '/' + APP_NAME + '/';
        } else if (os.contains("NUX")) {
            return System.getProperty("user.home") + "/." + APP_NAME + '/';
        }
        return System.getProperty("user.dir") + '/' + APP_NAME + '/';
    }

    /**
     * get the file the highscores are stored in.
     * @return highscores store
     */
    public static File getHighScoreFile() {
        final File highscore = new File(getAppDirectory() + "highscore.json");
        try {
            if (highscore.getParentFile().mkdirs()) {
                LOG.log(Level.INFO, "created app config directory over at "
                        + highscore.getParentFile().getAbsolutePath());
            }
            if (highscore.createNewFile()) {
                LOG.log(Level.INFO, "created file for storing "
                        + "highscores over at"
                + highscore.getAbsolutePath());
            }
        } catch (IOException e) {
            LOG.log(Level.ALL, "an I/O error occurred whilst trying to check"
                    + " the highscore file", e);
        }

        return highscore;
    }
}
