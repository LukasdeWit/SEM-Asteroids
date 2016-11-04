package game.highscore.model;

import lombok.Data;

/**
 * Created by douwe on 19-9-16.
 */
@Data
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
     * {@inheritDoc}
     */
    @Override
    public final int compareTo(final HighScore o) {
        return Integer.compare(getGamemode(), o.getGamemode());
    }
}
