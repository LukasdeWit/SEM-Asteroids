package game;

import display.DisplayText;
import game.highscore.HighscoreStore;
import game.highscore.model.HighScore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class that maintains the score.
 * @author Esmee
 *
 */
public class ScoreCounter {
	private static final int LIFE_SCORE = 10000;

	private static final int THREE = 3;

	@Setter
	private long score;
	private final HighscoreStore highscoreStore;
	@Getter
	private final Game thisGame;
	
	/**
	 * Constructor for score counter.
	 * @param game this scorecounter belongs to
	 * @param highscoreStore the HighscoreStore used for managing the highscores
	 */
	public ScoreCounter(final Game game, final HighscoreStore highscoreStore) {
		this.thisGame = game;
		this.highscoreStore = highscoreStore;
	}

	/**
	 * Set score to 0 at start of game.
	 * Write existing score as highscore if larger than current highscore.
	 * @param name - the name
	 */
	public final void startGame(final String name) {
		if (this.score > getHighscore()) {
			highscoreStore.addHighScore(name, score, thisGame.getGamestate().getMode().toInt());
			highscoreStore.writeScores();
		}		
		score = 0;
	}
	
	/**
	 * Display score on screen.
	 */
	public final void displayScore() {
		DisplayText.score(score);
		DisplayText.highscore(getHighscore());
	}

	/**
	 * Convert highscores into readable strings for display.
	 * @return the highscore strings.
	 */
	public final String[][] highScoresToStrings() {
		final List<HighScore> highscores = highscoreStore.getHighScores();
		final String[][] out = new String[THREE][highscores.size()];
		for (int i = 0; i < highscores.size(); i++) {
			out[0][i] = getThisGame().getGamestate().intToString(i + 1);
			out[1][i] = Long.toString(highscores.get(i).getScore());
			out[2][i] = highscores.get(i).getUserName();
		}
		return out;
	}
	
	/**
	 * Helps calculate difficulty for small saucer.
	 * @return difficulty for small saucer.
	 */
	public final long smallSaucerDifficulty() {
		return score / Spawner.getDifficultyStep();
	}
	
	/**
	 * @return the high score
	 */
	public final long getHighscore() {
		return highscoreStore.getHighestScore(thisGame.getGamestate().getMode().toInt());
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
		return score > highscoreStore.getHighestScore(thisGame.getGamestate().getMode().toInt());
	}
	
	/**
	 * @return true if the score is not better than the high score
	 */
	public final boolean isNotHighscore() {
		return !isHighscore();
	}
	
	/**
	 * Update the highscore.
	 * @param name - the name
	 */
	protected final void updateHighscore(final String name) {
		if (isHighscore()) {
			highscoreStore.addHighScore(name, score, thisGame.getGamestate().getMode().toInt());
			highscoreStore.writeScores();
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
	 * @param name - the name
	 * @param highscore the highscore to set
	 */
	public final void setHighscore(final String name, final long highscore) {
		highscoreStore.addHighScore(name, highscore, thisGame.getGamestate().getMode().toInt());
	}

	/**
	 * clear all saved highscores, and reset the stored file.
	 */
	public final void clearHighscores() {
		highscoreStore.clear();
	}
}
