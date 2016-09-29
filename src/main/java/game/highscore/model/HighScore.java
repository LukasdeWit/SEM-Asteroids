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
    public HighScore(final String userName, final long score, final int id, int gamemode) {
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
    public int getGamemode() {
        return gamemode;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HighScore highScore = (HighScore) o;

        if (getScore() != highScore.getScore()) {
            return false;
        }
        if (getId() != highScore.getId()) {
            return false;
        }
        if (getUserName() != null) {
            return getUserName().equals(highScore.getUserName());
        } else {
            return highScore.getUserName() == null;
        }

    }

    @Override
    public final int hashCode() {
        int result;
        if (getUserName() != null) {
            result = getUserName().hashCode();
        } else {
            result = 0;
        }
        final int prime = 31;
        final int i = 32;
        result = prime * result + (int) (getScore() ^ (getScore() >>> i));
        result = prime * result + getId();
        return result;
    }

    @Override
    public final String toString() {
        return "HighScore{"
                + "userName='" + userName + '\''
                + ", score=" + score
                + ", id=" + id
                + '}';
    }

    @Override
    public final int compareTo(final HighScore o) {
        return Long.compare(o.getScore(), getScore());
    }
}
