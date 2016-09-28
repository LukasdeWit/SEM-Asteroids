package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This class displays all numbers, letters and lives.
 * @author Kibo
 *
 */
public final class Display {
	private static final float[][] SPACE = {};
	private static final float[][] A = {
			{0, 6, 0, 2},
			{0, 2, 2, 0},
			{2, 0, 4, 2},
			{4, 2, 4, 6},
			{0, 4, 4, 4}
	};
	private static final float[][] B = {
			{0, 6, 0, 0},
			{0, 0, 3, 0},
			{3, 0, 4, 1},
			{4, 1, 4, 2},
			{4, 2, 3, 3},
			{3, 3, 4, 4},
			{4, 4, 4, 5},
			{4, 5, 3, 6},
			{3, 6, 0, 6},
			{0, 3, 3, 3}
	};
	private static final float[][] C = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6}
	};
	private static final float[][] D = {
			{0, 0, 0, 6},
			{0, 0, 2, 0},
			{2, 0, 4, 2},
			{4, 2, 4, 4},
			{4, 4, 2, 6},
			{2, 6, 0, 6}
	};
	private static final float[][] E = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{0, 3, 3, 3}
	};
	private static final float[][] F = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 3, 3, 3}
	};
	private static final float[][] G = {
			{4, 2, 4, 0},
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 4},
			{4, 4, 2, 4}
	};
	private static final float[][] H = {
			{0, 0, 0, 6},
			{4, 0, 4, 6},
			{0, 3, 4, 3}
	};
	private static final float[][] I = {
			{2, 0, 2, 6},
			{0, 0, 4, 0},
			{0, 6, 4, 6},
	};
	private static final float[][] J = {
			{4, 0, 4, 6},
			{4, 6, 2, 6},
			{2, 6, 0, 4}
	};
	private static final float[][] K = {
			{0, 0, 0, 6},
			{4, 0, 0, 3},
			{0, 3, 4, 6}
	};
	private static final float[][] L = {
			{0, 0, 0, 6},
			{0, 6, 4, 6}
	};
	private static final float[][] M = {
			{0, 6, 0, 0},
			{0, 0, 2, 2},
			{2, 2, 4, 0},
			{4, 0, 4, 6}
	};
	private static final float[][] N = {
			{0, 6, 0, 0},
			{0, 0, 4, 6},
			{4, 6, 4, 0}
	};
	private static final float[][] O = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0},
			{4, 0, 0, 0}
	};
	private static final float[][] P = {
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3}
	};
	private static final float[][] Q = {
			{2, 6, 0, 6},
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 4},
			{4, 4, 2, 6},
			{2, 4, 4, 6}
	};
	private static final float[][] R = {
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3},
			{1, 3, 4, 6}
	};
	private static final float[][] S = {
			{4, 0, 0, 0},
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 3, 4, 6},
			{4, 6, 0, 6}
	};
	private static final float[][] T = {
			{2, 0, 2, 6},
			{0, 0, 4, 0}
	};
	private static final float[][] U = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0}
	};
	private static final float[][] V = {
			{0, 0, 2, 6},
			{2, 6, 4, 0}
	};
	private static final float[][] W = {
			{0, 0, 0, 6},
			{0, 6, 2, 4},
			{2, 4, 4, 6},
			{4, 6, 4, 0}
	};
	private static final float[][] X = {
			{0, 0, 4, 6},
			{4, 0, 0, 6}
	};
	private static final float[][] Y = {
			{0, 0, 2, 2},
			{4, 0, 2, 2},
			{2, 2, 2, 6}
	};
	private static final float[][] Z = {
			{0, 0, 4, 0},
			{4, 0, 0, 6},
			{0, 6, 4, 6}
	};
	private static final float[][][] LETTERS = 
		{A, B, C, D, E, F, G, H, I, J, K, L, M, N, 
				O, P, Q, R, S, T, U, V, W, X, Y, Z};
	private static final float[][] ZERO = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0},
			{4, 0, 0, 0}
	};
	private static final float[][] ONE = {
			{4, 0, 4, 6}
	};
	private static final float[][] TWO = {
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3},
			{0, 3, 0, 6},
			{0, 6, 4, 6}
	};
	private static final float[][] THREE = {
			{0, 0, 4, 0},
			{4, 0, 4, 6},
			{0, 3, 4, 3},
			{0, 6, 4, 6}
	};
	private static final float[][] FOUR = {
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 0, 4, 6}
	};
	private static final float[][] FIVE = {
			{4, 0, 0, 0},
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 3, 4, 6},
			{4, 6, 0, 6}
	};
	private static final float[][] SIX = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 3},
			{4, 3, 0, 3}
	};
	private static final float[][] SEVEN = {
			{0, 0, 4, 0},
			{4, 0, 4, 6}
	};
	private static final float[][] EIGHT = {
			{0, 0, 0, 6},
			{4, 0, 4, 6},
			{0, 0, 4, 0},
			{0, 3, 4, 3},
			{0, 6, 4, 6}
	};
	private static final float[][] NINE = {
			{4, 3, 0, 3},
			{0, 3, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 6}
	};
	private static final float[][][] NUMBERS = 
			{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE};
	private static final float[][] LIFE = {
			{0, 6, 2, 0},
			{2, 0, 4, 6},
			{4, 6, 3, 5},
			{3, 5, 1, 5},
			{1, 5, 0, 6},
	};
	private static final float SCORE_X = 10;
	private static final float SCORE_Y = 10;
	private static final float SCORE_SIZE = 3;
	
	private static final float HIGHSCORE_X = 200;
	private static final float HIGHSCORE_Y = 15;
	private static final float HIGHSCORE_SIZE = 1.5f;
	
	private static final float BIG_TEXT_SIZE = 4.f;
	private static final float SMALL_TEXT_SIZE = 2.f;
	
	private static final float ASTEROIDS_TEXT_X = 140;
	private static final float ASTEROIDS_TEXT_Y = 100;
	private static final float PRESS_START_TEXT_X = 100;
	private static final float PRESS_START_TEXT_Y = 250;
	private static final float PRESS_START_TEXT_COOP_Y = 280;
	private static final float CONGRATULATIONS_TEXT_X = 80;
	private static final float CONGRATULATIONS_TEXT_Y = 100;
	private static final float PRESS_R_TEXT_X = 80;
	private static final float PRESS_R_TEXT_Y = 280;
	private static final float NEW_HIGHSCORE_TEXT_X = 80;
	private static final float NEW_HIGHSCORE_TEXT_Y = 250;
	
	private static final int X_OFFSET = 6;
	private static final float LIVES_X = 10;
	private static final float LIVES_TWO_X = 300;
	private static final float LIVES_Y = 40;
	private static final float LIVES_SIZE = 2;
	
	private static final float WAVE_X = 10;
	private static final float WAVE_Y = 470;
	private static final float WAVE_SIZE = 2;
	
	
	/**
	 * private constructor for utility class.
	 */
	private Display() {
	      //not called
	   }
	/**
	 * Display the score.
	 * @param score - score
	 */
	public static void score(final long score) {
		if (score == 0) {
			draw(SCORE_X, SCORE_Y, SCORE_SIZE, "        00");
		} else {
			draw(SCORE_X, SCORE_Y, SCORE_SIZE,
					String.format("%1$10s", score));
		}
	}
	
	/**
	 * Display the score.
	 * @param highscore - highscore
	 */
	public static void highscore(final long highscore) {
		if (highscore == 0) {
			draw(HIGHSCORE_X, HIGHSCORE_Y, HIGHSCORE_SIZE, "        00");
		} else {
			draw(HIGHSCORE_X, HIGHSCORE_Y, HIGHSCORE_SIZE,
					String.format("%1$10s", highscore));
		}
	}
	
	/**
	 * draw the start screen.
	 */
	public static void startScreen() {
		draw(ASTEROIDS_TEXT_X, ASTEROIDS_TEXT_Y, BIG_TEXT_SIZE, 
				"asteroids");
		draw(PRESS_START_TEXT_X, PRESS_START_TEXT_Y, SMALL_TEXT_SIZE, 
				"press x for Arcade Mode");
		draw(PRESS_START_TEXT_X, PRESS_START_TEXT_COOP_Y, SMALL_TEXT_SIZE, 
				"press c for Coop");
	}
	
	/**
	 * draw the highscore screen.
	 * @param highscore - the highscore
	 */
	public static void highscoreScreen(final long highscore) {
		draw(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, BIG_TEXT_SIZE, 
				"congratulations");
		draw(PRESS_R_TEXT_X, PRESS_R_TEXT_Y, SMALL_TEXT_SIZE, 
				"press r to restart");
		draw(NEW_HIGHSCORE_TEXT_X, NEW_HIGHSCORE_TEXT_Y, SMALL_TEXT_SIZE, 
				"your new highscore is " + highscore);
	}
	
	/**
	 * draw the pauze screen.
	 */
	public static void pauseScreen() {
		draw(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, BIG_TEXT_SIZE, 
				"Pauze");
		draw(PRESS_R_TEXT_X, PRESS_R_TEXT_Y, SMALL_TEXT_SIZE, 
				"press p to start");
	}
	
	/**
	 * Display the lives.
	 * @param lives - number of lives
	 */
	public static void lives(final int lives) {
		if (lives <= 0) {
			return;
		}
		final StringBuilder outputBuffer = new StringBuilder(lives);
		for (int i = 0; i < lives; i++) {
		   outputBuffer.append('*');
		}
		final String livesString = outputBuffer.toString();
		draw(LIVES_X, LIVES_Y, LIVES_SIZE, livesString);
	}
	/**
	 * draw lives for coop.
	 * @param lives - lives of player one
	 */
	public static void livesTwo(final int lives) {
		if (lives <= 0) {
			return;
		}
		final StringBuilder outputBuffer = new StringBuilder(lives);
		for (int i = 0; i < lives; i++) {
		   outputBuffer.append('*');
		}
		final String livesString = outputBuffer.toString();
		draw(LIVES_TWO_X, LIVES_Y, LIVES_SIZE, livesString);
	}

	/**
	 * Display the wave.
	 * @param wave - the current wave
	 */
	public static void wave(final int wave) {
		final String waveString = "Wave " + wave;
		draw(WAVE_X, WAVE_Y, WAVE_SIZE, waveString);
	}
	
	/**
	 * Draw a letter, number or life.
	 * @param x - left x coordinate
	 * @param y - upper y coordinate
	 * @param size - the size (1 equals 4 by 6 pixels)
	 * @param string - the string you want to draw 
	 * (any non-letter, -number, -space will be drawn as a life)
	 */
	public static void draw(final float x, final float y, 
			final float size, final String string) {
		final char[] charList = string.toCharArray();
		for (int i = 0; i < charList.length; i++) {
			final char c = charList[i];
			if (c == ' ') {
				drawChar(x + i * X_OFFSET * size, y, size, SPACE);
			} else {
				final int cInt = (int) c;
				if (cInt >= (int) 'a' && cInt <= (int) 'z') {
					drawChar(x + i * X_OFFSET * size, y, 
							size, LETTERS[cInt - (int) 'a']);
				} else if (cInt >= (int) 'A' && cInt <= (int) 'Z') {
					drawChar(x + i * X_OFFSET * size, y, 
							size, LETTERS[cInt - (int) 'A']);
				} else if (cInt >= (int) '0' && cInt <= (int) '9') {
					drawChar(x + i * X_OFFSET * size, y, 
							size, NUMBERS[cInt - (int) '0']);
				} else {
					drawChar(x + i * X_OFFSET * size, y, size, LIFE);
				}
			}
		}
	}
	
	/**
	 * Draw a letter, number or life.
	 * @param x - left x coordinate
	 * @param y - upper y coordinate
	 * @param size - the size (1 equals 4 by 6 pixels)
	 * @param figure - the figure you want to draw
	 * (any non-letter, -number, -space will be drawn as a life)
	 */
	private static void drawChar(final float x, final float y, 
			final float size, final float[][] figure) {
		for (final float[] stroke : figure) {
			final Line l = new Line(
					stroke[0] * size + x, stroke[1] * size + y, 
					stroke[2] * size + x, stroke[1 + 2] * size + y);
				//3 is not a magic number in this case.
			l.setStroke(Color.WHITE);
			l.setStrokeWidth(1);
			Launcher.getRoot().getChildren().add(l);
		}
	}
}
