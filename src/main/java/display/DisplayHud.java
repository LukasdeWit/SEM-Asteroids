package display;

import entity.Powerup;
import game.Game;
import game.Launcher;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by douwe on 11-10-16.
 */
public final class DisplayHud {
    private static final float LIVES_X = 10;
    private static final float LIVES_TWO_X = 300;
    private static final float LIVES_Y = 40;
    private static final float LIVES_SIZE = 2;
    private static final double POWERUP_SIZE = 20;
    private static final double POWERUP_SLOT_SIZE = POWERUP_SIZE + 5;
    private static final double POWERUP_SLOT_ONE_X = 10;
    private static final double POWERUP_SLOT_Y = 60;
    private static final double POWERUP_SLOT_TWO_X = Game.getCanvasSize() - 10 - POWERUP_SLOT_SIZE;

    /**
     * private constructor since this class only contains static methods.
     */
    private DisplayHud() {
        //no-op
    }

    /**
     * draw the lives of a player.
     *
     * @param lives     the number of lives the player has
     * @param playerTwo if this is player two
     */
    public static void lives(final int lives, final boolean playerTwo) {
        final Rectangle r = new Rectangle(POWERUP_SLOT_SIZE, POWERUP_SLOT_SIZE, Color.TRANSPARENT);
        r.setStroke(Color.WHITE);
        if (playerTwo) {
            r.setTranslateX(POWERUP_SLOT_TWO_X);
        } else {
            r.setTranslateX(POWERUP_SLOT_ONE_X);
        }
        r.setTranslateY(POWERUP_SLOT_Y);
        Launcher.getRoot().getChildren().add(r);

        if (lives <= 0) {
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder(lives);
        for (int i = 0; i < lives; i++) {
            stringBuilder.append('*');
        }
        final String livesString = stringBuilder.toString();
        if (playerTwo) {
            DisplayText.draw(LIVES_TWO_X, LIVES_Y, LIVES_SIZE, livesString);
        } else {
            DisplayText.draw(LIVES_X, LIVES_Y, LIVES_SIZE, livesString);
        }
    }

    /**
     * draw powerup in box.
     * @param p - the powerup
     */
    public static void powerup(final Powerup p) {
        final Circle c = new Circle(POWERUP_SLOT_SIZE / 2, POWERUP_SLOT_SIZE / 2, POWERUP_SIZE / 2);
        c.setFill(Color.WHITE);
        if (p.getPlayer().isPlayerTwo()) {
            c.setTranslateX(POWERUP_SLOT_TWO_X);
        } else {
            c.setTranslateX(POWERUP_SLOT_ONE_X);
        }
        c.setTranslateY(POWERUP_SLOT_Y);
        Launcher.getRoot().getChildren().add(c);
    }
}
