package entity;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock; 
import org.mockito.Mock;

import game.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;


public class AsteroidTest {
Asteroid Ceres; 
@Mock Game mockGame;

	@Before
	public void setUp() throws Exception {
		mockGame = mock(Game.class);
		/**Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Game.CANVAS_SIZE, Game.CANVAS_SIZE);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		mockGame = new Game(gc);**/
		Ceres = new Asteroid(0, 0, 0, 0, mockGame);
	}

	@Test
	public void testConstructor() {
		//Assert that the Asteroid was constructed.
		assertFalse(Ceres == null);
		//Assert that the Asteroid's properties are correct.
		assertTrue(Ceres.getDX()==0);
		assertTrue(Ceres.getDY()==0);
		assertTrue(Ceres.getX()==0);
		assertTrue(Ceres.getY()==0);
		assertTrue(Ceres.getThisGame()==mockGame);
	}
	
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDraw() {
		fail("Not yet implemented");
	}

	@Test
	public void testCollide() {
		fail("Not yet implemented");
	}

	@Test
	public void testAsteroid() {
		fail("Not yet implemented");
	}

	@Test
	public void testSplit() {
		fail("Not yet implemented");
	}

}
