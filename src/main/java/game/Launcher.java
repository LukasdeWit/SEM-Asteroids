package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * This class is the main launcher of the game.
 * 
 * @author Lukas
 *
 */
public class Launcher extends Application {
	/**
	 * Time of one frame.
	 */
	private static final double FRAME_TIME = 0.017;

	/**
	 * Main method.
	 * 
	 * @param args - standard
	 */
	public static void main(final String... args) {
		launch(args);
	}

	/**
	 * starts the window and boots the game.
	 * 
	 * @param stage - the stage for the scenes
	 */
	@Override
	public final void start(final Stage stage) {
		stage.setTitle("ASTEROIDS!");

		// set up the scene
		final Group root = new Group();
		final Scene scene = new Scene(root);
		stage.setScene(scene);

		// set up the canvas
		final Canvas canvas = 
				new Canvas(Game.getCanvasSize(), Game.getCanvasSize());
		root.getChildren().add(canvas);

		final GraphicsContext gc = canvas.getGraphicsContext2D();
		final Set<String> input = new HashSet<>();

		scene.setOnKeyPressed(e -> input.add(e.getCode().getName()
			.toUpperCase(Locale.ENGLISH)));

		// remove key code from input when key is released.
		scene.setOnKeyReleased(e -> input.remove(e.getCode().getName()
				.toUpperCase(Locale.ENGLISH)));

		final Game thisGame = new Game(gc);

		// set up the timing control
		final Timeline renderLoop = new Timeline();
		renderLoop.setCycleCount(Timeline.INDEFINITE);

		// add game to scene
		renderLoop.getKeyFrames().add(
				new KeyFrame(Duration.seconds(FRAME_TIME),
				event -> thisGame.update(input)));
		renderLoop.play();

		stage.show(); // show game
	}

}