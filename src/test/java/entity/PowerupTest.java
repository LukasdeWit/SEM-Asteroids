package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.builders.PlayerBuilder;
import entity.builders.PowerupBuilder;
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
	private PlayerBuilder pBuilder;
	private PowerupBuilder powerupBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		
		powerupBuilder = new PowerupBuilder();
		powerupBuilder.setThisGame(thisGame);
		powerupBuilder.setX(X_START);
		powerupBuilder.setY(Y_START);
		powerupBuilder.setDX(0);
		powerupBuilder.setDY(0);
		powerupBuilder.setType(0);
		
		pBuilder = new PlayerBuilder();
		pBuilder.setX(X_START);
		pBuilder.setY(Y_START);
		pBuilder.setDX(3);
		pBuilder.setDY(4);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
	}
	
	@Test
	public final void testCollide1(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.collide(p);
		assertEquals(p, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide2(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testCollide3(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Powerup p = (Powerup) powerupBuilder.getResult();
		powerup.setPickupTime(1);
		powerup.collide(p);
		assertEquals(null, powerup.getPlayer());
	}
	
	@Test
	public final void testPickup1(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(1);
		powerup.collide(p);
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public final void testPickup2(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(2);
		powerup.collide(p);
		assertEquals(Powerup.getNewBulletSize(), p.getCurrentBulletSize(), 0);
	}
	
	@Test
	public final void testPickup3(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(3);
		powerup.collide(p);
		assertTrue(p.isTripleShot());
	}
	
	@Test
	public final void testPickup4(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(4);
		powerup.collide(p);
		assertEquals(Powerup.getNewPiercingLevel(), p.getPiercing(), 0);
	}
	
	@Test
	public final void testPickup5(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		powerup.setType(5);
		powerup.collide(p);
		assertEquals(Powerup.getNewFireRate(), p.getCurrentFireRate(), 0);
	}
	
	@Test
	public final void onDeath(){
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.onDeath();
		assertFalse(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testDraw() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}
	
	@Test
	public void testDraw2() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.setPickupTime(1);
		powerup.draw();
		assertEquals(0, Launcher.getRoot().getChildren().size(), 0);
	}
	
	@Test
	public void testUpdate1() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.setStartTime(0);
		powerup.update(null);
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate2() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.update(null);
		assertFalse(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate3() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.setPickupTime(1);
		powerup.update(null);
		assertTrue(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testUpdate4() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		powerup.setPickupTime(System.currentTimeMillis());
		powerup.update(null);
		assertFalse(thisGame.getDestroyList().contains(powerup));
	}
	
	@Test
	public void testRunOut1() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setBulletSize(0);
		powerup.setPlayer(p);
		powerup.setType(2);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(Player.getBulletSize(), p.getCurrentBulletSize(), 0);
	}
	
	@Test
	public void testRunOut2() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setTripleShot(true);
		powerup.setPlayer(p);
		powerup.setType(3);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertFalse(p.isTripleShot());
	}
	
	@Test
	public void testRunOut3() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setPiercing(5);
		powerup.setPlayer(p);
		powerup.setType(4);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(1, p.getPiercing(), 0);
	}
	
	@Test
	public void testRunOut4() {
		final Powerup powerup = (Powerup) powerupBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setFireRate(0);
		powerup.setPlayer(p);
		powerup.setType(5);
		powerup.setPickupTime(1);
		powerup.update(null);
		assertEquals(Player.getFireRate(), p.getCurrentFireRate(), 0);
	}
}
