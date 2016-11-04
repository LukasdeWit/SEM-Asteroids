package game.highscore.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class HighScoreTest {
	private HighScore score;
	
	@Before
	public void setup() {
		score = new HighScore("user", 100, 1, 1);
	}
	
	@Test
	public void testID() {
		assertEquals(1, score.getId());
	}
	
	@Test
	public void testEquals1() {
		HighScore score2 = new HighScore("user", 100, 2, 1);
		assertFalse(score.equals(score2));
	}
	
	@Test
	public void testEquals2() {
		HighScore score2 = new HighScore("user", 100, 1, 1);
		assertEquals(score, score2);
	}
	
	@Test
	public void testEquals3() {
		Object o2 = null;
		assertFalse(score.equals(o2));
	}
	
	@Test
	public void testEquals4() {
		assertEquals(score, score);
	}
	
	@Test
	public void testEquals5() {
		HighScore o2 = null;
		assertFalse(score.equals(o2));
	}
	
	@Test
	public void testEquals6() {
		HighScore score2 = new HighScore("user", 100, 1, 2);
		assertFalse(score.equals(score2));
	}
	
	@Test
	public void testEquals7() {
		HighScore score2 = new HighScore("user", 200, 1, 1);
		assertFalse(score.equals(score2));
	}
	
	@Test
	public void testEquals8() {
		HighScore score2 = new HighScore("pacman", 100, 1, 1);
		assertFalse(score.equals(score2));
	}
	
	@Test
	public void testEquals9() {
		HighScore score2 = new HighScore(null, 100, 1, 1);
		assertFalse(score.equals(score2));
	}
	
	@Test
	public void testToString() {
		String hstring = "HighScore{userName=\'user\', score=100, id=1}";
		assertEquals(score.toString(), hstring);
	}
}
