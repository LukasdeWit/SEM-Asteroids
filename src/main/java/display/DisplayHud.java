package display;

import entity.Powerup;
import game.Game;
import game.Launcher;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
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
    private static final double[] LIVES_XY = new double[]{
            0, 6,
            2, 0,
            4, 6,
            3, 5,
            1, 5
    };

    /**
     * private constructor since this class only contains static methods.
     */
    private DisplayHud() {
        //no-op
    }

    /**
     * draw the lives of a player.
     *
     * @param lives the number of lives this player has
     * @param isPlayerTwo whether this player is player two or not
     */
    public static void lives(final int lives, final boolean isPlayerTwo) {
        final Rectangle r = new Rectangle(POWERUP_SLOT_SIZE, POWERUP_SLOT_SIZE, Color.TRANSPARENT);
        r.setStroke(Color.WHITE);
        if (isPlayerTwo) {
            r.setTranslateX(POWERUP_SLOT_TWO_X);
        } else {
            r.setTranslateX(POWERUP_SLOT_ONE_X);
        }
        r.setTranslateY(POWERUP_SLOT_Y);
        Launcher.getRoot().getChildren().add(r);

        if (lives <= 0) {
            return;
        }

        if (isPlayerTwo) {
            drawLives(LIVES_TWO_X, LIVES_Y, lives);
        } else {
            drawLives(LIVES_X, LIVES_Y, lives);
        }
    }

    /**
     * draw powerup in box.
     * @param p - the powerup
     */
    public static void powerup(final Powerup p) {
        final Circle c = new Circle(POWERUP_SLOT_SIZE / 2, POWERUP_SLOT_SIZE / 2, POWERUP_SIZE / 2);
        c.setFill(Color.WHITE);
        if (p.getPlayer() == null) {
            return;
        }
        if (p.getPlayer().isPlayerTwo()) {
            c.setTranslateX(POWERUP_SLOT_TWO_X);
        } else {
            c.setTranslateX(POWERUP_SLOT_ONE_X);
        }
        c.setTranslateY(POWERUP_SLOT_Y);
        Launcher.getRoot().getChildren().add(c);
    }

    /** 
     * draw the lives of a player.
     * @param x horizontal position
     * @param y vertical position
     * @param lives the number of lives to draw
     */
    private static void drawLives(final float x, final float y, final int lives) {
        final int width = 6;

        for (int i = 0; i < lives; i++) {
            final int index = i;
            final double[] points = DisplayUtils.translate(t -> t * LIVES_SIZE + x + index * width * LIVES_SIZE,
                    t -> t * LIVES_SIZE + y, LIVES_XY);
            final Polygon shape = new Polygon(points);

            shape.setFill(Color.WHITE);
            shape.setStrokeWidth(1);
            Launcher.getRoot().getChildren().add(shape);
        }
    }
}
