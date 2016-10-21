package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.builders.BossBuilder;
import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import game.Game;
import game.Launcher;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * Tests for Boss
 * @author Dario
 *
 */

public class BossTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	
	private BossBuilder bBuilder;
	private Game thisGame;
	
	@Before
	public void setUp() throws Exception {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		
		bBuilder = new BossBuilder();
		bBuilder.setThisGame(thisGame);
		bBuilder.setX(X_START);
		bBuilder.setY(Y_START);
		bBuilder.setDX(DX_START);
		bBuilder.setDY(DY_START);
	}
	
	@Test
	public void testUpdate() {
		final Boss boss = (Boss) bBuilder.getResult();
		//Test basic movement
		final float dX = boss.getDX();
		final float dY = boss.getDY();
		final List<String> input = new ArrayList<>(0);
		boss.update(input);
		assertEquals(boss.getX(), X_START + dX, 0);
		assertEquals(boss.getY(), Y_START + dY, 0);
	}
	
	@Test
	public void testUpdate2() {
		bBuilder.setX(-1);
		bBuilder.setY(-1);
		bBuilder.setDX(0);
		bBuilder.setDY(0);
		final Boss boss2 = (Boss) bBuilder.getResult();
		//Test resetting of movement values to keep inside screen.
		boss2.setDX(-2);
		boss2.setDY(-2);
		final List<String> input = new ArrayList<>(0);
		boss2.update(input);
		assertEquals(boss2.getDX(), 2.0,0);
		assertEquals(boss2.getDY(), 2.0,0);
	}

	@Test
	public void testDraw() {
		final Boss boss = (Boss) bBuilder.getResult();
		boss.draw();
		final Node c = Launcher.getRoot().getChildren().get(0);
		assertTrue(c instanceof Circle);
		assertEquals(X_START, c.getTranslateX(), 0);
	}

	@Test
	public void testCollide() {
		final Boss boss = (Boss) bBuilder.getResult();
		//Hit the boss with a friendly bullet.
		final BulletBuilder bulletBuilder = new BulletBuilder();
		bulletBuilder.setX(boss.getX());
		bulletBuilder.setY(boss.getY());
		bulletBuilder.setDX(0f);
		bulletBuilder.setDY(0f);
		bulletBuilder.setThisGame(thisGame);
		bulletBuilder.setFriendly(true);
		final Bullet bullet = (Bullet) bulletBuilder.getResult();

		boss.collide(bullet);
		//Show that the bullet is destroyed but the boss still has lives.
		assertTrue(boss.getThisGame().getDestroyList().contains(bullet));
		assertFalse(boss.getThisGame().getDestroyList().contains(boss));
		for(int i = 0; i < boss.getStartingLives(); i++) {
			boss.collide(bullet);
		}
		//Show that the boss' lives are exhausted.
		assertTrue(boss.getThisGame().getDestroyList().contains(boss));
	}

	@Test
	public void testCollide2() {
		final Boss boss = (Boss) bBuilder.getResult();
		final PlayerBuilder pBuilder = new PlayerBuilder();
		//Hit the boss with a player.
		pBuilder.setX(0);
		pBuilder.setY(0);
		pBuilder.setDX(0);
		pBuilder.setDY(0);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		final Player player = (Player) pBuilder.getResult();
		
		final int initiallives = player.getLives();
		boss.collide(player);
		//Show that the player doesn't lose a life, since they are currently invinicible.
		assertEquals(initiallives, player.getLives());
		//Make player not invincible and show that they lose a life.
		player.setInvincibleStart(0);
		boss.collide(player);
		assertEquals(initiallives - 1, player.getLives());
		assertFalse(boss.getThisGame().getDestroyList().contains(boss));
	}
	
	@Test
	public void testOnDeath() {
		final Boss boss = (Boss) bBuilder.getResult();
		final double initialScore = boss.getThisGame().getScoreCounter().getScore();
		boss.onDeath();
		assertEquals(initialScore + 20000,boss.getThisGame().getScoreCounter().getScore(),0);
	}

	//Since each method calls the other, more than one test would be silly.
	@Test
	public void testSetPath() {
		final Boss boss = (Boss) bBuilder.getResult();
		final int toRight = boss.getToRight();
		final int pathtoset = 1;
		final double predictedangle = Math.PI * toRight + (pathtoset-1) * Math.PI / 4;
		boss.setPath(pathtoset);
		assertEquals(boss.getDX(),Math.cos(predictedangle) * 2,0);
		assertEquals(boss.getDY(),-Math.sin(predictedangle) * 2,0);
	}

	@Test
	public void testCheckEdgeX() {
		bBuilder.setX(-1);
		bBuilder.setY(-1);
		bBuilder.setDX(0);
		bBuilder.setDY(0);
		final Boss boss2 = (Boss) bBuilder.getResult();
		boss2.setDX(-2);
		boss2.checkEdgeX();
		assertEquals(boss2.getDX(), 2.0,0);
	}

	@Test
	public void testCheckEdgeY() {
		bBuilder.setX(-1);
		bBuilder.setY(-1);
		bBuilder.setDX(0);
		bBuilder.setDY(0);
		final Boss boss2 = (Boss) bBuilder.getResult();
		boss2.setDY(-2);
		boss2.checkEdgeY();
		assertEquals(boss2.getDY(), 2.0,0);
	}

}
