import java.util.ArrayList;

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
 * This class is the main launcher of the game
 * @author Lukas
 *
 */
public class Launcher extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// set up the title
		stage.setTitle("ASTEROIDS!");
		
		// set up the scene
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		// set up the canvas
		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);
		
		// set up the graphicsContext
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// set up the keyhandler
		ArrayList<String> input = new ArrayList<String>();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				
				if(!input.contains(code)) input.add(code);
			}	
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});
		
		//Make a new Game
		Game thisGame= new Game(gc);
		
		// set up the timing control
		Timeline renderloop = new Timeline();
		renderloop.setCycleCount(Timeline.INDEFINITE);
		
		//final long startTime = System.currentTimeMillis();
		
		KeyFrame kf = new KeyFrame(
			Duration.seconds(0.017), 
			new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
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
