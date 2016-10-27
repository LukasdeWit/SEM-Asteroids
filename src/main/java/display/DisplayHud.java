package display;

import entity.Powerup;
import game.Game;
import game.Launcher;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private static final double[] LIVES_LINES = new double[]{
            0, 6,
            2, 0,
            4, 6,
            3, 5,
            1, 5
    };
	private static final double POWERUP_SLOT_CENTER = 2.5f;
	private static final double POWERUP_STROKE_WIDTH = 4;
	private static final Double[] POWERUP_TRIANGLE = new Double[]{10.0, 0.0, 16.0, 6.0, 4.0, 6.0 };

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
        if (p.getPlayer() == null) {
            return;
        }
        final Group g = p.getPowerupShape();
        if (p.getPlayer().isPlayerTwo()) {
            g.setTranslateX(POWERUP_SLOT_TWO_X + POWERUP_SLOT_CENTER);
        } else {
            g.setTranslateX(POWERUP_SLOT_ONE_X + POWERUP_SLOT_CENTER);
        }
        g.setTranslateY(POWERUP_SLOT_Y + POWERUP_SLOT_CENTER);
        Launcher.getRoot().getChildren().add(g);
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
                    t -> t * LIVES_SIZE + y, LIVES_LINES);
            final Polygon shape = new Polygon(points);

            shape.setFill(Color.WHITE);
            shape.setStrokeWidth(1);
            Launcher.getRoot().getChildren().add(shape);
        }
    }

    /**
     * make an extra life icon.
     * @return the group
     */
	public static Group extraLifeGroup() {
		final Group g = new Group();
		final Line l1 = new Line(0, 10, 20, 10);
		l1.setStroke(Color.WHITE);
		l1.setStrokeWidth(POWERUP_STROKE_WIDTH);
		g.getChildren().add(l1);
		final Line l2 = new Line(10, 0, 10, 20);
		l2.setStroke(Color.WHITE);
		l2.setStrokeWidth(POWERUP_STROKE_WIDTH);
		g.getChildren().add(l2);
		return g;
	}

    /**
     * make a shield icon.
     * @return the group
     */
	public static Group shieldGroup() {
		final Group g = new Group();
		final Circle c = new Circle(10, 10, 10, Color.BLACK);
		c.setStroke(Color.WHITE);
		c.setStrokeWidth(2);
		g.getChildren().add(c);
		return g;
	}

    /**
     * make a bullet size icon.
     * @return the group
     */
	public static Group bulletSizeGroup() {
		final Group g = new Group();
		final Circle c = new Circle(10, 10, 8, Color.WHITE);
		c.setStroke(Color.WHITE);
		c.setStrokeWidth(2);
		g.getChildren().add(c);
		return g;
	}

    /**
     * make a triple shot icon.
     * @return the group
     */
	public static Group tripleShotGroup() {
		final Group g = new Group();
		final Circle c1 = new Circle(4, 14, 2, Color.WHITE);
		c1.setStroke(Color.WHITE);
		c1.setStrokeWidth(2);
		g.getChildren().add(c1);
		final Circle c2 = new Circle(16, 14, 2, Color.WHITE);
		c2.setStroke(Color.WHITE);
		c2.setStrokeWidth(2);
		g.getChildren().add(c2);
		final Circle c3 = new Circle(10, 4, 2, Color.WHITE);
		c3.setStroke(Color.WHITE);
		c3.setStrokeWidth(2);
		g.getChildren().add(c3);
		return g;
	}

    /**
     * make a piercing icon.
     * @return the group
     */
	public static Group piercingGroup() {
		final Group g = new Group();
		final Line l1 = new Line(0, 12, 5, 12);
		l1.setStroke(Color.WHITE);
		l1.setStrokeWidth(POWERUP_STROKE_WIDTH);
		g.getChildren().add(l1);
		final Line l2 = new Line(15, 12, 20, 12);
		l2.setStroke(Color.WHITE);
		l2.setStrokeWidth(POWERUP_STROKE_WIDTH);
		g.getChildren().add(l2);
		final Line l3 = new Line(10, 4, 10, 20);
		l3.setStroke(Color.WHITE);
		l3.setStrokeWidth(POWERUP_STROKE_WIDTH);
		g.getChildren().add(l3);
		final Polygon t = new Polygon();
		t.getPoints().addAll(POWERUP_TRIANGLE);
		t.setFill(Color.WHITE);
		g.getChildren().add(t);
		return g;
	}

    /**
     * make a minigun icon.
     * @return the group
     */
	public static Group minigunGroup() {
		final Group g = new Group();
		final Circle c1 = new Circle(10, 4, 2, Color.WHITE);
		c1.setStroke(Color.WHITE);
		c1.setStrokeWidth(2);
		g.getChildren().add(c1);
		final Circle c2 = new Circle(10, 10, 2, Color.WHITE);
		c2.setStroke(Color.WHITE);
		c2.setStrokeWidth(2);
		g.getChildren().add(c2);
		final Circle c3 = new Circle(10, 16, 2, Color.WHITE);
		c3.setStroke(Color.WHITE);
		c3.setStrokeWidth(2);
		g.getChildren().add(c3);
		return g;
	}
}
