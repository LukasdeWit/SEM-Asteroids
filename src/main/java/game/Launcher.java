package game;

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

import java.util.ArrayList;
import java.util.List;

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
	private static Group root  = new Group();

	// Make a new Game
	final Game thisGame = new Game();

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
		// set up the title
		stage.setTitle("ASTEROIDS!");
		// set up the scene
		final Scene scene = new Scene(root, 500, 500, Color.BLACK);
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
	 * @param scene - the scene
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
		return root;
	}

}