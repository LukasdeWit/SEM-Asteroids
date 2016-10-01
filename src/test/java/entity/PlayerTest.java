package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import display.DisplayEntity;
import game.Game;
import game.Gamestate;
import game.Launcher;
import javafx.scene.Group;

public class PlayerTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	private static final String SPACE = "SPACE";
	private Player player;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		Gamestate.getInstance().setMode(Gamestate.getModeArcade());
		Launcher.getRoot().getChildren().clear();
		player = new Player(X_START, Y_START, DX_START, DY_START, false);
	}
	
	@Test
	public void testConstructor1() {
		assertNotSame(player, null);
		assertEquals(X_START, player.getX(), 0);
		assertEquals(Y_START, player.getY(), 0);
	}
	
	@Test
	public void testConstructor2() {
		assertEquals(DX_START, player.getDX(), 0);
		assertEquals(DY_START, player.getDY(), 0);
		assertFalse(player.isPlayerTwo());
	}

	@Test
	public void testOnDeath() {
		player.onDeath();
		assertTrue(Game.getInstance().getDestroyList().isEmpty());
	}

	@Test
	public void testOnHit1() {
		player.onHit();
		assertEquals(2, player.getLives(), 0);
	}

	@Test
	public void testOnHit2() {
		player.setShielding(1);
		player.onHit();
		assertEquals(3, player.getLives(), 0);
		assertEquals(0, player.getShielding(), 0);
	}
	
	@Test
	public void testOnHit3() {
		player.setLives(1);
		player.onHit();
		assertEquals(0, player.getLives(), 0);
	}

	@Test
	public void testOnHit4() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		player2.onHit();
		assertEquals(Game.getInstance().getScreenX() / 2 + Player.getSpawnOffset(), player2.getX(), 0);
	}

	@Test
	public void testOnHit5() {
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		player.onHit();
		assertEquals(Game.getInstance().getScreenX() / 2 - Player.getSpawnOffset(), player.getX(), 0);
	}
	
	@Test
	public void testGainLife1() {
		player.gainLife();
		assertEquals(4,player.getLives(),0);
	}
	
	@Test
	public void testGainLife2() {
		player.setLives(0);
		player.gainLife();
		assertEquals(1,player.getLives(),0);
		assertEquals(Game.getInstance().getScreenX() / 2, player.getX(), 0);
	}
	
	@Test
	public void testGainLife3() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		player2.setLives(0);
		player2.gainLife();
		assertEquals(1,player2.getLives(),0);
		assertEquals(Game.getInstance().getScreenX() / 2 + Player.getSpawnOffset(), player2.getX(), 0);
	}
	
	@Test
	public void testGainLife4() {
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		player.setLives(0);
		player.gainLife();
		assertEquals(1,player.getLives(),0);
		assertEquals(Game.getInstance().getScreenX() / 2 - Player.getSpawnOffset(), player.getX(), 0);
	}
	
	@Test
	public void testUpdate() {
		final List<String> input = new ArrayList<String>();	
		player.update(input);
		assertEquals(X_START+DX_START,player.getX(),0);
		assertEquals(Y_START+DY_START,player.getY(),0);
	}
	
	@Test
	public void testKeyHandler1() {
		final String[] input = {"LEFT"};
		update(player, input, false);
		assertEquals(Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandler2() {
		final String[] input = {"RIGHT"};
		update(player, input, false);
		assertEquals(-Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandler3() {
		final String[] input = {"A"};
		update(player, input, false);
		assertEquals(Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandler4() {
		final String[] input = {"D"};
		update(player, input, false);
		assertEquals(-Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandler5() {
		final String[] input = {"UP"};
		update(player, input, false);
		assertTrue(player.isBoost());
	}

	@Test
	public void testKeyHandler6() {
		final String[] input = {"W"};
		update(player, input, false);
		assertTrue(player.isBoost());
	}

	@Test
	public void testKeyHandler7() {
		final String[] input = {"DOWN"};
		update(player, input, false);
		assertTrue(System.currentTimeMillis() == player.getHyperspaceStart() || player.getLives() == 2);
	}

	@Test
	public void testKeyHandler8() {
		final String[] input = {"S"};
		update(player, input, false);
		assertTrue(System.currentTimeMillis() == player.getHyperspaceStart() || player.getLives() == 2);
	}

	@Test
	public void testKeyHandler9() {
		final String[] input = {SPACE};
		update(player, input, false);
		assertEquals(System.currentTimeMillis(), player.getLastShot(), 1);
	}
	

	
	@Test
	public void testKeyHandlerTwo1() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		final String[] input = {"LEFT"};
		update(player2, input, true);
		assertEquals(Player.getRotationSpeed(),player2.getRotation(),0);
	}

	@Test
	public void testKeyHandlerTwo2() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		final String[] input = {"RIGHT"};
		update(player2, input, true);
		assertEquals(-Player.getRotationSpeed(),player2.getRotation(),0);
	}

	@Test
	public void testKeyHandlerTwo3() {
		final String[] input = {"A"};
		update(player, input, true);
		assertEquals(Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandlerTwo4() {
		final String[] input = {"D"};
		update(player, input, true);
		assertEquals(-Player.getRotationSpeed(),player.getRotation(),0);
	}

	@Test
	public void testKeyHandlerTwo5() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		final String[] input = {"UP"};
		update(player2, input, true);
		assertTrue(player2.isBoost());
	}

	@Test
	public void testKeyHandlerTwo6() {
		final String[] input = {"W"};
		update(player, input, true);
		assertTrue(player.isBoost());
	}

	@Test
	public void testKeyHandlerTwo7() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		final String[] input = {"DOWN"};
		update(player2, input, true);
		assertTrue(System.currentTimeMillis() == player2.getHyperspaceStart() || player2.getLives() == 2);
	}

	@Test
	public void testKeyHandlerTwo8() {
		final String[] input = {"S"};
		update(player, input, true);
		assertTrue(System.currentTimeMillis() == player.getHyperspaceStart() || player.getLives() == 2);
	}

	@Test
	public void testKeyHandlerTwo9() {
		final String[] input = {SPACE};
		update(player, input, true);
		assertEquals(System.currentTimeMillis(), player.getLastShot(), 1);
	}

	@Test
	public void testKeyHandlerTwo10() {
		final Player player2 = new Player(X_START, Y_START, DX_START + 2, DY_START + 2, true);
		final String[] input = {"ENTER"};
		update(player2, input, true);
		assertEquals(System.currentTimeMillis(), player2.getLastShot(), 1);
	}
	
	@Test
	public void testAccelerate() {
		final String[] input = {"W"};
		player.setDX(0);
		player.setDY(0);
		update(player, input, false);
		assertNotSame(Player.getMaxSpeed(), player.speed());
	}
	
	@Test
	public void testGoHyperspace() {
		final String[] input = {"S"};
		player.setChangeOfDying(1);
		update(player, input, false);
		assertEquals(2, player.getLives(), 0);
	}
	
	@Test
	public void testFire() {
		final String[] input = {SPACE};
		player.setTripleShot(true);
		update(player, input, false);
		assertEquals(3, Game.getInstance().getCreateList().size(), 0);
	}
	
	@Test
	public void testFire2() {
		final String[] input = {SPACE};
		update(player, input, false);
		update(player, input, false);
		assertEquals(1, Game.getInstance().getCreateList().size(), 0);
	}
	
	@Test
	public void testFire3() {
		final String[] input = {SPACE};
		player.setMaxBullets(0);
		update(player, input, false);
		assertEquals(0, Game.getInstance().getCreateList().size(), 0);
	}
	
	@Test
	public void testCollide() {
		final AbstractEntity ae = new Asteroid(X_START, Y_START, DX_START, DY_START);
		player.setInvincibleStart(0);
		player.collide(ae);
		assertEquals(1, Game.getInstance().getDestroyList().size(), 0);
		assertEquals(2, player.getLives(), 0);
	}
	
	@Test
	public void testCollide2() {
		final AbstractEntity ae = new Asteroid(X_START, Y_START, DX_START, DY_START);
		player.collide(ae);
		assertEquals(System.currentTimeMillis(), player.getInvincibleStart(), 2);
	}
	
	@Test
	public void testCollide3() {
		final AbstractEntity ae = new Asteroid(X_START, Y_START, DX_START, DY_START);
		player.setHyperspaceStart(System.currentTimeMillis());
		player.collide(ae);
		assertEquals(System.currentTimeMillis(), player.getInvincibleStart(), 2);
	}
	
	@Test
	public void testCollide4() {
		final AbstractEntity ae = new Asteroid(X_START, Y_START, DX_START, DY_START);
		player.setInvincibleStart(0);
		player.collide(ae);
		assertEquals(System.currentTimeMillis(), player.getInvincibleStart(), 2);
	}
	
	@Test
	public void testCollide5() {
		final AbstractEntity ae = new Asteroid(X_START, Y_START, DX_START, DY_START);
		player.setHyperspaceStart(System.currentTimeMillis());
		player.setInvincibleStart(0);
		player.collide(ae);
		assertEquals(System.currentTimeMillis(), player.getInvincibleStart(), 2);
	}
	
	@Test
	public void testCollide6() {
		final AbstractEntity ae = new Bullet(X_START, Y_START, DX_START, DY_START);
		player.collide(ae);
		assertEquals(3, player.getLives(), 0);
	}
	
	@Test
	public void testCollide7() {
		final AbstractEntity ae = new Bullet(X_START, Y_START, DX_START, DY_START);
		((Bullet) ae).setFriendly(false);
		player.collide(ae);
		assertEquals(2, player.getLives(), 0);
	}
	
	@Test
	public void testCollide8() {
		final AbstractEntity ae = new Player(X_START, Y_START, DX_START, DY_START, false);
		player.collide(ae);
		assertEquals(3, player.getLives(), 0);
	}
	
	@Test
	public void testDraw(){
		player.draw();
		final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
		final int strokesInShape = DisplayEntity.getPlayerOneLines().length;
		assertEquals(strokesInShape, strokesInGroup, 0);
	}
	
	@Test
	public void testIsAlive1(){
		assertTrue(player.isAlive());
	}
	
	@Test
	public void testIsAlive2(){
		player.setLives(0);
		assertFalse(player.isAlive());
	}
	
	@Test
	public void testGettersAndSetters(){
		player.setBulletSize(Player.getBulletSize());
		player.setPiercing(Player.getMaxBullets());
		player.setFireRate(Player.getFireRate());
		player.gainShield();
		assertEquals(1, player.getShielding(), 0);
	}
	
	private void update(final Player player, final String[] in, final boolean coop){
		final List<String> input = new ArrayList<String>();	
		for (final String string : in) {
			input.add(string);
		}
		if (coop) {
			Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		}
		player.setInvincibleStart(0);
		player.update(input);
	}
}
