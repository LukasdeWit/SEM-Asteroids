package game.highscore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import game.Logger;
import game.highscore.model.HighScore;

/**
 * Created by douwe on 20-9-16.
 */
public class HighscoreStore {

    private static final int MODES = 6;
	/**
     * the local list of known highscores.
     */
    private HighScore[] highScores;

    /**
     * basic constructor.
     */
    public HighscoreStore() {
    	highScores = initHighscores();
        final HighScore[] oldScores = readHighScores();
        if (oldScores.length == MODES) {
	        for (int i = 0; i < MODES; i++) {
				if (oldScores[i].getScore() > highScores[i].getScore()) {
					highScores[i] = oldScores[i];
				}
			}
        }
    }

    /**
     * initialize highscores in correct order.
     * @return the initialized list of highscores
     */
    private HighScore[] initHighscores() {
    	final HighScore[] array = new HighScore[MODES];
    	for (int i = 0; i < MODES; i++) {
			array[i] = new HighScore("", 0, i, i + 1);
		}
    	return array;
	}

	/**
     * read the highscores currently stored on disk. if an error occurs whilst
     * trying to read the file an empty list will be returned
     * @return the list of highscores
     */
    private HighScore[] readHighScores() {
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(HighscoreUtils.getHighScoreFile()),
                StandardCharsets.UTF_8)) {
            final Gson gson = new GsonBuilder().create();
            HighScore[] array = gson.fromJson(reader, HighScore[].class);

            reader.close();

            if (array == null) {
                array = new HighScore[0];
            }

            final List<HighScore> result = Arrays.asList(array);
            Collections.sort(result);

            return result.toArray(array);
        } catch (IOException e) {
            Logger.getInstance().log("was unable to read the highscores file", e);
            if (highScores == null) {
                highScores = initHighscores();
            }
            return initHighscores();
        }
    }

    /**
     * adds a <strong>nonnull</strong> highscore to the store.
     * @param name - the name
     * @param score - the score we want to add as a highscore.
     * @param modeInt - the current gamemode
     */
    public final void addHighScore(final String name, final long score, final int modeInt) {
    	highScores[modeInt - 1] = new HighScore(name, score, modeInt, modeInt);
    }

    /**
     * write the currently stored scores to file. this overrides everything
     * previously in the file
     */
    public final void writeScores() {
        try (JsonWriter writer = new JsonWriter(new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(
                        HighscoreUtils.getHighScoreFile()),
                        StandardCharsets.UTF_8)))) {
            new GsonBuilder().create().toJson(highScores, HighScore[].class,
                    writer);
        } catch (IOException e) {
            Logger.getInstance().log("an error occurred whilst trying to write "
                    + "the highscore to file", e);
        }
    }

    /**
     * clear all stored highscores and reset the file stored on disk.
     */
    public final void clear() {
        highScores = initHighscores();
        this.writeScores();
    }

    /**
     * get an Immutable list of all the highscores in the store.
     * @return an immutable list of highscores
     */
    public final List<HighScore> getHighScores() {
        return ImmutableList.copyOf(highScores);
    }

    /**
     * get the current #1 highscore.
     * @param mode - the mode
     * @return the highest score
     */
    public final long getHighestScore(final int mode) {
    	if (mode > 0) {
    		return highScores[mode - 1].getScore();
    	}
    	return 0;
    }   
}
