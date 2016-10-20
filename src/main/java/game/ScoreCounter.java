package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import display.DisplayText;

/**
 * Class that maintains the score.
 * @author Esmee
 *
 */
public class ScoreCounter {
	private static final int LIFE_SCORE = 10000;

	private long score;
	private long highscore;
	// not used  right now, but useful when we want to separate
	// highscores for each game mode
	private final Game thisGame;
	
	/**
	 * Constructor for score counter.
	 * @param game this scorecounter belongs to
	 */
	public ScoreCounter(final Game game) {
		highscore = readHighscore();
		thisGame = game;
	}
	
	/**
	 * reads the highscore from file in resources folder.
	 *
	 * @return the highscore
	 */
	private long readHighscore() {
		long currentHighscore = 0;
		final String filePath = "src/main/resources/highscore.txt";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),
				StandardCharsets.UTF_8))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				currentHighscore = Long.parseLong(sCurrentLine);
			}
		} catch (IOException e) {
			Logger.getInstance().log("Creating highscore file");
		}
		return currentHighscore;
	}
	
	/**
	 * writes the highscore to file in resources folder.
	 */
	private void writeHighscore() {
		final String content = String.valueOf(highscore);
		final File file = new File("src/main/resources/highscore.txt");
		try (FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile())) {
			fos.write(content.getBytes(StandardCharsets.UTF_8));
			fos.flush();
			fos.close();
		} catch (IOException e) {
			Logger.getInstance().log("unable to write highscore to file", e);
		}
	}
	
	/**
	 * Set score to 0 at start of game.
	 * Write existing score as highscore if larger than current highscore.
	 */
	protected final void startGame() {
		if (this.score > highscore) {
			highscore = this.score;
			writeHighscore();
		}		
		score = 0;
	}
	
	/**
	 * Display score on screen.
	 */
	public final void displayScore() {
		DisplayText.score(score);
		DisplayText.highscore(highscore);
	}
	
	/**
	 * @return the high score
	 */
	public final long getHighscore() {
		return highscore;
	}
	
	/**
	 * Score getter.
	 *
	 * @return score
	 */
	public final long getScore() {
		return score;
	}
	
	/**
	 * @return true if the score is better than the highscore
	 */
	public final boolean isHighscore() {
		return score > highscore;
	}
	
	/**
	 * @return true if the score is not better than the high score
	 */
	public final boolean isNotHighscore() {
		return !isHighscore();
	}
	
	/**
	 * Update the highscore.
	 */
	protected final void updateHighscore() {
		if (isHighscore()) {
			highscore = score;
			writeHighscore();
		}
	}
	
	/**
	 * Add the amount of points to the current score.
	 * @param points amount of points to be added
	 */
	public final void addScore(final long points) {
		score += points;
	}
	
	/**
	 * Method to check if the player has enough points to gain a life.
	 * @param points amount of points that the player gains
	 * @return true when the player can gain a life
	 */
	public final boolean canGainLife(final int points) {
		return this.score % LIFE_SCORE + points >= LIFE_SCORE;
	}
	
	/**
	 * @param score the score to set
	 */
	public final void setScore(final long score) {
		this.score = score;
	}
	
	/**
	 * @param highscore the highscore to set
	 */
	public final void setHighscore(final long highscore) {
		this.highscore = highscore;
	}
	
	/**
	 * @return game this scorecounter belongs to
	 */
	public final Game getThisGame() {
		return thisGame;
	}
}
