package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import entity.AbstractEntity;
import entity.Asteroid;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import javafx.scene.shape.Rectangle;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class GameTest {
	
	private final Game game = Game.getInstance();
	private final List<String> noInput = new ArrayList<String>();

	@Before
	public final void setUp() {
		Gamestate.getInstance().setMode(Gamestate.getModeArcade());
		game.setScore(0);
		game.setHighscore(0);
		game.setEntities(new ArrayList<AbstractEntity>());
		Launcher.getRoot().getChildren().clear();
		game.setDestroyList(new ArrayList<AbstractEntity>());
		game.setCreateList(new ArrayList<AbstractEntity>());
		game.setPlayer(null);
		game.setPlayerTwo(null);
	}
	
	@Test
	public final void testStartGame1(){
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.setScore(10);
		game.startGame();
		assertEquals(0, Game.getInstance().getScore(), 0);
		assertEquals(10, Game.getInstance().getHighscore(), 0);
	}
	
	@Test
	public final void testStartGame2(){
		game.setScore(5);
		game.setHighscore(20);
		game.startGame();
		assertEquals(0, Game.getInstance().getScore(), 0);
		assertEquals(20, Game.getInstance().getHighscore(), 0);
	}
	
	@Test
	public final void testUpdate(){
		game.update(noInput);
		assertTrue(Launcher.getRoot().getChildren().get(0) instanceof Rectangle);
	}
	
	@Test
	public final void testUpdateGame1(){
		final Asteroid a = new Asteroid(0, 0, 1, 0);
		addToEntities(a);
		game.updateGame(noInput);
		assertEquals(1, a.getX(), 0);
	}
	
	@Test
	public final void testUpdateGame2(){
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		final Player p = new Player(0, 0, 0, 0, false);
		game.setPlayer(p);
		game.updateGame(noInput);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdateGame3(){
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		final Player p = new Player(0, 0, 0, 0, false);
		game.setPlayer(p);
		game.setPlayerTwo(p);
		game.updateGame(noInput);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testCollision1(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0);
		final Bullet e2 = new Bullet(0, 1, 0, 0);
		addToEntities(e2);
		game.checkCollision(e1);
		assertEquals(2, game.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision2(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0);
		final Bullet e2 = new Bullet(0, 1, 0, 0);
		game.destroy(e2);
		addToEntities(e2);
		game.checkCollision(e1);
		assertEquals(1, game.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision3(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0);
		final Bullet e2 = new Bullet(0, 1, 0, 0);
		game.destroy(e1);
		addToEntities(e2);
		game.checkCollision(e1);
		assertEquals(1, game.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision4(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0);
		final Bullet e2 = new Bullet(0, 100, 0, 0);
		addToEntities(e2);
		game.checkCollision(e1);
		assertEquals(0, game.getDestroyList().size(), 0);
	}

	private final void addToEntities(final AbstractEntity a) {
		final List<AbstractEntity> entities = game.getEntities();
		entities.add(a);
		game.setEntities(entities);
	}

	@Test
	public final void testCreate() {
		final Asteroid a = new Asteroid(0, 0, 0, 0);
		game.create(a);
		assertTrue(game.getCreateList().contains(a));
	}

	@Test
	public final void testOver1() {
		game.over();
		assertEquals(0, game.getDestroyList().size(), 0);
	}

	@Test
	public final void testOver2() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		game.setPlayerTwo(p2);
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.over();
		assertTrue(game.getDestroyList().contains(p1));
	}

	@Test
	public final void testOver3() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		p2.setLives(0);
		game.setPlayerTwo(p2);
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.over();
		assertEquals(2, game.getDestroyList().size(), 0);
	}

	@Test
	public final void testOver4() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		game.setPlayerTwo(p2);
		game.over();
		assertTrue(game.getDestroyList().contains(p1));
	}

	@Test
	public final void testOver5() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		game.setScore(10);
		game.over();
		assertTrue(game.getDestroyList().contains(p1));
		assertEquals(10, game.getHighscore(), 0);
	}

	@Test
	public final void testAddScore1() {
		game.addScore(10);
		assertEquals(10, game.getScore(), 0);
	}

	@Test
	public final void testAddScore2() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		game.setPlayerTwo(p2);
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.addScore(10000);
		assertEquals(10000, game.getScore(), 0);
		assertEquals(4, p1.getLives(), 0);
		assertEquals(4, p2.getLives(), 0);
	}

	@Test
	public final void testAddScore3() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		game.setPlayer(p1);
		game.addScore(10000);
		assertEquals(10000, game.getScore(), 0);
		assertEquals(4, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore4() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		p2.setLives(0);
		game.setPlayerTwo(p2);
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.addScore(10000);
		assertEquals(0, game.getScore(), 0);
		assertEquals(0, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore5() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		game.addScore(10000);
		assertEquals(0, game.getScore(), 0);
		assertEquals(0, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore6() {
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, false);
		game.setPlayerTwo(p2);
		Gamestate.getInstance().setMode(Gamestate.getModeCoop());
		game.addScore(10000);
		assertEquals(10000, game.getScore(), 0);
		assertEquals(1, p1.getLives(), 0);
		assertEquals(4, p2.getLives(), 0);
	}
	
	@Test
	public final void testBullets1(){
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0);
		b.setPlayer(p1);
		addToEntities(b);
		assertEquals(1, game.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets2(){
		final Player p1 = new Player(0, 0, 0, 0, false);
		final Player p2 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0);
		b.setPlayer(p2);
		addToEntities(b);
		assertEquals(0, game.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets3(){
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0);
		b.setFriendly(false);
		addToEntities(b);
		assertEquals(0, game.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets4(){
		final Player p1 = new Player(0, 0, 0, 0, false);
		p1.setLives(0);
		game.setPlayer(p1);
		final Asteroid b = new Asteroid(0, 0, 0, 0);
		addToEntities(b);
		assertEquals(0, game.bullets(p1), 0);
	}
	
	@Test
	public final void testEnemies(){
		final Bullet b = new Bullet(0, 0, 0, 0);
		final Saucer s = new Saucer(0, 0, 0, 0);
		final Asteroid a = new Asteroid(0, 0, 0, 0);
		addToEntities(b);
		addToEntities(s);
		addToEntities(a);
		assertEquals(2, game.enemies(), 0);
	}
	
	@Test
	public final void testGetters(){
		assertNull(game.getPlayerTwo());
		assertTrue(game.getRandom() instanceof Random);
	}
}
