package display;

import game.Launcher;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

    private static final float CONGRATULATIONS_TEXT_X = 80;
	private static final float CONGRATULATIONS_TEXT_Y = 100;
	private static final float PRESS_R_TEXT_X = 80;

    private static final Font DEFAULT_FONT;
	private static final Font HIGHSCORE_FONT;
	private static final Font SCORE_FONT;
	private static final Font LARGE_FONT;
	private static final Font WAVE_FONT;

    private static boolean test;

	static {
		final String fontLoc = "/fonts/HyperspaceBold.otf";
        final float highscoreSize = 0.75f;
        final float scoreSize = 1.5F;
        final float bigTextSize = 2F;
        final float waveSize = 0.75f;

        DEFAULT_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE);
        HIGHSCORE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * highscoreSize);
        SCORE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * scoreSize);
        LARGE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * bigTextSize);
        WAVE_FONT = Font.loadFont(DisplayText.class.getResourceAsStream(fontLoc), DEFAULT_SIZE * waveSize);
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
		final float scoreX = 10;
		final float scoreY = 25;
		if (score == 0) {
			drawText(scoreX, scoreY, SCORE_FONT, "        00");
		} else {
			drawText(scoreX, scoreY, SCORE_FONT, String.format("%1$10s", score));
		}
	}
	
	/**
	 * DisplayText the score.
	 * @param highscore - highscore
	 */
	public static void highscore(final long highscore) {
		final float highscoreX = (float) 200;
		final float highscoreY = (float) 15;
		if (highscore == 0) {
			drawText(highscoreX, highscoreY, HIGHSCORE_FONT, "        00");
		} else {
			drawText(highscoreX, highscoreY, HIGHSCORE_FONT, String.format("%1$10s", highscore));
		}
	}

	/**
	 * draw the start screen.
	 */
	public static void startScreen() {
		final float asteroidsTextX = (float) 140;
		final float pressStartTextX = (float) 100;
		final float startArcadeTextY = (float) 220;
		final float startSurvivalTextY = (float) 280;
		final float pressStartTextBossY = (float) 340;
		final float pressStartTextBosscoopY = (float) 370;
		final float startArcadeCoopTextY = (float) 250;
		final float startSurvivalCoopTextY = (float) 310;
		final float viewHighscoresTextY = (float) 410;
		final float quitTextY = (float) 440;
		final float asteroidsTextY = (float) 100;
		drawText(asteroidsTextX, asteroidsTextY, LARGE_FONT, "asteroids");
		drawText(pressStartTextX, startArcadeTextY, "press a to start arcade");
		drawText(pressStartTextX, startSurvivalTextY, "press s to start survival");
		drawText(pressStartTextX, pressStartTextBossY, "press d for Boss Mode");
		drawText(pressStartTextX, pressStartTextBosscoopY, "press c for Coop Boss Mode");
		drawText(pressStartTextX, startArcadeCoopTextY, "press z to start Arcade coop");
		drawText(pressStartTextX, startSurvivalCoopTextY, "press x to start survival Coop");
		drawText(pressStartTextX, viewHighscoresTextY, "press h to view highscores");
		drawText(pressStartTextX, quitTextY, "press escape to quit the game");
	}

	/**
	 * draw the highscore screen.
	 *
	 * @param score - the score
	 * @param name - the name
	 */
	public static void highscoreScreen(final long score, final String name) {
		final float newHighscoreTextX = (float) 80;
		final float newHighscoreTextY = (float) 250;
		final float enterNameTextY = (float) 310;
		final float nameInputY = (float) 350;
		final int confirmTextY = 390;

		drawText(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, LARGE_FONT, "congratulations");
		drawText(newHighscoreTextX, newHighscoreTextY, "your new highscore is " + score);
		drawText(PRESS_R_TEXT_X, enterNameTextY, "please enter your name ");
		drawText(PRESS_R_TEXT_X, nameInputY, name);
		drawText(PRESS_R_TEXT_X, confirmTextY, "press enter to confirm");
	}

	/**
	 * draw the view highscores screen.
	 * @param strings - the highscore strings
	 */
	public static void viewHighscoresScreen(final String[][] strings) {
        final float highscoresTextX = (float) 140;
        final float highscoresTextY = (float) 100;
        final float highscoresModesTextX = (float) 100;
        final float modesTextStartY = (float) 220;
        final float modeTextSize = (float) 200;
        final int highscoresModesSpace = 30;
        final float deleteTextY = (float) 410;
        final float returnToMainTextY = (float) 440;
        final float scoreTextSize = (float) 100;

        drawText(highscoresTextX, highscoresTextY, LARGE_FONT, "highscores");

        for (int i = 0; i < strings[0].length; i++) {
            drawText(highscoresModesTextX, modesTextStartY + i * highscoresModesSpace, strings[0][i]);
            drawText(highscoresModesTextX + modeTextSize,
					modesTextStartY + i * highscoresModesSpace, strings[1][i]);
            drawText(highscoresModesTextX + modeTextSize + scoreTextSize,
					modesTextStartY + i * highscoresModesSpace, strings[2][i]);
        }
        drawText(highscoresModesTextX, deleteTextY, 			"press d to delete all highscores");
        drawText(highscoresModesTextX, returnToMainTextY, 	"press r to return to main menu");
	}

	/**
	 * draw the pause screen.
	 */
	public static void pauseScreen() {
        final float pressPTextY = (float) 280;
        final float pressRTextY = (float) 310;

        drawText(CONGRATULATIONS_TEXT_X, CONGRATULATIONS_TEXT_Y, LARGE_FONT, "Pause");
        drawText(PRESS_R_TEXT_X, pressPTextY, "press p to continue");
        drawText(PRESS_R_TEXT_X, pressRTextY, "press r to return to main menu");
	}

	/**
	 * DisplayText the wave.
	 *
	 * @param wave - the current wave
	 */
	public static void wave(final int wave) {
        final float waveX = (float) 10;
        final float waveY = (float) 470;
        drawText(waveX, waveY, WAVE_FONT, "Wave " + wave);
	}

	/**
	 * draw a string to the screen.
	 * @param x the horizontal position of the text
	 * @param y the vertical position of the text
	 * @param font the font to use for this text
	 * @param text the text to draw
	 */
	public static void drawText(final float x, final float y, final Font font, final String text) {
		if (test) {
			Launcher.getRoot().getChildren().add(new Line());
			return;
		}
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

	/**
	 * @param test the test to set
	 */
	public static void setTest(final boolean test) {
		DisplayText.test = test;
	}
}
