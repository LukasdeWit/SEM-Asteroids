package display;

import game.Launcher;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * utility class for displaying text on the screen.
 * @author Kibo
 *
 */
public final class DisplayText {
	private static final float DEFAULT_SIZE = 20;

	private static final float SCORE_X = 10;
	private static final float SCORE_Y = 25;
	private static final float SCORE_SIZE = 1.5F;
	
	private static final float HIGHSCORE_X = 200;
	private static final float HIGHSCORE_Y = 15;
	private static final float HIGHSCORE_SIZE = 0.75f;
	
	private static final float BIG_TEXT_SIZE = 2F;

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

	private static final float WAVE_X = 10;
	private static final float WAVE_Y = 470;
	private static final float WAVE_SIZE = 0.75f;

	private static final Font DEFAULT_FONT;
	private static final Font HIGHSCORE_FONT;
	private static final Font SCORE_FONT;
	private static final Font LARGE_FONT;
	private static final Font WAVE_FONT;

	static {
		final String fontLoc = "/fonts/HyperspaceBold.otf";
		DEFAULT_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE);
		HIGHSCORE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * HIGHSCORE_SIZE);
		SCORE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * SCORE_SIZE);
		LARGE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * BIG_TEXT_SIZE);
		WAVE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * WAVE_SIZE);
	}

	/**
	 * private constructor for utility class.
	 */
	private DisplayText() {
		//not called
	}
	
	/**
	 * DisplayText the score.
	 * @param score - score
	 */
	public static void score(final long score) {
		if (score == 0) {
			drawText(SCORE_X, SCORE_Y, SCORE_FONT, "        00");
		} else {
			drawText(SCORE_X, SCORE_Y, SCORE_FONT, String.format("%1$10s", score));
		}
	}
	
	/**
	 * DisplayText the score.
	 * @param highscore - highscore
	 */
	public static void highscore(final long highscore) {
		if (highscore == 0) {
			drawText(HIGHSCORE_X, HIGHSCORE_Y, HIGHSCORE_FONT, "        00");
		} else {
			drawText(HIGHSCORE_X, HIGHSCORE_Y, HIGHSCORE_FONT, String.format("%1$10s", highscore));
		}
	}

	/**
	 * draw the start screen.
	 */
	public static void startScreen() {
		drawText(ASTEROIDS_TEXT_X, ASTEROIDS_TEXT_Y, LARGE_FONT, "asteroids");
		drawText(PRESS_START_TEXT_X, PRESS_START_TEXT_Y, "press x for Arcade Mode");
		drawText(PRESS_START_TEXT_X, PRESS_START_TEXT_COOP_Y, "press c for Coop");
	}

	/**
	 * draw the highscore screen.
	 *
	 * @param highscore - the highscore
	 */
	public static void highscoreScreen(final long highscore) {
		drawText(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, LARGE_FONT , "congratulations");
		drawText(PRESS_R_TEXT_X, PRESS_R_TEXT_Y, "press r to restart");
		drawText(NEW_HIGHSCORE_TEXT_X, NEW_HIGHSCORE_TEXT_Y, "your new highscore is " + highscore);
	}

	/**
	 * draw the pauze screen.
	 */
	public static void pauseScreen() {
		drawText(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, LARGE_FONT, "Pauze");
		drawText(PRESS_R_TEXT_X, PRESS_R_TEXT_Y, "press p to start");
	}

	/**
	 * DisplayText the wave.
	 *
	 * @param wave - the current wave
	 */
	public static void wave(final int wave) {
		drawText(WAVE_X, WAVE_Y, WAVE_FONT, "Wave " + wave);
	}

	/**
	 * draw a string to the screen.
	 * @param x the horizontal position of the text
	 * @param y the vertical position of the text
	 * @param font the font to use for this text
	 * @param text the text to draw
	 */
	public static void drawText(final float x, final float y, final Font font, final String text) {
		final Text textNode = new Text(x, y, text);
		textNode.setFont(font);
		textNode.setTextAlignment(TextAlignment.CENTER);
		textNode.setFill(Color.WHITE);
		Launcher.getRoot().getChildren().add(textNode);
	}

	/**
	 * draw the text with the default font.
	 * @param x horizontal position of the text
	 * @param y vertical position of the text
	 * @param text the text to draw
	 */
	public static void drawText(final float x, final float y, final String text) {
		drawText(x, y, DEFAULT_FONT, text);
	}
}
