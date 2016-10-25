package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * Tests for AngryAsteroidBoss
 * @author Dario
 *
 */

public class AngryAsteroidBossTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private AngryAsteroidBoss angryAsteroidBoss;
	private Game thisGame;
	
	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		angryAsteroidBoss = new AngryAsteroidBoss(X_START, Y_START, DX_START, DY_START, thisGame);
	}

	@Test
	public final void testConstructor() {
		//Assert that the AngryAsteroidBoss was constructed.
		assertNotSame(angryAsteroidBoss, null);
		//Assert that the AngryAsteroidBoss's properties are correct.
		assertEquals(angryAsteroidBoss.getX(), X_START, 0);
		assertEquals(angryAsteroidBoss.getY(), Y_START, 0);
		//Can't test DX or DY, since they are randomly initialized internally.
	}
	
	@Test
	public void testUpdate() {
		//Test basic movement
		final float dX = angryAsteroidBoss.getDX();
		final float dY = angryAsteroidBoss.getDY();
		final List<String> input = new ArrayList<>(0);
		angryAsteroidBoss.update(input);
		assertEquals(angryAsteroidBoss.getX(), X_START + dX, 0);
		assertEquals(angryAsteroidBoss.getY(), Y_START + dY, 0);
	}
	
	@Test
	public void testUpdate2() {
		final AngryAsteroidBoss AngryAsteroidBoss2 = new AngryAsteroidBoss(-1, -1, 0, 0, thisGame);
		//Test resetting of movement values to keep inside screen.
		AngryAsteroidBoss2.setDX(-2);
		AngryAsteroidBoss2.setDY(-2);
		final List<String> input = new ArrayList<>(0);
		AngryAsteroidBoss2.update(input);
		assertEquals(AngryAsteroidBoss2.getDX(), 2.0,0);
		assertEquals(AngryAsteroidBoss2.getDY(), 2.0,0);
	}

	@Test
	public void testDraw() {
		angryAsteroidBoss.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}

	@Test
	public void testCollide() {
		//Hit the AngryAsteroidBoss with a friendly bullet.
		final BulletBuilder bBuilder = new BulletBuilder();
		bBuilder.setX(angryAsteroidBoss.getX());
		bBuilder.setY(angryAsteroidBoss.getY());
		bBuilder.setDX(0f);
		bBuilder.setDY(0f);
		bBuilder.setThisGame(thisGame);
		bBuilder.setFriendly(true);
		final Bullet bullet = (Bullet) bBuilder.getResult();

		angryAsteroidBoss.collide(bullet);
		//Show that the bullet is destroyed but the AngryAsteroidBoss still has lives.
		assertTrue(angryAsteroidBoss.getThisGame().getDestroyList().contains(bullet));
		assertFalse(angryAsteroidBoss.getThisGame().getDestroyList().contains(angryAsteroidBoss));
		for(int i = 0; i < angryAsteroidBoss.getStartingLives(); i++) {
			angryAsteroidBoss.collide(bullet);
		}
		//Show that the AngryAsteroidBoss' lives are exhausted.
		assertTrue(angryAsteroidBoss.getThisGame().getDestroyList().contains(angryAsteroidBoss));
	}

	@Test
	public void testCollide2() {
		final PlayerBuilder pBuilder = new PlayerBuilder();
		//Hit the AngryAsteroidBoss with a player.
		pBuilder.setX(0);
		pBuilder.setY(0);
		pBuilder.setDX(0);
		pBuilder.setDY(0);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		final Player player = (Player) pBuilder.getResult();
		
		final int initiallives = player.getLives();
		angryAsteroidBoss.collide(player);
		//Show that the player doesn't lose a life, since they are currently invinicible.
		assertEquals(initiallives, player.getLives());
		//Make player not invincible and show that they lose a life.
		player.setInvincibleStart(0);
		angryAsteroidBoss.collide(player);
		assertEquals(initiallives - 1, player.getLives());
		assertFalse(angryAsteroidBoss.getThisGame().getDestroyList().contains(angryAsteroidBoss));
	}
	
	@Test
	public void testOnDeath() {
		final double initialScore = angryAsteroidBoss.getThisGame().getScoreCounter().getScore();
		angryAsteroidBoss.onDeath();
		assertEquals(initialScore + 20000,angryAsteroidBoss.getThisGame().getScoreCounter().getScore(),0);
	}

	//Since each method calls the other, more than one test would be silly.
	@Test
	public void testSetPath() {
		final int toRight = angryAsteroidBoss.getToRight();
		final int pathtoset = 1;
		final double predictedangle = Math.PI * toRight + (pathtoset-1) * Math.PI / 4;
		angryAsteroidBoss.setPath(pathtoset);
		assertEquals(angryAsteroidBoss.getDX(),Math.cos(predictedangle) * 2,0);
		assertEquals(angryAsteroidBoss.getDY(),-Math.sin(predictedangle) * 2,0);
	}

	@Test
	public void testCheckEdgeX() {
		final AngryAsteroidBoss AngryAsteroidBoss2 = new AngryAsteroidBoss(-1, -1, 0, 0, thisGame);
		AngryAsteroidBoss2.setDX(-2);
		AngryAsteroidBoss2.checkEdgeX();
		assertEquals(AngryAsteroidBoss2.getDX(), 2.0,0);
	}

	@Test
	public void testCheckEdgeY() {
		final AngryAsteroidBoss AngryAsteroidBoss2 = new AngryAsteroidBoss(-1, -1, 0, 0, thisGame);
		AngryAsteroidBoss2.setDY(-2);
		AngryAsteroidBoss2.checkEdgeY();
		assertEquals(AngryAsteroidBoss2.getDY(), 2.0,0);
	}

}
