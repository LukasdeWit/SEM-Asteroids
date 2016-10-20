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
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import game.Logger;
import game.highscore.model.HighScore;

/**
 * Created by douwe on 20-9-16.
 */
public class HighscoreStore {

    /**
     * the local list of known highscores.
     */
    private List<HighScore> highScores;

    /**
     * basic constructor.
     */
    public HighscoreStore() {
        // initialises the highScore list
        highScores = readHighScores();
    }

    /**
     * read the highscores currently stored on disk. if an error occurs whilst
     * trying to read the file an empty list will be returned
     * @return the list of highscores
     */
    private List<HighScore> readHighScores() {
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

            return Lists.newArrayList(result);
        } catch (IOException e) {
            Logger.getInstance().log("was unable to read the highscores file", e);
            if (highScores == null) {
                highScores = Lists.newArrayList();
            }
            return Lists.newArrayList();
        }
    }

    /**
     * join the local highscores and those stored on the disk. the result will
     * become the new list of highscores, both locally and on the disk
     *//*
    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private void join() {
        highScores = Stream.concat(highScores.stream(), readHighScores()
                .stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        writeScores();
    }*/

    /**
     * adds a <strong>nonnull</strong> highscore to the store.
     * @param score the score we want to add as a highscore.
     * @param gamemode the current gamemode
     */
    public final void addHighScore(final long score, final int gamemode) {
        highScores.add(new HighScore(System.getProperty("user.name"),
                score, nextId(), gamemode));

        Collections.sort(highScores);
    }

    /**
     * the next available ID, used to add new highscores.
     * @return the id
     */
    private int nextId() {
        final Optional<Integer> result = highScores
                .stream()
                .map(HighScore::getId)
                // reverse sort to get the biggest id at the start
                .sorted((i1, i2) -> Integer.compare(i2, i1))
                .findFirst();
        if (result.isPresent()) {
            return result.get() + 1;
        } else {
            return 0;
        }
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
            Collections.sort(highScores);

            new GsonBuilder().create().toJson(highScores.toArray(
                    new HighScore[highScores.size()]), HighScore[].class,
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
        this.highScores.clear();
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
     * @return the highest score
     */
    public final long getHighestScore() {
        if (highScores.isEmpty()) {
            return 0;
        }

        return highScores.get(0).getScore();
    }
}
