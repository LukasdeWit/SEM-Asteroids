package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.builders.PlayerBuilder;
import entity.shooters.PlayerShooter;
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

	private Game thisGame;
	private Powerup powerup;
	private PlayerBuilder pBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		powerup = new Powerup(X_START, Y_START, thisGame);
		powerup.setType(0);
		
		pBuilder = new PlayerBuilder();
		pBuilder.setX(X_START);
		pBuilder.setY(Y_START);
		pBuilder.setDX(3);
		pBuilder.setDY(4);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
	}
	
	@Test
	public final void testConstructor(){
		assertNotSame(powerup, null);
		assertEquals(X_START, powerup.getX(), 0);
		assertEquals(Y_START, powerup.getY(), 0);
	}
	
	@Test
	public final void testCollide1(){
		final Player p = (Player) pBuilder.getResult();
		powerup.collide(p);
		assertEquals(p, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide2(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide3(){
		final Powerup p = new Powerup(X_START, Y_START, thisGame);
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testPickup1(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(1);
		powerup.collide(p);
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public final void testPickup2(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(2);
		powerup.collide(p);
		assertEquals(Powerup.getNewBulletSize(), p.getShooter().getCurrentBulletSize(), 0);
	}
	
	@Test
	public final void testPickup3(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(3);
		powerup.collide(p);
		assertTrue(p.getShooter().isTripleShot());
	}
	
	@Test
	public final void testPickup4(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(4);
		powerup.collide(p);
		assertEquals(Powerup.getNewPiercingLevel(), p.getShooter().getPiercing(), 0);
	}
	
	@Test
	public final void testPickup5(){
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(5);
		powerup.collide(p);
		assertEquals(Powerup.getNewFireRate(), p.getShooter().getCurrentFireRate(), 0);
	}
	
	@Test
	public final void onDeath(){
		powerup.onDeath();
		assertFalse(thisGame.getDestroyList().contains(powerup));
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
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate2() {
		powerup.update(null);
		assertFalse(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate3() {
		powerup.setPickupTime(1);
		powerup.update(null);
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate4() {
		powerup.setPickupTime(System.currentTimeMillis());
		powerup.update(null);
		assertFalse(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testRunOut1() {
		final Player p = (Player) pBuilder.getResult();
		p.getShooter().setBulletSize(0);
		powerup.setPlayer(p);
		powerup.setType(2);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(PlayerShooter.getBulletSize(), p.getShooter().getCurrentBulletSize(), 0);
	}
	
	@Test
	public void testRunOut2() {
		final Player p = (Player) pBuilder.getResult();
		p.getShooter().setTripleShot(true);
		powerup.setPlayer(p);
		powerup.setType(3);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertFalse(p.getShooter().isTripleShot());
	}
	
	@Test
	public void testRunOut3() {
		final Player p = (Player) pBuilder.getResult();
		p.getShooter().setPiercing(5);
		powerup.setPlayer(p);
		powerup.setType(4);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(1, p.getShooter().getPiercing(), 0);
	}
	
	@Test
	public void testRunOut4() {
		final Player p = (Player) pBuilder.getResult();
		p.getShooter().setFireRate(0);
		powerup.setPlayer(p);
		powerup.setType(5);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(PlayerShooter.getFireRate(), p.getShooter().getCurrentFireRate(), 0);
	}
}
