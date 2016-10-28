package entity.shooters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.AbstractEntity;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import entity.builders.BulletBuilder;
import entity.builders.PlayerBuilder;
import game.Game;
import game.Launcher;

public class SaucerShooterTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;

	private Game thisGame;
	private Saucer saucer;
	private PlayerBuilder pBuilder;
	private BulletBuilder bBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setPlayer(null);
		thisGame.getScoreCounter().setScore(0);
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		saucer = new Saucer(X_START, Y_START, DX_START, DY_START, thisGame);
		saucer.setRadius(Saucer.getBigRadius());
		
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
	}
	
	@Test
	public final void testSmallShotDir(){
		pBuilder.setX(Game.getCanvasSize() - X_START);
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		thisGame.getScoreCounter().setScore(120000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.getShooter().setLastShot(0);
		saucer.update(null);
		final Bullet b = (Bullet) thisGame.getCreateList().get(0);
		assertEquals(4, b.getDX(), 0.1);
		assertEquals(0, b.getDY(), 0.1);
	}
	
	@Test
	public final void testSmallShotTime(){
		pBuilder.setX(Game.getCanvasSize() - X_START);
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		thisGame.getScoreCounter().setScore(50000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.getShooter().setLastShot(0);
		saucer.update(null);
		final Bullet b = (Bullet) thisGame.getCreateList().get(0);
		assertEquals(4, b.getDX(), 4);
		assertEquals(0, b.getDY(), 4);
	}
	
	@Test
	public final void testShoot1(){
		final Player p = (Player) pBuilder.getResult();
		thisGame.setPlayer(p);
		saucer.update(null);
		assertEquals(System.currentTimeMillis(), saucer.getShooter().getLastShot(), 0);
	}
	
	@Test
	public final void testShoot2(){
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.getShooter().setLastShot(0);
		saucer.update(null);
		assertEquals(1, thisGame.getCreateList().size(), 0);
		final AbstractEntity b = thisGame.getCreateList().get(0);
		assertTrue(b instanceof Bullet);
		assertFalse(((Bullet) b).isFriendly());
	}
	
	@Test
	public final void testShoot3(){
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.update(null);
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public final void testShoot4(){
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.getShooter().setLastShot(0);
		saucer.update(null);
		assertEquals(1, thisGame.getCreateList().size(), 0);
		final AbstractEntity b = thisGame.getCreateList().get(0);
		assertTrue(b instanceof Bullet);
		assertFalse(((Bullet) b).isFriendly());
	}
	
	@Test
	public final void testShoot5(){
		final Player p = (Player) pBuilder.getResult();
		p.setInvincibleStart(0);
		thisGame.setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.update(null);
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
}
