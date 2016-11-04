package display;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import entity.Player;
import entity.Powerup;
import game.Game;
import game.Launcher;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class DisplayHudTest {
	
	@Before
	public void setUp() {
		Launcher.getRoot().getChildren().clear();
	}
	
	@Test
	public void testLives1() {
		DisplayHud.lives(0, false);
		Node n = Launcher.getRoot().getChildren().get(0);
		assertTrue(n instanceof Rectangle);
	}
	
	@Test
	public void testLives2() {
		DisplayHud.lives(3, false);
		assertFalse(Launcher.getRoot().getChildren().isEmpty());
		for (Node n : Launcher.getRoot().getChildren()) {
			assertTrue (n instanceof Rectangle || n instanceof Polygon);
		}
	}
	
	@Test
	public void testLives3() {
		DisplayHud.lives(3, true);
		assertFalse(Launcher.getRoot().getChildren().isEmpty());
		for (Node n : Launcher.getRoot().getChildren()) {
			assertTrue (n instanceof Rectangle || n instanceof Polygon);
		}
	}
	
	@Test
	public void testPowerup1() {
		Powerup p = new Powerup(10, 10, new Game());
		DisplayHud.powerup(p);
		assertTrue(Launcher.getRoot().getChildren().isEmpty());
	}
	
	@Test
	public void testPowerup2() {
		Powerup p = new Powerup(10, 10, new Game());
		p.setPlayer(new Player());
		DisplayHud.powerup(p);
		assertTrue(Launcher.getRoot().getChildren().get(0) instanceof Group);
	}
	
	@Test
	public void testPowerup3() {
		Powerup p = new Powerup(10, 10, new Game());
		Player player = new Player();
		player.setThisGame(new Game());
		player.setPlayerTwo(true);
		p.setPlayer(player);
		DisplayHud.powerup(p);
		assertTrue(Launcher.getRoot().getChildren().get(0) instanceof Group);
	}
	
	@Test
	public void testExtraLifeGroup() {
		Group group = DisplayHud.extraLifeGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Polygon || n instanceof Line);
		}
	}
	
	@Test
	public void testShieldGroup() {
		Group group = DisplayHud.shieldGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Circle);
		}
	}
	
	@Test
	public void testBulletSizeGroup() {
		Group group = DisplayHud.bulletSizeGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Circle);
		}
	}
	
	@Test
	public void testTripleShotGroup() {
		Group group = DisplayHud.tripleShotGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Circle);
		}
	}
	
	@Test
	public void testPiercingGroup() {
		Group group = DisplayHud.piercingGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Polygon || n instanceof Line);
		}
	}
	
	@Test
	public void testMinigunGroup() {
		Group group = DisplayHud.minigunGroup();
		assertFalse(group.getChildren().isEmpty());
		for (Node n : group.getChildren()) {
			assertTrue(n instanceof Circle);
		}
	}
	
	@Test
	public void testSound1() {
		DisplayHud.sound(false);
		assertFalse(Launcher.getRoot().getChildren().isEmpty());
		Node node = Launcher.getRoot().getChildren().get(0);
		assertTrue(node instanceof Group);
		Group g = (Group) node;
		assertFalse(g.getChildren().isEmpty());
		for (Node n : g.getChildren()) {
			assertTrue(n instanceof Polygon || n instanceof Line);
		}
	}

	@Test
	public void testSound2() {
		DisplayHud.sound(true);
		assertFalse(Launcher.getRoot().getChildren().isEmpty());
		Node node = Launcher.getRoot().getChildren().get(0);
		assertTrue(node instanceof Group);
		Group g = (Group) node;
		assertFalse(g.getChildren().isEmpty());
		for (Node n : g.getChildren()) {
			assertTrue(n instanceof Polygon);
		}
	}
	
}
