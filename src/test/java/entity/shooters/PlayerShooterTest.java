package entity.shooters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.Player;
import entity.builders.PlayerBuilder;
import game.Game;
import game.Launcher;

public class PlayerShooterTest {
	private static final float X_START = 1;
	private static final float Y_START = 2;
	private static final float DX_START = 3;
	private static final float DY_START = 4;
	private static final String SPACE = "SPACE";
	private Player player;
	private Player player2;
	private Game thisGame;
	

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.getGamestate().setMode(thisGame.getGamestate().getArcadeMode());
		Launcher.getRoot().getChildren().clear();
		final PlayerBuilder pBuilder = new PlayerBuilder();
		pBuilder.setX(X_START);
		pBuilder.setY(Y_START);
		pBuilder.setDX(DX_START);
		pBuilder.setDY(DY_START);
		pBuilder.setThisGame(thisGame);
		pBuilder.setPlayerTwo(false);
		player = (Player) pBuilder.getResult();
		pBuilder.setDX(DX_START + 2);
		pBuilder.setDY(DY_START + 2);
		pBuilder.setPlayerTwo(true);
		player2 = (Player) pBuilder.getResult();		
		thisGame.getAudio().setMute(true);
	}
	@Test
	public void testKeyHandlerShoot() {
		final String[] input = {SPACE};
		update(player, input, false);
		assertEquals(System.currentTimeMillis(), player.getShooter().getLastShot(), 1);
	}
	@Test
	public void testKeyHandlerShoot2() {
		final String[] input = {SPACE};
		update(player, input, true);
		assertEquals(System.currentTimeMillis(), player.getShooter().getLastShot(), 1);
	}

	@Test
	public void testKeyHandlerPlayer2Shoot() {
		final String[] input = {"ENTER"};
		update(player2, input, true);

		assertEquals(System.currentTimeMillis(), player2.getShooter().getLastShot(), 1);
	}
	
	@Test
	public void testFire() {
		player.setThisGame(thisGame);
		player.getShooter().setTripleShot(true);
		player.getShooter().setLastShot(0);
		player.getShooter().shoot();

		assertEquals(3, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testFire2() {
		player.setThisGame(thisGame);
		player.getShooter().setLastShot(0);
		player.getShooter().shoot();
		assertEquals(1, thisGame.getCreateList().size(), 0);
	}
	
	
	@Test
	public void testFire3() {
		player.getShooter().setMaxBullets(0);
		player.getShooter().setLastShot(0);
		player.getShooter().shoot();
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testP2Fire1() {
		player2.setThisGame(thisGame);
		player2.getShooter().setTripleShot(true);
		player2.getShooter().setLastShot(0);
		player2.getShooter().shoot();

		assertEquals(3, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testP2Fire2() {
		player2.setThisGame(thisGame);
		player2.getShooter().setLastShot(0);
		player2.getShooter().shoot();
		assertEquals(1, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testP2Fire3() {
		player2.getShooter().setMaxBullets(0);
		player2.getShooter().setLastShot(0);
		player2.getShooter().shoot();
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testGettersAndSetters(){
		final PlayerShooter ps = player.getShooter();
		ps.setBulletSize(PlayerShooter.getBulletSize());
		ps.setPiercing(PlayerShooter.getMaxBullets());
		ps.setFireRate(PlayerShooter.getFireRate());
		player.gainShield();
		assertEquals(1, player.getShielding(), 0);
	}
	
	private void update(final Player player, final String[] in, final boolean coop){
		final List<String> input = new ArrayList<>();
		Collections.addAll(input, in);
		if (coop) {
			thisGame.getGamestate().setMode(thisGame.getGamestate().getCoopArcadeMode());
		}
		player.setInvincibleStart(0);
		player.update(input);
	}
}
