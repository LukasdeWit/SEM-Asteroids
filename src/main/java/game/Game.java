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

import entity.AbstractEntity;
import entity.Asteroid;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class defines everything within the game.
 * 
 * @author Kibo
 *
 */
public class Game {
	private final Group root;
	private final Gamestate gamestate;
	/**
	 * The player of this game.
	 */
	private Player player;
	private Player playerTwo;
	/**
	 * The spawner of this game.
	 */
	private final Spawner spawner;
	/**
	 * List of all entities currently in the game.
	 */
	private final List<AbstractEntity> entities;
	/**
	 * Object of random used to get random numbers.
	 */
	private final Random random;
	/**
	 * List of all entities to be destroyed at the and of the tick.
	 */
	private final List<AbstractEntity> destroyList;
	/**
	 * List of all entities to be created at the and of the tick.
	 */
	private final List<AbstractEntity> createList;
	/**
	 * length of canvas in pixels.
	 */
	private final float screenX;
	/**
	 * height of canvas in pixels.
	 */
	private final float screenY;
	/**
	 * current score.
	 */
	private long score;
	/**
	 * current highscore.
	 */
	private long highscore;
	private static Game instance;
	
	/**
	 * Size of canvas.
	 */
	private static final float CANVAS_SIZE = 500;
	/**
	 * Number of points needed to gain a life.
	 */
	private static final int LIFE_SCORE = 10000;

	/**
	 * Constructor for a new game.
	 * @param root - the root of the group of the canvas
	 */
	private Game(final Group root) {
		this.root = root;
		Logger.getInstance().log("Game constructed.");
		screenX = CANVAS_SIZE;
		screenY = CANVAS_SIZE;
		gamestate = new Gamestate(this);
		spawner = new Spawner(this);
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
		if (instance == null) {
			instance = new Game(Launcher.getRoot());
		}
		return instance;
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
	 * Starts or restarts the game, with initial entities.
	 */
	public final void startGame() {
		gamestate.start();
		entities.clear();
		if (gamestate.isCoop()) {
			player = new Player(screenX / 2 - Player.getSpawnOffset(), screenY / 2, 0, 0, this, false);
			playerTwo = new Player(screenX / 2 + Player.getSpawnOffset(), screenY / 2, 0, 0, this, true);
			entities.add(player);
			entities.add(playerTwo);
		} else {
			player = new Player(screenX / 2, screenY / 2, 0, 0, this, false);
			entities.add(player);
		} 
		if (this.score > highscore) {
			highscore = this.score;
			writeHighscore();
		}
		score = 0;
		spawner.reset();
		Logger.getInstance().log("Game started.");
	}
	
	/**
	 * update runs every game tick and updates all necessary entities.
	 * 
	 * @param input
	 *            - all keys pressed at the time of update
	 */
	public final void update(final List<String> input) {
		root.getChildren().clear();
		Rectangle r = new Rectangle(0, 0, screenX, screenY);
		r.setFill(Color.BLACK);
		root.getChildren().add(r);
		//root.setFill(Color.BLACK);
		//root.fillRect(0, 0, screenX, screenY);
		gamestate.update(input);
		Display.wave(spawner.getWave(), root);
	}	
	
	/**
	 * handles the update logic of the game itself.
	 * 
	 * @param input
	 * 			  - all keys pressed at the time of update
	 */
	public final void updateGame(final List<String> input) {
		for (final AbstractEntity e : entities) {
			e.update(input);
			checkCollision(e);
			e.draw(root);
		}
		spawner.update();
		destroyList.forEach(AbstractEntity::onDeath);
		entities.removeAll(destroyList);
		entities.addAll(createList);
		createList.clear();
		destroyList.clear();
		createList.clear();
		Display.score(score, root);
		Display.highscore(highscore, root);
		if (gamestate.isCoop()) {
			Display.livesTwo(playerTwo.getLives(), root);
		}
		Display.lives(player.getLives(), root);
		
	}

	/**
	 * checks all collisions of an entity, if there is a hit then collide of the
	 * entity class will be run.
	 * 
	 * @param e1
	 *            - the entity
	 */
	public final void checkCollision(final AbstractEntity e1) {
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
	public final void destroy(final AbstractEntity e) {
		destroyList.add(e);
	}

	/**
	 * adds an Entity to the createList, and will be added to the game at the
	 * and of the current tick.
	 * 
	 * @param e
	 *            - the Entity
	 */
	public final void create(final AbstractEntity e) {
		createList.add(e);
	}

	/**
	 * Game over function, destroys the player.
	 */
	public final void over() {
		if (player.isAlive()) {
			destroy(playerTwo);
			return;
		} else if (gamestate.isCoop() && playerTwo.isAlive()) {
			destroy(player);
			return;
		}
		destroy(player);
		if (gamestate.isCoop()) {
			destroy(playerTwo);
		}
		Logger.getInstance().log("Game over.");
		if (score <= highscore) {
			gamestate.setState(Gamestate.getStateStartScreen());
		} else {
			highscore = score;
			writeHighscore();
			Logger.getInstance().log("New highscore is " + highscore + ".");
			gamestate.setState(Gamestate.getStateHighscoreScreen());
		}
	}

	/**
	 * Adds score to this.score.
	 * @param score - the score to be added.
	 */
	public final void addScore(final int score) {
		if (player.isAlive() || (gamestate.isCoop() && playerTwo.isAlive())) {
			Logger.getInstance().log("Player gained " + score + " points.");
			if (this.score % LIFE_SCORE + score >= LIFE_SCORE) {
				player.gainLife();
				if (gamestate.isCoop()) {
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
	public final int bullets(final Player player) {
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
	public final int enemies() {
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
	public final float getScreenX() {
		return screenX;
	}

	/**
	 * getter for screenY.
	 * @return - screenY
	 */
	public final float getScreenY() {
		return screenY;
	}

	/**
	 * Score getter.
	 * @return score
	 */
	public final long getScore() {
		return score;
	}

	/**
	 * Player getter.
	 * @return the player
	 */
	public final Player getPlayer() {
		return player;
	}

	/**
	 * @return the playerTwo
	 */
	public final Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * random getter.
	 * @return the random
	 */
	public final Random getRandom() {
		return random;
	}

	/**
	 * @return the highscore
	 */
	public final long getHighscore() {
		return highscore;
	}

	/**
	 * @return the gamestate
	 */
	public final Gamestate getGamestate() {
		return gamestate;
	}

	/**
	 * @return the root
	 */
	public final Group getRoot() {
		return root;
	}
}