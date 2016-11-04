package game.highscore.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by douwe on 4-11-16.
 */
@RunWith(Parameterized.class)
public class HighScoreTest {

    private HighScore test1;
    private HighScore test2;

    public HighScoreTest(HighScore test1, HighScore test2) {
        this.test1 = test1;
        this.test2 = test2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new HighScore[][]{
                {new HighScore("foobar", 123, 0, 1), new HighScore("barfoo", 321, 1, 0)},
                {new HighScore("foobar", 2, 0, 1), new HighScore("", 1, 2, 3)}
        });
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(test1.getUserName(), test1.getUserName());
        assertNotEquals(test1.getUserName(), test2.getUserName());
    }

    @Test
    public void getScore() throws Exception {
        assertEquals(test1.getScore(), test1.getScore());
        assertNotEquals(test1.getScore(), test2.getScore());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(test1.getId(), test1.getId());
        assertNotEquals(test1.getId(), test2.getId());
    }

    @Test
    public void getGamemode() throws Exception {
        assertEquals(test1.getGamemode(), test1.getGamemode());
        assertNotEquals(test1.getGamemode(), test2.getGamemode());
    }

    @Test
    public void equals() throws Exception {
       assertTrue(test1.equals(test1));
       assertFalse(test1.equals(test2));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("HighScore("
                + "userName=" + test1.getUserName()
                + ", score=" + test1.getScore()
                + ", id=" + test1.getId()
                + ", gamemode=" + test1.getGamemode()
                + ')', test1.toString());
    }

    @Test
    public void compareTo() throws Exception {
        assertEquals(Long.compare(test1.getScore(), test2.getScore()), test1.compareTo(test2));
    }
}