package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import display.DisplayEntity;
import entity.builders.AsteroidBuilder;
import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import entity.builders.SaucerBuilder;
import game.Game;
import game.Launcher;
import javafx.scene.Group;

/**
 * Tests for Saucer.
 * @author Kibo
 *
 */
public class SaucerTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Game thisGame;
	private PlayerBuilder pBuilder;
	private BulletBuilder bBuilder;
	private SaucerBuilder sBuilder;
	private AsteroidBuilder aBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setPlayer(null);
		thisGame.getScoreCounter().setScore(0);
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		
		pBuilder = new PlayerBuilder();
		pBuilder.setX(X_START);
		pBuilder.setY(Y_START);
		pBuilder.setDX(DX_START);
		pBuilder.setDY(DY_START);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		
		bBuilder = new BulletBuilder();
		bBuilder.setX(X_START);
		bBuilder.setY(Y_START);
		bBuilder.setDX(DX_START);
		bBuilder.setDY(DY_START);
		bBuilder.setThisGame(thisGame);
		
		sBuilder = new SaucerBuilder();
		sBuilder.setX(X_START);
		sBuilder.setY(Y_START);
		sBuilder.setDX(DX_START);
		sBuilder.setDY(DY_START);
		sBuilder.setThisGame(thisGame);
		sBuilder.setRadius(Saucer.getBigRadius());
		
		aBuilder = new AsteroidBuilder();
		aBuilder.setX(X_START);
		aBuilder.setY(Y_START);
		aBuilder.setDX(DX_START);
		aBuilder.setDY(DY_START);
		aBuilder.setThisGame(thisGame);
	}
	
	@Test
	public final void testSetPath(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setPath(1);
		assertEquals(0, saucer.getDY(), 0);
	}
	
	@Test
	public final void testUpdate(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setPath(1);
		saucer.update(null);
		assertEquals(X_START + 2, saucer.getX(), 0);
	}
	
	@Test
	public final void testShoot1(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		thisGame.setPlayer(p);
		saucer.update(null);
		assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testShoot2(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.setShotTime(0);
		saucer.update(null);
		assertEquals(1, thisGame.getCreateList().size(), 0);
		final AbstractEntity b = thisGame.getCreateList().get(0);
		assertTrue(b instanceof Bullet);
		assertFalse(((Bullet) b).isFriendly());
	}
	
	@Test
	public final void testShoot3(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.update(null);
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testShoot4(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		assertEquals(1, thisGame.getCreateList().size(), 0);
		final AbstractEntity b = thisGame.getCreateList().get(0);
		assertTrue(b instanceof Bullet);
		assertFalse(((Bullet) b).isFriendly());
	}
	
	@Test
	public final void testShoot5(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.update(null);
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testSmallShotDir(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		pBuilder.setX(Game.getCanvasSize() - X_START);
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		thisGame.getScoreCounter().setScore(120000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		final Bullet b = (Bullet) thisGame.getCreateList().get(0);
		assertEquals(4, b.getDX(), 0.1);
		assertEquals(0, b.getDY(), 0.1);
	}
	
	@Test
	public final void testSmallShotTime(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		pBuilder.setX(Game.getCanvasSize() - X_START);
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		thisGame.getScoreCounter().setScore(50000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		final Bullet b = (Bullet) thisGame.getCreateList().get(0);
		assertEquals(4, b.getDX(), 4);
		assertEquals(0, b.getDY(), 4);
	}
	
	@Test
	public final void testCheckEnd1(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setX(Game.getCanvasSize() + 10);
		saucer.update(null);
		assertTrue(thisGame.getDestroyList().contains(saucer));
	}
	
	@Test
	public final void testCheckEnd2(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setX(-10);
		saucer.update(null);
		assertTrue(thisGame.getDestroyList().contains(saucer));
	}
	
	@Test
	public final void testChangeDirection(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setDirChangeTime(0);
		saucer.update(null);
		assertEquals(System.currentTimeMillis(), saucer.getDirChangeTime(), 0);
	}
	
	@Test
	public final void testDraw(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.draw();
		final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
		final int strokesInShape = DisplayEntity.getSaucerShape().length;
		assertEquals(strokesInShape, strokesInGroup, 0);
	}
	
	@Test
	public final void testCollide(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		saucer.collide(p);
		assertTrue(thisGame.getDestroyList().contains(saucer));
	}
	
	@Test
	public final void testCollide2(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Player p = (Player) pBuilder.getResult();
		saucer.collide(p);
		assertFalse(thisGame.getDestroyList().contains(saucer));
	}
	
	@Test
	public final void testCollide3(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Bullet b = (Bullet) bBuilder.getResult();
		saucer.collide(b);
		assertEquals(2, thisGame.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollide4(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Bullet b = (Bullet) bBuilder.getResult();
		b.setFriendly(false);
		saucer.collide(b);
		assertFalse(thisGame.getDestroyList().contains(saucer));
	}
	
	@Test
	public final void testCollide5(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		final Asteroid a = (Asteroid) aBuilder.getResult();
		saucer.collide(a);
		assertEquals(2, thisGame.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testOnDeath(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.onDeath();
		assertEquals(Saucer.getSmallScore(), thisGame.getScoreCounter().getScore(), 0);
	}
	
	@Test
	public final void testOnDeath2(){
		final Saucer saucer = (Saucer) sBuilder.getResult();
		saucer.onDeath();
		assertEquals(Saucer.getBigScore(), thisGame.getScoreCounter().getScore(), 0);
	}
}
