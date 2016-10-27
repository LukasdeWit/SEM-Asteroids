package game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the main launcher of the game.
 *
 * @author Lukas
 */
public class Launcher extends Application {
	/**
	 * Time of one frame.
	 */
	private static final double FRAME_TIME = 0.017;
	private static final Group ROOT = new Group();

	// Make a new Game
	private final Game thisGame = new Game();
	private static Stage thisStage;

	/**
	 * Main method.
	 *
	 * @param args standard
	 */
	public static void main(final String... args) {
		launch(args);
	}

	/**
	 * starts the window and boots the game.
	 *
	 * @param stage the stage for the scenes
	 */
	@Override
	public final void start(final Stage stage) {
		Launcher.setThisStage(stage);
		// set up the title
		stage.setTitle("ASTEROIDS!");
		// set up the scene
		final Scene scene = new Scene(ROOT, 500, 500, Color.BLACK);
		stage.setScene(scene);
		final List<String> input = getInput(scene);
		// set up the timing control
		final Timeline renderLoop = new Timeline();
		renderLoop.setCycleCount(Timeline.INDEFINITE);
		final KeyFrame kf = new KeyFrame(Duration.seconds(FRAME_TIME),
				new EventHandler<ActionEvent>() {
					/**
					 * Updates game based on keyboard input.
					 */
					@Override
					public void handle(final ActionEvent e) {
						thisGame.update(input);
					}
				});
		// add game to scene
		renderLoop.getKeyFrames().add(kf);
		renderLoop.play();
		// show game
		stage.show();
	}

	/**
	 * get the input.
	 *
	 * @param scene the scene
	 * @return the input
	 */
	private List<String> getInput(final Scene scene) {
		// set up the key handler
		final ArrayList<String> input = new ArrayList<>();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			/**
			 * Add key code to input when key is pressed.
			 */
			@Override
			public void handle(final KeyEvent e) {
				final String code = e.getCode().toString();
				if (!input.contains(code)) {
					input.add(code);
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			/**
			 * Remove key code from input when key is released.
			 */
			@Override
			public void handle(final KeyEvent e) {
				final String code = e.getCode().toString();
				input.remove(code);
			}
		});
		return input;
	}

	/**
	 * @return the root
	 */
	public static Group getRoot() {
		return ROOT;
	}

	/**
	 * quit the game.
	 */
	public static void quit() {
		Launcher.getThisStage().close();
	}

	/**
	 * @return the thisStage
	 */
	public static final Stage getThisStage() {
		return thisStage;
	}

	/**
	 * @param thisStage the thisStage to set
	 */
	public static final void setThisStage(final Stage thisStage) {
		Launcher.thisStage = thisStage;
	}

}