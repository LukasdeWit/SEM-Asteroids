package entity;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import display.DisplayEntity;
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
	
	private Saucer saucer;

	@Before
	public final void setUp() {
		Game.getInstance().setCreateList(new ArrayList<AbstractEntity>());
		Game.getInstance().setDestroyList(new ArrayList<AbstractEntity>());
		Game.getInstance().setPlayer(null);
		Game.getInstance().setScore(0);
		Launcher.getRoot().getChildren().clear();
		saucer = new Saucer(X_START, Y_START, DX_START, DY_START);
		saucer.setRadius(Saucer.getBigRadius());
	}
	
	@Test
	public final void testConstructor1(){
		assertNotSame(saucer, null);
		assertEquals(X_START, saucer.getX(), 0);
		assertEquals(Y_START, saucer.getY(), 0);
	}

	@Test
	public final void testConstructor2(){
		final Saucer saucer2 = new Saucer(Game.getCanvasSize() - 1, Game.getCanvasSize() - 1, 0, 0);
		assertEquals(Game.getCanvasSize() - 1, saucer2.getX(), 0);
		assertEquals(1, saucer2.getToRight());
	}
	
	@Test
	public final void testSetPath(){
		saucer.setPath(1);
		assertEquals(0, saucer.getDY(), 0);
	}
	
	@Test
	public final void testUpdate(){
		saucer.setPath(1);
		saucer.update(null);
		assertEquals(X_START + 2, saucer.getX(), 0);
	}
	
	@Test
	public final void testShoot1(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		Game.getInstance().setPlayer(p);
		saucer.update(null);
		assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testShoot2(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		saucer.setShotTime(0);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testShoot3(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testShoot4(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testShoot5(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testSmallShotDir(){
		final Player p = new Player(Game.getCanvasSize() - X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		Game.getInstance().setScore(120000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testSmallShotTime(){
		final Player p = new Player(Game.getCanvasSize() - X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		Game.getInstance().setPlayer(p);
		Game.getInstance().setScore(50000);
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.setShotTime(0);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testCheckEnd1(){
		saucer.setX(Game.getCanvasSize() + 10);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testCheckEnd2(){
		saucer.setX(-10);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testChangeDirection(){
		saucer.setDirChangeTime(0);
		saucer.update(null);
		//assertEquals(System.currentTimeMillis(), saucer.getShotTime(), 0);
	}
	
	@Test
	public final void testDraw(){
		saucer.draw();
		final int strokesInGroup = ((Group)Launcher.getRoot().getChildren().get(0)).getChildren().size();
		final int strokesInShape = DisplayEntity.getSaucerShape().length;
		assertEquals(strokesInShape, strokesInGroup, 0);
	}
	
	@Test
	public final void testCollide(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		p.setInvincibleStart(0);
		saucer.collide(p);
		//test
	}
	
	@Test
	public final void testCollide2(){
		final Player p = new Player(X_START, Y_START, DX_START, DY_START, false);
		saucer.collide(p);
		//test
	}
	
	@Test
	public final void testCollide3(){
		final Bullet b = new Bullet(X_START, Y_START, DX_START, DY_START);
		saucer.collide(b);
		//test
	}
	
	@Test
	public final void testCollide4(){
		final Bullet b = new Bullet(X_START, Y_START, DX_START, DY_START);
		b.setFriendly(false);
		saucer.collide(b);
		//test
	}
	
	@Test
	public final void testCollide5(){
		final Asteroid a = new Asteroid(X_START, Y_START, DX_START, DY_START);
		saucer.collide(a);
		//test
	}
	
	@Test
	public final void testOnDeath(){
		saucer.setRadius(Saucer.getSmallRadius());
		saucer.onDeath();
		//test
	}
	
	@Test
	public final void testOnDeath2(){
		saucer.onDeath();
		//test
	}
}
