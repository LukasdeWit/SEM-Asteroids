package game.highscore;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by douwe on 4-11-16.
 */
public class HighscoreUtilsTest {

    @Test
    public void getAppDirectoryForOs() throws Exception {
        assertEquals("incorrect Windows app folder" , System.getenv("APPDATA") + "/" + HighscoreUtils.APP_NAME + '/',
                HighscoreUtils.getAppDirectoryForOs("WIN"));

        assertEquals("incorrect Mac app folder", System.getProperty("user.home") + "/Library/Application " + "Support"
                + '/' + HighscoreUtils.APP_NAME + '/', HighscoreUtils.getAppDirectoryForOs("MAC"));

        assertEquals("incorrect Linux app folder", System.getProperty("user.home") + "/."
                        + HighscoreUtils.APP_NAME + '/', HighscoreUtils.getAppDirectoryForOs("NUX"));

        assertEquals("incorrect user directory folder", System.getProperty("user.dir") + "/"
                        + HighscoreUtils.APP_NAME + '/', HighscoreUtils.getAppDirectoryForOs(""));


    }

    @Test
    public void getHighScoreFile() throws Exception {
        final File highscore = new File(HighscoreUtils.getAppDirectory() + "highscore.json");
        highscore.delete();
        highscore.getParentFile().delete();

        assertEquals("found incorrect highscore file", HighscoreUtils.getAppDirectory() + "highscore.json",
                HighscoreUtils.getHighScoreFile().getAbsolutePath());
    }
}