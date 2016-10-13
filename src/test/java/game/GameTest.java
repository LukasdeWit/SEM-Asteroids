package game;

import entity.*;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests for Asteroid.
 * @author Kibo
 *
 */
public class GameTest {

	private final Game thisGame = new Game();
	private final Gamestate gamestate = thisGame.getGamestate();
	private final List<String> noInput = new ArrayList<>();

	@Before
	public final void setUp() {
		gamestate.setMode(Gamestate.getModeArcade());
		thisGame.getScoreCounter().setScore(0);
		thisGame.getScoreCounter().setHighscore(0);
		thisGame.setEntities(new ArrayList<>());
		Launcher.getRoot().getChildren().clear();
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setPlayer(null);
		thisGame.setPlayerTwo(null);
	}
	
	@Test
	public final void testStartGame1(){
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.getScoreCounter().setScore(10);
		thisGame.startGame();
		assertEquals(0, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(10, thisGame.getScoreCounter().getHighscore(), 0);
	}
	
	@Test
	public final void testStartGame2(){
		thisGame.getScoreCounter().setScore(5);
		thisGame.getScoreCounter().setHighscore(20);
		thisGame.startGame();
		assertEquals(0, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(20, thisGame.getScoreCounter().getHighscore(), 0);
	}
	
	@Test
	public final void testUpdate(){
		thisGame.update(noInput);
		assertTrue(Launcher.getRoot().getChildren().get(0) instanceof Rectangle);
	}
	
	@Test
	public final void testUpdateGame1(){
		final Asteroid a = new Asteroid(0, 0, 1, 0, thisGame);
		addToEntities(a);
		thisGame.updateGame(noInput);
		assertEquals(1, a.getX(), 0);
	}
	
	@Test
	public final void testUpdateGame2(){
		gamestate.setMode(Gamestate.getModeCoop());
		final Player p = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayer(p);
		thisGame.updateGame(noInput);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testUpdateGame3(){
		gamestate.setMode(Gamestate.getModeCoop());
		final Player p = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayer(p);
		thisGame.setPlayerTwo(p);
		thisGame.updateGame(noInput);
		assertTrue(Launcher.getRoot().getChildren().size() > 0);
	}
	
	@Test
	public final void testCollision1(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0, thisGame);
		final Bullet e2 = new Bullet(0, 1, 0, 0, thisGame);
		addToEntities(e2);
		thisGame.checkCollision(e1);
		assertEquals(2, thisGame.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision2(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0, thisGame);
		final Bullet e2 = new Bullet(0, 1, 0, 0, thisGame);
		thisGame.destroy(e2);
		addToEntities(e2);
		thisGame.checkCollision(e1);
		assertEquals(1, thisGame.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision3(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0, thisGame);
		final Bullet e2 = new Bullet(0, 1, 0, 0, thisGame);
		thisGame.destroy(e1);
		addToEntities(e2);
		thisGame.checkCollision(e1);
		assertEquals(1, thisGame.getDestroyList().size(), 0);
	}
	
	@Test
	public final void testCollision4(){
		final Asteroid e1 = new Asteroid(0, 0, 1, 0, thisGame);
		final Bullet e2 = new Bullet(0, 100, 0, 0, thisGame);
		addToEntities(e2);
		thisGame.checkCollision(e1);
		assertEquals(0, thisGame.getDestroyList().size(), 0);
	}

	private void addToEntities(final AbstractEntity a) {
		final List<AbstractEntity> entities = thisGame.getEntities();
		entities.add(a);
		thisGame.setEntities(entities);
	}

	@Test
	public final void testCreate() {
		final Asteroid a = new Asteroid(0, 0, 0, 0, thisGame);
		thisGame.create(a);
		assertTrue(thisGame.getCreateList().contains(a));
	}

	@Test
	public final void testOver1() {
		thisGame.over();
		assertEquals(0, thisGame.getDestroyList().size(), 0);
	}

	@Test
	public final void testOver2() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayerTwo(p2);
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.over();
		assertTrue(thisGame.getDestroyList().contains(p1));
	}

	@Test
	public final void testOver3() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		p2.setLives(0);
		thisGame.setPlayerTwo(p2);
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.over();
		assertEquals(2, thisGame.getDestroyList().size(), 0);
	}

	@Test
	public final void testOver4() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayerTwo(p2);
		thisGame.over();
		assertTrue(thisGame.getDestroyList().contains(p1));
	}

	@Test
	public final void testOver5() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		thisGame.getScoreCounter().setScore(10);
		thisGame.over();
		assertTrue(thisGame.getDestroyList().contains(p1));
		assertEquals(10, thisGame.getScoreCounter().getHighscore(), 0);
	}

	@Test
	public final void testAddScore1() {
		thisGame.addScore(10);
		assertEquals(10, thisGame.getScoreCounter().getScore(), 0);
	}

	@Test
	public final void testAddScore2() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayerTwo(p2);
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.addScore(10000);
		assertEquals(10000, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(4, p1.getLives(), 0);
		assertEquals(4, p2.getLives(), 0);
	}

	@Test
	public final void testAddScore3() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayer(p1);
		thisGame.addScore(10000);
		assertEquals(10000, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(4, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore4() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		p2.setLives(0);
		thisGame.setPlayerTwo(p2);
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.addScore(10000);
		assertEquals(0, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(0, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore5() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		thisGame.addScore(10000);
		assertEquals(0, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(0, p1.getLives(), 0);
	}

	@Test
	public final void testAddScore6() {
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		thisGame.setPlayerTwo(p2);
		gamestate.setMode(Gamestate.getModeCoop());
		thisGame.addScore(10000);
		assertEquals(10000, thisGame.getScoreCounter().getScore(), 0);
		assertEquals(1, p1.getLives(), 0);
		assertEquals(4, p2.getLives(), 0);
	}
	
	@Test
	public final void testBullets1(){
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0, thisGame);
		b.setPlayer(p1);
		addToEntities(b);
		assertEquals(1, thisGame.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets2(){
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		final Player p2 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0, thisGame);
		b.setPlayer(p2);
		addToEntities(b);
		assertEquals(0, thisGame.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets3(){
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Bullet b = new Bullet(0, 0, 0, 0, thisGame);
		b.setFriendly(false);
		addToEntities(b);
		assertEquals(0, thisGame.bullets(p1), 0);
	}
	
	@Test
	public final void testBullets4(){
		final Player p1 = new Player(0, 0, 0, 0, thisGame, false);
		p1.setLives(0);
		thisGame.setPlayer(p1);
		final Asteroid b = new Asteroid(0, 0, 0, 0, thisGame);
		addToEntities(b);
		assertEquals(0, thisGame.bullets(p1), 0);
	}
	
	@Test
	public final void testEnemies(){
		final Bullet b = new Bullet(0, 0, 0, 0, thisGame);
		final Saucer s = new Saucer(0, 0, 0, 0, thisGame);
		final Asteroid a = new Asteroid(0, 0, 0, 0, thisGame);
		addToEntities(b);
		addToEntities(s);
		addToEntities(a);
		assertEquals(2, thisGame.enemies(), 0);
	}
	
	@Test
	public final void testGetters(){
		assertTrue(thisGame.getRandom() != null);
	}
}
