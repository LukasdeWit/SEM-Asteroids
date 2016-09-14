package game;

import javafx.scene.canvas.GraphicsContext;

/**
 * This class displays all numbers, letters and lives.
 * @author Kibo
 *
 */
public final class Display {
		//what does this checkstyle warning mean?
	/**
	 * The SPACE.
	 */
	private static final float[][] SPACE = {};
	/**
	 * The A.
	 */
	private static final float[][] A = {
			{0, 6, 0, 2},
			{0, 2, 2, 0},
			{2, 0, 4, 2},
			{4, 2, 4, 6},
			{0, 4, 4, 4}
	};
	/**
	 * The B.
	 */
	private static final float[][] B = {
			{0, 4, 0, 0},
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
	/**
	 * The C.
	 */
	private static final float[][] C = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6}
	};
	/**
	 * The D.
	 */
	private static final float[][] D = {
			{0, 0, 0, 6},
			{0, 0, 2, 0},
			{2, 0, 4, 2},
			{4, 2, 4, 4},
			{4, 4, 2, 6},
			{2, 6, 0, 6}
	};
	/**
	 * The E.
	 */
	private static final float[][] E = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{0, 3, 3, 3}
	};
	/**
	 * The F.
	 */
	private static final float[][] F = {
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 3, 3, 3}
	};
	/**
	 * The G.
	 */
	private static final float[][] G = {
			{4, 2, 4, 0},
			{4, 0, 0, 0},
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 4},
			{4, 4, 2, 4}
	};
	/**
	 * The H.
	 */
	private static final float[][] H = {
			{0, 0, 0, 6},
			{4, 0, 4, 6},
			{0, 3, 4, 3}
	};
	/**
	 * The I.
	 */
	private static final float[][] I = {
			{2, 0, 2, 6},
			{0, 0, 4, 0},
			{0, 6, 4, 6},
	};
	/**
	 * The J.
	 */
	private static final float[][] J = {
			{4, 0, 4, 6},
			{4, 6, 2, 6},
			{2, 6, 0, 4}
	};
	/**
	 * The K.
	 */
	private static final float[][] K = {
			{0, 0, 0, 6},
			{4, 0, 0, 3},
			{0, 3, 4, 6}
	};
	/**
	 * The L.
	 */
	private static final float[][] L = {
			{0, 0, 0, 6},
			{0, 6, 4, 6}
	};
	/**
	 * The M.
	 */
	private static final float[][] M = {
			{0, 6, 0, 0},
			{0, 0, 2, 2},
			{2, 2, 4, 0},
			{4, 0, 4, 6}
	};
	/**
	 * The N.
	 */
	private static final float[][] N = {
			{0, 6, 0, 0},
			{0, 0, 4, 6},
			{4, 6, 4, 0}
	};
	/**
	 * The O.
	 */
	private static final float[][] O = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0},
			{4, 0, 0, 0}
	};
	/**
	 * The P.
	 */
	private static final float[][] P = {
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3}
	};
	/**
	 * The Q.
	 */
	private static final float[][] Q = {
			{2, 6, 0, 6},
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 4},
			{4, 4, 2, 6},
			{2, 4, 4, 6}
	};
	/**
	 * The R.
	 */
	private static final float[][] R = {
			{0, 6, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3},
			{1, 3, 4, 6}
	};
	/**
	 * The S.
	 */
	private static final float[][] S = {
			{4, 0, 0, 0},
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 3, 4, 6},
			{4, 6, 0, 6}
	};
	/**
	 * The T.
	 */
	private static final float[][] T = {
			{2, 0, 2, 6},
			{0, 0, 4, 0}
	};
	/**
	 * The U.
	 */
	private static final float[][] U = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0}
	};
	/**
	 * The V.
	 */
	private static final float[][] V = {
			{0, 0, 2, 6},
			{2, 6, 4, 0}
	};
	/**
	 * The W.
	 */
	private static final float[][] W = {
			{0, 0, 0, 6},
			{0, 6, 2, 4},
			{2, 4, 4, 6},
			{4, 6, 4, 0}
	};
	/**
	 * The X.
	 */
	private static final float[][] X = {
			{0, 0, 4, 6},
			{4, 0, 0, 6}
	};
	/**
	 * The Y.
	 */
	private static final float[][] Y = {
			{0, 0, 2, 2},
			{4, 0, 2, 2},
			{2, 2, 2, 6}
	};
	/**
	 * The Z.
	 */
	private static final float[][] Z = {
			{0, 0, 4, 0},
			{4, 0, 0, 6},
			{0, 6, 4, 6}
	};
	/**
	 * All letters.
	 */
	private static final float[][][] LETTERS = 
		{A, B, C, D, E, F, G, H, I, J, K, L, M, N, 
				O, P, Q, R, S, T, U, V, W, X, Y, Z};
	/**
	 * The ZERO.
	 */
	private static final float[][] ZERO = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 0},
			{4, 0, 0, 0}
	};
	/**
	 * The ONE.
	 */
	private static final float[][] ONE = {
			{4, 0, 4, 6}
	};
	/**
	 * The TWO.
	 */
	private static final float[][] TWO = {
			{0, 0, 4, 0},
			{4, 0, 4, 3},
			{4, 3, 0, 3},
			{0, 3, 0, 6},
			{0, 6, 4, 6}
	};
	/**
	 * The THREE.
	 */
	private static final float[][] THREE = {
			{0, 0, 4, 0},
			{4, 0, 4, 6},
			{0, 3, 4, 3},
			{0, 6, 4, 6}
	};
	/**
	 * The FOUR.
	 */
	private static final float[][] FOUR = {
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 0, 4, 6}
	};
	/**
	 * The FIVE.
	 */
	private static final float[][] FIVE = {
			{4, 0, 0, 0},
			{0, 0, 0, 3},
			{0, 3, 4, 3},
			{4, 3, 4, 6},
			{4, 6, 0, 6}
	};
	/**
	 * The SIX.
	 */
	private static final float[][] SIX = {
			{0, 0, 0, 6},
			{0, 6, 4, 6},
			{4, 6, 4, 3},
			{4, 3, 0, 3}
	};
	/**
	 * The SEVEN.
	 */
	private static final float[][] SEVEN = {
			{0, 0, 4, 0},
			{4, 0, 4, 6}
	};
	/**
	 * The EIGHT.
	 */
	private static final float[][] EIGHT = {
			{0, 0, 0, 6},
			{4, 0, 4, 6},
			{0, 0, 4, 0},
			{0, 3, 4, 3},
			{0, 6, 4, 6}
	};
	/**
	 * The NINE.
	 */
	private static final float[][] NINE = {
			{4, 3, 0, 3},
			{0, 3, 0, 0},
			{0, 0, 4, 0},
			{4, 0, 4, 6}
	};
	/**
	 * All numbers.
	 */
	private static final float[][][] NUMBERS = 
			{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE};
	/**
	 * A life.
	 */
	public static final float[][] LIFE = {
			{0, 6, 2, 0},
			{2, 0, 4, 6},
			{4, 6, 3, 5},
			{3, 5, 1, 5},
			{1, 5, 0, 6},
	};
	/**
	 * X coordinate of score display.
	 */
	public static final float SCORE_X = 10;
	/**
	 * Y coordinate of score display.
	 */
	public static final float SCORE_Y = 10;
	/**
	 * Size of score display.
	 */
	public static final float SCORE_SIZE = 4;
	
	/**
	 * Space between characters.
	 */	
	private static final float X_OFFSET = 5.5f;
	
	/**
	 * Draw a letter, number or life.
	 * @param x - left x coordinate
	 * @param y - upper y coordinate
	 * @param size - the size (1 equals 4 by 6 pixels)
	 * @param string - the string you want to draw 
	 * @param gc - the graphics context
	 * (any non-letter, -number, -space will be drawn as a life)
	 */
	public static void draw(final float x, final float y, 
			final float size, final String string, final GraphicsContext gc) {
		char[] charList = string.toCharArray();
		for (int i = 0; i < charList.length; i++) {
			char c = charList[i];
			if (c == ' ') {
				System.out.println(' ');
				drawChar(x + i * X_OFFSET * size, y, size, SPACE, gc);
			} else {
				int cInt = (int) c;
				if (cInt >= (int) 'a' && cInt <= (int) 'z') {
					System.out.println(cInt - (int) 'a');
					drawChar(x + i * X_OFFSET * size, y, 
							size, LETTERS[cInt - (int) 'a'], gc);
				} else if (cInt >= (int) 'A' && cInt <= (int) 'Z') {
					System.out.println(cInt - (int) 'A');
					drawChar(x + i * X_OFFSET * size, y, 
							size, LETTERS[cInt - (int) 'A'], gc);
				} else if (cInt >= (int) '0' && cInt <= (int) '9') {
					System.out.println(cInt - (int) '0');
					drawChar(x + i * X_OFFSET * size, y, 
							size, NUMBERS[cInt - (int) '0'], gc);
				} else {
					System.out.println("*LIFE*");
					drawChar(x + i * X_OFFSET * size, y, size, LIFE, gc);
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
	 * @param gc - the graphics context
	 * (any non-letter, -number, -space will be drawn as a life)
	 */
	private static void drawChar(final float x, final float y, 
			final float size, final float[][] figure, 
			final GraphicsContext gc) {
		for (float[] stroke : figure) {
			gc.strokeLine(
					stroke[0] * size + x, stroke[1] * size + y, 
					stroke[2] * size + x, stroke[1 + 2] * size + y);
				//3 is not a magic number in this case.
		}
	}
	/*
	*//**
	 * test.
	 * @param args - args
	 *//*
	public static void main(final String[] args) {
		draw(0, 0, 0, "abcde#fghijklm^&NOPQRSTUV#WXYz1234567890");
	}*/
}
