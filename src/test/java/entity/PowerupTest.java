package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class PowerupTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	
	private Powerup powerup;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		Launcher.getRoot().getChildren().clear();
		powerup = new Powerup(X_START, Y_START);
		powerup.setType(0);
	}
	
	@Test
	public final void testConstructor(){
		assertNotSame(powerup, null);
		assertEquals(X_START, powerup.getX(), 0);
		assertEquals(Y_START, powerup.getY(), 0);
	}
	
	@Test
	public final void testCollide1(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.collide(p);
		assertEquals(p, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide2(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide3(){
		final Powerup p = new Powerup(X_START, Y_START);
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testPickup1(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setType(1);
		powerup.collide(p);
		assertTrue(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public final void testPickup2(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setType(2);
		powerup.collide(p);
		assertEquals(Powerup.getNewBulletSize(), p.getCurrentBulletSize(), 0);
	}
	
	@Test
	public final void testPickup3(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setType(3);
		powerup.collide(p);
		assertTrue(p.isTripleShot());
	}
	
	@Test
	public final void testPickup4(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setType(4);
		powerup.collide(p);
		assertEquals(Powerup.getNewPiercingLevel(), p.getPiercing(), 0);
	}
	
	@Test
	public final void testPickup5(){
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		powerup.setType(5);
		powerup.collide(p);
		assertEquals(Powerup.getNewFireRate(), p.getCurrentFireRate(), 0);
	}
	
	@Test
	public final void onDeath(){
		powerup.onDeath();
		assertFalse(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public void testDraw() {
		powerup.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}
	
	@Test
	public void testDraw2() {
		powerup.setPickupTime(1);
		powerup.draw();
		assertEquals(0, Launcher.getRoot().getChildren().size(), 0);
	}
	
	@Test
	public void testUpdate1() {
		powerup.setStartTime(0);
		powerup.update(null);
		assertTrue(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate2() {
		powerup.update(null);
		assertFalse(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate3() {
		powerup.setPickupTime(1);
		powerup.update(null);
		assertTrue(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate4() {
		powerup.setPickupTime(System.currentTimeMillis());
		powerup.update(null);
		assertFalse(Game.getInstance().getDestroyList().contains(powerup));
	}
	
	@Test
	public void testRunOut1() {
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		p.setBulletSize(0);
		powerup.setPlayer(p);
		powerup.setType(2);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(Player.getBulletSize(), p.getCurrentBulletSize(), 0);
	}
	
	@Test
	public void testRunOut2() {
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		p.setTripleShot(true);
		powerup.setPlayer(p);
		powerup.setType(3);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertFalse(p.isTripleShot());
	}
	
	@Test
	public void testRunOut3() {
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		p.setPiercing(5);
		powerup.setPlayer(p);
		powerup.setType(4);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(1, p.getPiercing(), 0);
	}
	
	@Test
	public void testRunOut4() {
		final Player p = new Player(X_START, Y_START, 3, 4, false);
		p.setFireRate(0);
		powerup.setPlayer(p);
		powerup.setType(5);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(Player.getFireRate(), p.getCurrentFireRate(), 0);
	}
}
