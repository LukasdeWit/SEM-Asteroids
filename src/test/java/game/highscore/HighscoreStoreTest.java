package game.highscore;

import game.highscore.model.HighScore;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by douwe on 4-11-16.
 */
public class HighscoreStoreTest {

    private HighscoreStore store;

    @Before
    public void setup() {
        store = new HighscoreStore();
        store.clear();
    }

    @Test
    public void addHighScore() throws Exception {
        final HighScore expected = new HighScore("foobar", 123, 1, 1);

        store.addHighScore(expected.getUserName(), expected.getScore(), expected.getGamemode());

        final HighScore actual = store.getHighScores().get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void clear() throws Exception {
        final HighScore testHighScore = new HighScore("foobar", 123, 1, 1);

        store.addHighScore(testHighScore.getUserName(), testHighScore.getScore(),
                testHighScore.getGamemode());
        assertEquals("did not properly add one highscore", testHighScore,
                store.getHighScore(testHighScore.getGamemode()));
        store.clear();
        assertEquals("store was not cleared properly", new HighScore("", 0, 0, 1), store.getHighScore(1));
    }

    @Test
    public void getHighScores() throws Exception {
        HighScore testHighScore = new HighScore("foobar", 123, 1, 1);
        final HighScore[] expected = new HighScore[]{
                testHighScore,
                new HighScore("", 0, 1, 2),
                new HighScore("", 0, 2, 3),
                new HighScore("", 0, 3, 4),
                new HighScore("", 0, 4, 5),
                new HighScore("", 0, 5, 6)
        };

        store.addHighScore(testHighScore.getUserName(),
                testHighScore.getScore(),
                testHighScore.getGamemode());

        final HighScore[] actual = store.getHighScores().toArray(new HighScore[1]);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getHighScore() throws Exception {
        assertEquals("incorrectly initialised highscores", new HighScore("", 0, 0, 1), store.getHighScore(1));

        final HighScore expected = new HighScore("foobar", 123, 1, 1);
        store.addHighScore(expected.getUserName(), expected.getScore(), expected.getGamemode());

        assertEquals(expected, store.getHighScores().get(0));
    }
}