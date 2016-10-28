package display;

import java.util.function.DoubleFunction;

import entity.AbstractEntity;
import entity.Asteroid;
import entity.AbstractBoss;
import entity.Bullet;
import entity.Particle;
import entity.Player;
import entity.Powerup;
import entity.Saucer;
import game.Launcher;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * This class displays all entities.
 *
 * @author Kibo
 */
public final class DisplayEntity {
	private static final double[][] ASTEROID_SHAPES = {
			{
					-2, -4,
					0, -2,
					2, -4,
					4, -2,
					3, 0,
					4, 2,
					1, 4,
					-2, 4,
					-4, 2,
					-4, -2
			},
			{
					-2, -4,
					0, -3,
					2, -4,
					4, -2,
					2, -1,
					4, 0,
					2, 3,
					-1, 2,
					-2, 3,
					-4, 1,
					-3, 0,
					-4, -2
			},
			{
					-2, -4,
					1, -4,
					4, -2,
					4, -1,
					2, 0,
					4, 2,
					2, 4,
					1, 3,
					-2, 4,
					-4, 1,
					-4, -2,
					-1, -2,
			}
	};
	private static final float ASTEROID_SIZE = .25f;
	private static final float ASTEROID_WIDTH = 4;

	private static final float BULLET_SIZE = 1f;
	private static final float PARTICLE_SIZE = .5f;
	private static final float POWERUP_SIZE = .15f;

	private static final int PLAYER_RESPAWN_FLICKER_TIME = 250;
	private static final float[] PLAYER_TWO_CIRCLE = {11, 0, 9};
	private static final double[][] PLAYER_TWO_LINES = {
			{
					11, 0,
					-7, 0
			},
			{
					-2, 0,
					-8, -6
			},
			{
					-2, 0,
					-8, 6
			},
			{
					0, -6,
					-19, -6
			},
			{
					0, 6,
					-19, 6
			}
	};
	private static final double[][] PLAYER_TWO_BOOST = {
			{-9, 2, -9, -2},
			{-14, 2, -14, -2},
			{-19, 2, -19, -2}
	};
	private static final float PLAYER_TWO_SIZE = .5f;

	private static final double[] PLAYER_ONE_LINES = {
			10, 0,
			-8, 8,
			-8, -8,
	};
	private static final double[] PLAYER_ONE_BOOST = {
			-14, 0,
			-8, -6,
			-14, 0,
			-8, 6
	};
	private static final float PLAYER_ONE_SIZE = .5f;

	private static final double[][] SAUCER_SHAPE = {
			{
					1.25, -3.5,
					2.5, -0.75,
					5, 1,
					2.5, 3,
					-2.5, 3,
					-5, 1,
					-2.5, -0.75,
					-1.25, -3.5,
					1.25, -3.5},
			{
					2.5, -0.75,
					-2.5, -0.75
			},
			{
					5, 1,
					-5, 1
			}
	};

	private static final float SAUCER_SIZE = .20f;
	private static final float SAUCER_WIDTH = 4;
	
	private static final float BOSS_SIZE = 1f;
	private static final double[] POWERUP_SHAPE = {
			0, -5,
			3, 4,
			-4.5, -1.5,
			4.5, -1.5,
			-3, 4,
			0, -5
			
	};
	private static final float POWERUP_WIDTH = 4;

	/**
	 * private constructor for utility class.
	 */
	private DisplayEntity() {
		//no-op
	}

	/**
	 * Draw an asteroid.
	 *
	 * @param a - the asteroid
	 */
	public static void asteroid(final Asteroid a) {
		final Polygon polygon = new Polygon(DisplayUtils.translate(
				d -> d * (a.getRadius() * ASTEROID_SIZE), d -> d * (a.getRadius() * ASTEROID_SIZE),
				ASTEROID_SHAPES[a.getShape()]));

		polygon.setStroke(Color.WHITE);
		polygon.setStrokeWidth(ASTEROID_WIDTH * ASTEROID_SIZE);
		polygon.setTranslateX(a.getX());
		polygon.setTranslateY(a.getY());
		Launcher.getRoot().getChildren().add(polygon);
	}

	/**
	 * Display bullet on screen.
	 *
	 * @param b - the bullet
	 */
	public static void bullet(final Bullet b) {
		drawEntity(b, Color.WHITE, BULLET_SIZE);
	}

	/**
	 * draw a particle on the screen.
	 *
	 * @param p - the particle
	 */
	public static void particle(final Particle p) {
		drawEntity(p, Color.GRAY, PARTICLE_SIZE);
	}

	/**
	 * draw powerup.
	 *
	 * @param p - the powerup
	 */
	public static void powerup(final Powerup p) {
		final Polygon polygon = new Polygon(DisplayUtils.translate(
				d -> d * (p.getRadius() * POWERUP_SIZE), d -> d * (p.getRadius() * POWERUP_SIZE),
				POWERUP_SHAPE));

		polygon.setStroke(Color.WHITE);
		polygon.setFill(Color.WHITE);
		polygon.setStrokeWidth(POWERUP_WIDTH * POWERUP_SIZE);
		polygon.setTranslateX(p.getX());
		polygon.setTranslateY(p.getY());
		Launcher.getRoot().getChildren().add(polygon);
	}
	
	/**
	 * draw boss.
	 * 
	 * @param boss -  the boss
	 */
	public static void boss(final AbstractBoss boss) {
		drawEntity(boss, Color.WHITE, BOSS_SIZE);
	}

	/**
	 * general draw method for entities.
	 *
	 * @param e the entity we want to draw
	 * @param color  the color the entity should be
	 * @param size   the size of the entity
	 */
	private static void drawEntity(final AbstractEntity e, final Paint color, final float size) {
		final Circle c = new Circle(0, 0, e.getRadius() * size);
		c.setFill(color);
		c.setTranslateX(e.getX());
		c.setTranslateY(e.getY());
		Launcher.getRoot().getChildren().add(c);
	}

	/**
	 * draw the player.
	 *
	 * @param p - the player
	 */
	public static void player(final Player p) {
		Paint color = Color.WHITE;
		if (p.invincible() && (System.currentTimeMillis() - p.getInvincibleStart()) % (PLAYER_RESPAWN_FLICKER_TIME * 2)
				< PLAYER_RESPAWN_FLICKER_TIME) {
			color = Color.GREY;
		}

		final Group group = new Group();
		if (p.isPlayerTwo()) {
			playerTwo(p, color, group);
		} else {
			playerOne(p, color, group);
		}

		group.setRotate(Math.toDegrees(-p.getRotation()));
		group.setTranslateX(p.getX());
		group.setTranslateY(p.getY());
		Launcher.getRoot().getChildren().add(group);
	}

	/**
	 * DisplayText Player two on screen.
	 *
	 * @param p     - the player
	 * @param color - the color
	 * @param group scene group to add nodes to
	 */
	private static void playerTwo(final Player p, final Paint color, final Group group) {
		final DoubleFunction<Double> function = d -> d * PLAYER_ONE_SIZE;

		for (final double[] f : PLAYER_TWO_LINES) {
			final Polygon polygon = new Polygon(DisplayUtils.translate(function, function, f));
			polygon.setStroke(color);
			polygon.setStrokeWidth(2 * PLAYER_TWO_SIZE);
			group.getChildren().add(polygon);
		}

		final Circle c = new Circle(PLAYER_TWO_CIRCLE[0] * PLAYER_TWO_SIZE,
				PLAYER_TWO_CIRCLE[1] * PLAYER_TWO_SIZE, PLAYER_TWO_CIRCLE[2] * PLAYER_TWO_SIZE);
		c.setFill(color);
		group.getChildren().add(c);

		if (p.isBoost()) {
			for (final double[] shape : PLAYER_TWO_BOOST) {
				final Polygon boostModel = new Polygon(DisplayUtils.translate(function, function, shape));
				boostModel.setStroke(Color.WHITE);
				boostModel.setStrokeWidth(2 * PLAYER_TWO_SIZE);
				group.getChildren().add(boostModel);
			}
			p.setBoost(false);
		}
	}

	/**
	 * DisplayText Player one on screen.
	 *
	 * @param p     - the player
	 * @param color - the color
	 * @param group scene group to add nodes to
	 */
	private static void playerOne(final Player p, final Paint color, final Group group) {
		final DoubleFunction<Double> function = d -> d * PLAYER_ONE_SIZE;
		final Polygon playerModel = new Polygon(DisplayUtils.translate(function, function, PLAYER_ONE_LINES));
		playerModel.setStroke(color);
		playerModel.setStrokeWidth(2 * PLAYER_ONE_SIZE);
		group.getChildren().add(playerModel);

		if (p.isBoost()) {
			final Polygon boostModel = new Polygon(DisplayUtils.translate(function, function, PLAYER_ONE_BOOST));
			boostModel.setStroke(Color.WHITE);
			boostModel.setStrokeWidth(2 * PLAYER_ONE_SIZE);
			group.getChildren().add(boostModel);
			final Line l = new Line((-PLAYER_ONE_BOOST[0] + 2) * PLAYER_ONE_SIZE, 0,
										(-PLAYER_ONE_BOOST[0] + 2) * PLAYER_ONE_SIZE, 1);
							//so rotation is not wrong

			group.getChildren().add(l);

			p.setBoost(false);
		}
	}

	/**
	 * DisplayText UFO on screen.
	 *
	 * @param s - the saucer
	 */
	public static void saucer(final Saucer s) {
		final Group group = new Group();
		for (final double[] shape : SAUCER_SHAPE) {
			final Polygon polygon = new Polygon(DisplayUtils.translate(p -> p * (s.getRadius() * SAUCER_SIZE),
					p -> p * (s.getRadius() * SAUCER_SIZE), shape));
			polygon.setStroke(Color.WHITE);
			polygon.setStrokeWidth(SAUCER_WIDTH * SAUCER_SIZE);
			group.getChildren().add(polygon);
		}

		group.setTranslateX(s.getX());
		group.setTranslateY(s.getY());
		Launcher.getRoot().getChildren().add(group);
	}

	/**
	 * @return the asteroidShapes
	 */
	public static double[][] getAsteroidShapes() {
		return ASTEROID_SHAPES.clone();
	}

	/**
	 * @return the playerOneLines
	 */
	public static double[] getPlayerOneLines() {
		return PLAYER_ONE_LINES.clone();
	}

	/**
	 * @return the saucerShape
	 */
	public static double[][] getSaucerShape() {
		return SAUCER_SHAPE.clone();
	}
}
