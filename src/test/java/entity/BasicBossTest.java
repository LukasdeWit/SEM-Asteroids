package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import game.Game;
import game.Launcher;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * Tests for basicBoss
 * @author Dario
 *
 */

public class BasicBossTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private BasicBoss basicBoss;
	private Game thisGame;
	
	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		basicBoss = new BasicBoss(X_START, Y_START, DX_START, DY_START, thisGame);
	}

	@Test
	public final void testConstructor() {
		//Assert that the basicBoss was constructed.
		assertNotSame(basicBoss, null);
		//Assert that the basicBoss's properties are correct.
		assertEquals(basicBoss.getX(), X_START, 0);
		assertEquals(basicBoss.getY(), Y_START, 0);
		//Can't test DX or DY, since they are randomly initialized internally.
	}
	
	@Test
	public void testUpdate() {
		//Test basic movement
		final float dX = basicBoss.getDX();
		final float dY = basicBoss.getDY();
		final List<String> input = new ArrayList<>(0);
		basicBoss.update(input);
		assertEquals(basicBoss.getX(), X_START + dX, 0);
		assertEquals(basicBoss.getY(), Y_START + dY, 0);
	}
	
	@Test
	public void testUpdate2() {
		final BasicBoss BasicBoss2 = new BasicBoss(-1, -1, 0, 0, thisGame);
		//Test resetting of movement values to keep inside screen.
		BasicBoss2.setDX(-2);
		BasicBoss2.setDY(-2);
		final List<String> input = new ArrayList<>(0);
		BasicBoss2.update(input);
		assertEquals(BasicBoss2.getDX(), 2.0,0);
		assertEquals(BasicBoss2.getDY(), 2.0,0);
	}

	@Test
	public void testDraw() {
		basicBoss.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Group);
		assertEquals(X_START, c.getTranslateX(), 0);
	}

	@Test
	public void testCollide() {
		//Hit the basicBoss with a friendly bullet.
		final BulletBuilder bBuilder = new BulletBuilder();
		bBuilder.setX(basicBoss.getX());
		bBuilder.setY(basicBoss.getY());
		bBuilder.setDX(0f);
		bBuilder.setDY(0f);
		bBuilder.setThisGame(thisGame);
		bBuilder.setFriendly(true);
		final Bullet bullet = (Bullet) bBuilder.getResult();

		basicBoss.collide(bullet);
		//Show that the bullet is destroyed but the basicBoss still has lives.
		assertTrue(basicBoss.getThisGame().getDestroyList().contains(bullet));
		assertFalse(basicBoss.getThisGame().getDestroyList().contains(basicBoss));
		for(int i = 0; i < basicBoss.getStartingLives(); i++) {
			basicBoss.collide(bullet);
		}
		//Show that the basicBoss' lives are exhausted.
		assertTrue(basicBoss.getThisGame().getDestroyList().contains(basicBoss));
	}

	@Test
	public void testCollide2() {
		final PlayerBuilder pBuilder = new PlayerBuilder();
		//Hit the basicBoss with a player.
		pBuilder.setX(0);
		pBuilder.setY(0);
		pBuilder.setDX(0);
		pBuilder.setDY(0);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		final Player player = (Player) pBuilder.getResult();
		
		final int initiallives = player.getLives();
		basicBoss.collide(player);
		//Show that the player doesn't lose a life, since they are currently invinicible.
		assertEquals(initiallives, player.getLives());
		//Make player not invincible and show that they lose a life.
		player.setInvincibleStart(0);
		basicBoss.collide(player);
		assertEquals(initiallives - 1, player.getLives());
		assertFalse(basicBoss.getThisGame().getDestroyList().contains(basicBoss));
	}
	
	@Test
	public void testOnDeath() {
		final double initialScore = basicBoss.getThisGame().getScoreCounter().getScore();
		basicBoss.onDeath();
		assertEquals(initialScore + 20000,basicBoss.getThisGame().getScoreCounter().getScore(),0);
	}

	//Since each method calls the other, more than one test would be silly.
	@Test
	public void testSetPath() {
		final int toRight = basicBoss.getToRight();
		final int pathtoset = 1;
		final double predictedangle = Math.PI * toRight + (pathtoset-1) * Math.PI / 4;
		basicBoss.setPath(pathtoset);
		assertEquals(basicBoss.getDX(),Math.cos(predictedangle) * 2,0);
		assertEquals(basicBoss.getDY(),-Math.sin(predictedangle) * 2,0);
	}

	@Test
	public void testCheckEdgeX() {
		final BasicBoss BasicBoss2 = new BasicBoss(-1, -1, 0, 0, thisGame);
		BasicBoss2.setDX(-2);
		BasicBoss2.checkEdgeX();
		assertEquals(BasicBoss2.getDX(), 2.0,0);
	}

	@Test
	public void testCheckEdgeY() {
		final BasicBoss BasicBoss2 = new BasicBoss(-1, -1, 0, 0, thisGame);
		BasicBoss2.setDY(-2);
		BasicBoss2.checkEdgeY();
		assertEquals(BasicBoss2.getDY(), 2.0,0);
	}

}
