package game;

import display.DisplayText;
import game.highscore.HighscoreStore;

/**
 * Class that maintains the score.
 * @author Esmee
 *
 */
public class ScoreCounter {
	/**
	 * The amount of points needed to gain a life.
	 */
	private static final int LIFE_SCORE = 10000;

	private long score;
	private final HighscoreStore highscoreStore;
	// not used  right now, but useful when we want to separate
	// highscores for each game mode
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
	 */
	protected final void startGame() {
		if (this.score > highscoreStore.getHighestScore()) {
			highscoreStore.addHighScore(score, thisGame.getGamestate().getMode());
			highscoreStore.writeScores();
		}		
		score = 0;
	}
	
	/**
	 * Display score on screen.
	 */
	public final void displayScore() {
		DisplayText.score(score);
		DisplayText.highscore(highscoreStore.getHighestScore());
	}
	
	/**
	 * @return the high score
	 */
	public final long getHighscore() {
		return highscoreStore.getHighestScore();
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
		return score > highscoreStore.getHighestScore();
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
		highscoreStore.addHighScore(score, thisGame.getGamestate().getMode());
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
	 * @param score the score to set
	 */
	public final void setScore(final long score) {
		this.score = score;
	}
	
	/**
	 * @param highscore the highscore to set
	 */
	public final void setHighscore(final long highscore) {
		highscoreStore.addHighScore(highscore, thisGame.getGamestate().getMode());
	}
	
	/**
	 * @return game this scorecounter belongs to
	 */
	public final Game getThisGame() {
		return thisGame;
	}
}
