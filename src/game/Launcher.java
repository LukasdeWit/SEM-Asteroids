package game;
import java.util.ArrayList;

import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	 * @param args
	 *            - standard
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	/**
	 * starts the window and boots the game.
	 * 
	 * @param stage
	 *            - the stage for the scenes
	 */
	@Override
	public final void start(final Stage stage) throws Exception {
		// set up the title
		stage.setTitle("ASTEROIDS!");

		// set up the scene
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		// set up the canvas
		Canvas canvas = new Canvas(Game.CANVAS_SIZE, Game.CANVAS_SIZE);
		root.getChildren().add(canvas);

		// set up the graphicsContext
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// set up the keyhandler
		ArrayList<String> input = new ArrayList<String>();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent e) {
				String code = e.getCode().toString();

				if (!input.contains(code)) {
					input.add(code);
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});

		// Make a new Game
		Game thisGame = new Game(gc);

		// set up the timing control
		Timeline renderloop = new Timeline();
		renderloop.setCycleCount(Timeline.INDEFINITE);

		// final long startTime = System.currentTimeMillis();

		KeyFrame kf = new KeyFrame(Duration.seconds(FRAME_TIME), 
				new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				thisGame.update(input);
			}
		});

		// add game to scene
		renderloop.getKeyFrames().add(kf);
		renderloop.play();

		// show game
		stage.show();
	}

}