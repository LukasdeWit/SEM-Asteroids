package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import display.DisplayText;
import entity.AbstractEntity;
import entity.Asteroid;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class defines everything within the game.
 * 
 * @author Kibo
 *
 */
public final class Game {
	private Player player;
	private Player playerTwo;
	private final List<AbstractEntity> entities;
	private final Random random;
	private List<AbstractEntity> destroyList;
	private List<AbstractEntity> createList;
	private final float screenX;
	private final float screenY;
	private long score;
	private long highscore;
	
	private static final Game INSTANCE = new Game();
	private static final float CANVAS_SIZE = 500;
	private static final int LIFE_SCORE = 10000;

	/**
	 * Constructor for a new game.
	 */
	private Game() {
		Logger.getInstance().log("Game constructed.");
		screenX = CANVAS_SIZE;
		screenY = CANVAS_SIZE;
		entities = new ArrayList<>();
		destroyList = new ArrayList<>();
		createList = new ArrayList<>();
		random = new Random();
		highscore = readHighscore();
	}
	
	/**
	 * Singleton getinstance.
	 * @return the instance
	 */
	public static Game getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Starts or restarts the game, with initial entities.
	 */
	public void startGame() {
		Gamestate.getInstance().start();
		entities.clear();
		if (Gamestate.getInstance().isCoop()) {
			player = new Player(screenX / 2 - Player.getSpawnOffset(), screenY / 2, 0, 0, false);
			playerTwo = new Player(screenX / 2 + Player.getSpawnOffset(), screenY / 2, 0, 0, true);
			entities.add(player);
			entities.add(playerTwo);
		} else {
			player = new Player(screenX / 2, screenY / 2, 0, 0, false);
			entities.add(player);
		} 
		if (this.score > highscore) {
			highscore = this.score;
			writeHighscore();
		}
		score = 0;
		Spawner.getInstance().reset();
		Logger.getInstance().log("Game started.");
	}
	
	/**
	 * reads the highscore from file in resources folder.
	 * @return the highscore
	 */
	private long readHighscore() {
		long currentHighscore = 0;
		final String filePath = "src/main/resources/highscore.txt";
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath),
						StandardCharsets.UTF_8))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				currentHighscore = Long.parseLong(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getInstance().log("unable to read highscore from file");
		}
		return currentHighscore;
	}

	/**
	 * writes the highscore to file in resources folder.
	 */
	private void writeHighscore() {
		final String content = String.valueOf(highscore);
		final File file = new File("src/main/resources/highscore.txt");
		try (FileOutputStream fos =
					 new FileOutputStream(file.getAbsoluteFile())) {
			fos.write(content.getBytes(StandardCharsets.UTF_8));
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getInstance().log("unable to write highscore to file");
		}
	}
	
	/**
	 * update runs every game tick and updates all necessary entities.
	 * 
	 * @param input
	 *            - all keys pressed at the time of update
	 */
	public void update(final List<String> input) {
		Launcher.getRoot().getChildren().clear();
		final Rectangle r = new Rectangle(0, 0, screenX, screenY);
		r.setFill(Color.BLACK);
		Launcher.getRoot().getChildren().add(r);
		//root.setFill(Color.BLACK);
		//root.fillRect(0, 0, screenX, screenY);
		Gamestate.getInstance().update(input);
		DisplayText.wave(Spawner.getInstance().getWave());
	}	
	
	/**
	 * handles the update logic of the game itself.
	 * 
	 * @param input
	 * 			  - all keys pressed at the time of update
	 */
	public void updateGame(final List<String> input) {
		for (final AbstractEntity e : entities) {
			e.update(input);
			checkCollision(e);
			e.draw();
		}
		Spawner.getInstance().update();
		destroyList.forEach(AbstractEntity::onDeath);
		entities.removeAll(destroyList);
		entities.addAll(createList);
		createList.clear();
		destroyList.clear();
		createList.clear();
		DisplayText.score(score);
		DisplayText.highscore(highscore);
		if (Gamestate.getInstance().isCoop()) {
			DisplayText.livesTwo(playerTwo.getLives());
		}
		DisplayText.lives(player.getLives());
		
	}

	/**
	 * checks all collisions of an entity, if there is a hit then collide of the
	 * entity class will be run.
	 * 
	 * @param e1
	 *            - the entity
	 */
	public void checkCollision(final AbstractEntity e1) {
		entities
				.stream()
				.filter(e2 -> !e1.equals(e2)
						&& AbstractEntity.collision(e1, e2)
						&& !destroyList.contains(e1)
						&& !destroyList.contains(e2))
				.forEach(e1::collide);
	}
	
	/**
	 * adds an Entity to the destroy list and will be destroyed at the and of
	 * the current tick.
	 * 
	 * @param e
	 *            - the Entity
	 */
	public void destroy(final AbstractEntity e) {
		destroyList.add(e);
	}

	/**
	 * adds an Entity to the createList, and will be added to the game at the
	 * and of the current tick.
	 * 
	 * @param e
	 *            - the Entity
	 */
	public void create(final AbstractEntity e) {
		createList.add(e);
	}

	/**
	 * Game over function, destroys the player.
	 */
	public void over() {
		if (player == null) {
			return;
		}
		if (player.isAlive()) {
			destroy(playerTwo);
			return;
		} else if (Gamestate.getInstance().isCoop() && playerTwo.isAlive()) {
			destroy(player);
			return;
		}
		destroy(player);
		if (Gamestate.getInstance().isCoop()) {
			destroy(playerTwo);
		}
		Logger.getInstance().log("Game over.");
		if (score <= highscore) {
			Gamestate.getInstance().setState(Gamestate.getStateStartScreen());
		} else {
			highscore = score;
			writeHighscore();
			Logger.getInstance().log("New highscore is " + highscore + ".");
			Gamestate.getInstance().setState(Gamestate.getStateHighscoreScreen());
		}
	}

	/**
	 * Adds score to this.score.
	 * @param score - the score to be added.
	 */
	public void addScore(final int score) {
		if (player == null) {
			return;
		}
		if (player.isAlive() || Gamestate.getInstance().isCoop() && playerTwo.isAlive()) {
			Logger.getInstance().log("Player gained " + score + " points.");
			if (this.score % LIFE_SCORE + score >= LIFE_SCORE) {
				player.gainLife();
				if (Gamestate.getInstance().isCoop()) {
					playerTwo.gainLife();
				}
				Logger.getInstance().log("Player gained an extra life.");
			}
			this.score += score;
		}
	}
	
	/**
	 * Amount of bullets currently in game.
	 * @param player 
	 * @return amount of bullets
	 */
	public int bullets(final Player player) {
		int bullets = 0;
		for (final AbstractEntity entity : entities) {
			if (entity instanceof Bullet && ((Bullet) entity).isFriendly() 
					&& ((Bullet) entity).getPlayer().equals(player)) {
				bullets++;
			}
		}
		return bullets;
	}

	/**
	 * Amount of enemies currently in game.
	 * @return amount of enemies
	 */
	public int enemies() {
		int enemies = 0;
		for (final AbstractEntity entity : entities) {
			if (entity instanceof Asteroid || entity instanceof Saucer) {
				enemies++;
			}
		}
		return enemies;
	}

	/**
	 * CanvasSize getter.
	 * @return canvas size
	 */
	public static float getCanvasSize() {
		return CANVAS_SIZE;
	}

	/**
	 * getter for screenX.
	 * @return - screenX
	 */
	public float getScreenX() {
		return screenX;
	}

	/**
	 * getter for screenY.
	 * @return - screenY
	 */
	public float getScreenY() {
		return screenY;
	}

	/**
	 * Score getter.
	 * @return score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(final long score) {
		this.score = score;
	}

	/**
	 * Player getter.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * @return the playerTwo
	 */
	public Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * random getter.
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * @return the highscore
	 */
	public long getHighscore() {
		return highscore;
	}

	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(final long highscore) {
		this.highscore = highscore;
	}

	/**
	 * @return the destroyList
	 */
	public List<AbstractEntity> getDestroyList() {
		return destroyList;
	}

	/**
	 * @param destroyList the destroyList to set
	 */
	public void setDestroyList(final List<AbstractEntity> destroyList) {
		this.destroyList = destroyList;
	}

	/**
	 * @return the createList
	 */
	public List<AbstractEntity> getCreateList() {
		return createList;
	}

	/**
	 * @param createList the createList to set
	 */
	public void setCreateList(final List<AbstractEntity> createList) {
		this.createList = createList;
	}
}