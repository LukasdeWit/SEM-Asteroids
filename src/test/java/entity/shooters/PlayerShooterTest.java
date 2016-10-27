package entity.shooters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entity.Player;
import entity.builders.BulletBuilder;
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
	private BulletBuilder bBuilder;
	private PlayerBuilder pBuilder;

	@Before
	public final void setUp() {
		thisGame = new Game();
		thisGame.setCreateList(new ArrayList<>());
		thisGame.setDestroyList(new ArrayList<>());
		thisGame.getGamestate().setMode(thisGame.getGamestate().getArcadeMode());
		Launcher.getRoot().getChildren().clear();
		
		pBuilder = new PlayerBuilder();
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
				
		bBuilder = new BulletBuilder();
		bBuilder.setX(X_START);
		bBuilder.setY(Y_START);
		bBuilder.setDX(DX_START);
		bBuilder.setDY(DY_START);
		bBuilder.setThisGame(thisGame);
	}
	@Test
	public void testKeyHandler9() {
		final String[] input = {SPACE};
		update(player, input, false);
		assertEquals(System.currentTimeMillis(), player.getShooter().getLastShot(), 1);
	}
	@Test
	public void testKeyHandlerTwo9() {
		final String[] input = {SPACE};
		update(player, input, true);
		assertEquals(System.currentTimeMillis(), player.getShooter().getLastShot(), 1);
	}

	@Test
	public void testKeyHandlerTwo10() {
		final String[] input = {"ENTER"};
		update(player2, input, true);

		assertEquals(System.currentTimeMillis(), player2.getShooter().getLastShot(), 1);
	}
	
	@Test
	public void testFire() {
		final String[] input = {SPACE};
		player.getShooter().setTripleShot(true);
		update(player, input, false);
		assertEquals(3, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testFire2() {
		final String[] input = {SPACE};
		update(player, input, false);
		update(player, input, false);
		assertEquals(1, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testFire3() {
		final String[] input = {SPACE};
		player.getShooter().setMaxBullets(0);
		update(player, input, false);
		assertEquals(0, thisGame.getCreateList().size(), 0);
	}
	
	@Test
	public void testGettersAndSetters(){
		PlayerShooter ps = player.getShooter();
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
