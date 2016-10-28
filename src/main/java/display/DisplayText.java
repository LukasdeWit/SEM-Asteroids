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
        float highscoreSize = 0.75f;
        float scoreSize = 1.5F;
        float bigTextSize = 2F;
        float waveSize = 0.75f;

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
		float scoreX = 10;
		float scoreY = 25;
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
		float highscoreX = (float) 200;
		float highscoreY = (float) 15;
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
		float asteroidsTextX = (float) 140;
		float pressStartTextX = (float) 100;
		float startArcadeTextY = (float) 220;
		float startSurvivalTextY = (float) 280;
		float pressStartTextBossY = (float) 340;
		float pressStartTextBosscoopY = (float) 370;
		float startArcadeCoopTextY = (float) 250;
		float startSurvivalCoopTextY = (float) 310;
		float viewHighscoresTextY = (float) 410;
		float quitTextY = (float) 440;
		float asteroidsTextY = (float) 100;
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
		float newHighscoreTextX = (float) 80;
		float newHighscoreTextY = (float) 250;
		float enterNameTextY = (float) 310;
		float nameInputY = (float) 350;
		int confirmTextY = 390;

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
        float highscoresTextX = (float) 140;
        float highscoresTextY = (float) 100;
        float highscoresModesTextX = (float) 100;
        float modesTextStartY = (float) 220;
        float modeTextSize = (float) 200;
        int highscoresModesSpace = 30;
        float deleteTextY = (float) 410;
        float returnToMainTextY = (float) 440;
        float scoreTextSize = (float) 100;

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
        float pressPTextY = (float) 280;
        float pressRTextY = (float) 310;

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
        float waveX = (float) 10;
        float waveY = (float) 470;
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
