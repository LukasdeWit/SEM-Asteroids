package game.highscore.model;

/**
 * Created by douwe on 19-9-16.
 */
public class HighScore implements Comparable<HighScore> {
    /**
     * username of the person who set this highscore.
     */
    private final String userName;
    /**
     * the score of this highscore.
     */
    private final long score;
    /**
     * the id of the highscore entry.
     */
    private final int id;
    /**
     * the gametype this highscore was achieved on.
     */
    private final int gamemode;
    /**
     * basic constructor.
     * @param userName username of the person who set this highscore
     * @param score the score set in this highscore
     * @param id the id related to this highscore
     * @param gamemode the gamemode this score was achieved on
     */
    public HighScore(final String userName, final long score, final int id, final int gamemode) {
        this.userName = userName;
        this.score = score;
        this.id = id;
        this.gamemode = gamemode;
    }

    /**
     * get username of the person who set this highscore.
     * @return the username
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * get the score set in this highscore.
     * @return the score
     */
    public final long getScore() {
        return score;
    }

    /**
     * get the id of this highscore.
     * @return the id
     */
    public final int getId() {
        return id;
    }

    /**
     * get the gametype this highscore was achieved on.
     * @return the gametype
     */
    public final int getGamemode() {
        return gamemode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }        
        return checkHighscore(o);       
    }

    /**
     * Checks individual parts of highscore.
     * @param o - the object.
     * @return true if equal.
     */
    private boolean checkHighscore(final Object o) {
    	final HighScore highScore = (HighScore) o;
        if (getScore() != highScore.getScore()) {
            return false;
        }
        if (getId() != highScore.getId()) {
            return false;
        }
        if (getGamemode() != highScore.getGamemode()) {
            return false;
        }        
        if (getUserName() == null) {
            return highScore.getUserName() == null;
        } else {
            return getUserName().equals(highScore.getUserName());
        }
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        int result;
        if (getUserName() == null) {
            result = 0;
        } else {
            result = getUserName().hashCode();
        }

        final int prime = 31;
        result = prime * result + (int) (getScore() ^ (getScore() >>> prime + 1));
        result = prime * result + getId();
        result = prime * result + getGamemode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return "HighScore{"
                + "userName='" + userName + '\''
                + ", score=" + score
                + ", id=" + id
                + '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int compareTo(final HighScore o) {
        return Long.compare(getScore(), o.getScore());
    }
}
