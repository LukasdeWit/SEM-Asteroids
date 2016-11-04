package game.highscore;

import game.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by douwe on 19-9-16.
 */
public final class HighscoreUtils {
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
        return getAppDirectoryForOs(System.getProperty("os.name"));
    }

    /**
     * get app directory of given operating system.
     * @param osName name of operating system
     * @return the path to the app directory
     */
    public static String getAppDirectoryForOs(final String osName) {
        final String os = osName.toUpperCase(Locale.ENGLISH);
        if (os.contains("WIN")) {
            return System.getenv("APPDATA") + "/" + APP_NAME + '/';
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
                Logger.getInstance().log("created app config directory over at "
                        + highscore.getParentFile().getAbsolutePath());
            }
            if (highscore.createNewFile()) {
                Logger.getInstance().log("created file for storing "
                        + "highscores over at"
                        + highscore.getAbsolutePath());
            }
        } catch (IOException e) {
            Logger.getInstance().log("an I/O error occurred whilst trying to check the highscore file", e);
        }

        return highscore;
    }
}
