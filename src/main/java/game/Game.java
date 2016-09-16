package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * This class defines everything within the game.
 * 
 * @author Kibo
 *
 */
public class Game {
	/**
	 * The player of this game.
	 */
	private Player player;
	/**
	 * The spawner of this game.
	 */
	private Spawner spawner;
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
	 * heigth of canvas in pixels.
	 */
	private final float screenY;
	/**
	 * the GraphicsContext, needed to draw things.
	 */
	private final GraphicsContext gc;
	/**
	 * the start time of the current game.
	 */
	private long restartTime;
	/**
	 * current score.
	 */
	private long score;
	/**
	 * current highscore.
	 */
	private long highscore;
	/**
	 * current gamemode
	 */
	private int gamemode;
	
	/**
	 * Size of canvas.
	 */
	private static final float CANVAS_SIZE = 500;
	/**
	 * Minimal restart time.
	 */
	private static final long MINIMAL_RESTART_TIME = 300;
	/**
	 * Number of points needed to gain a life.
	 */
	private static final int LIFE_SCORE = 10000;
	/**
	 * the startscreen gamemode
	 */
	private static final int GAMEMODE_STARTSCREEN = 0;
	/**
	 * the "game" gamemode
	 */
	private static final int GAMEMODE_GAME = 1;
	/**
	 * the highscore screen
	 */
	private static final int GAMEMODE_HIGHSCORESCREEN = 2;

	/**
	 * Constructor for a new game.
	 * 
	 * @param gc
	 *            - the GraphicsContext of the canvas
	 */
	public Game(final GraphicsContext gc) {
		this.gc = gc;
		screenX = CANVAS_SIZE;
		screenY = CANVAS_SIZE;
		spawner = new Spawner(this);
		entities = new ArrayList<>();
		destroyList = new ArrayList<>();
		createList = new ArrayList<>();
		random = new Random();
		highscore = readHighscore();
		startGame();
	}
	
	/**
	 * reades the highscore from file in resources folder.
	 * @return the highscore
	 */
	private long readHighscore() {
		BufferedReader br = null;
		long currentHighscore = 0;
		try {
			String sCurrentLine;
			br = new BufferedReader(
					new FileReader("src/main/resources/highscore.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				currentHighscore = Long.parseLong(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return currentHighscore;
	}

	/**
	 * writes the highscore to file in resources folder.
	 */
	private void writeHighscore() {
		try {
			String content = String.valueOf(highscore);
			File file = new File("src/main/resources/highscore.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts or restarts the game, with initial entities.
	 */
	public final void startGame() {
		restartTime = System.currentTimeMillis();
		entities.clear();
		player = new Player(screenX / 2, screenY / 2, 0, 0, this);
		entities.add(player);
		if (this.score > highscore) {
			highscore = this.score;
			writeHighscore();
		}
		score = 0;
		gamemode = GAMEMODE_STARTSCREEN;
		spawner.reset();
	}
	
	/**
	 * update runs every game tick and updates all necessary entities.
	 * 
	 * @param input
	 *            - all keys pressed at the time of update
	 */
	public final void update(final List<String> input) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenX, screenY);
		
		switch(gamemode) {
		case GAMEMODE_STARTSCREEN:
			updateStartscreen(input);
			break;
		case GAMEMODE_GAME:
			updateGame(input);
			break;
		case GAMEMODE_HIGHSCORESCREEN:
			updateHighscorescreen(input);
			break;
		default:
			gamemode = GAMEMODE_STARTSCREEN;
		}
	}
	
	/**
	 * handles the update logic of the start screen
	 * 
	 * @param input
	 * 			  - all keys pressed at the time of update
	 */
	private final void updateStartscreen(final List<String> input) {
		if (input.contains("SPACE")) {
			gamemode = GAMEMODE_GAME;
		}
		Display.startscreen(gc);
	}
	
	/**
	 * handles the update logic of the game itself
	 * 
	 * @param input
	 * 			  - all keys pressed at the time of update
	 */
	private final void updateGame(final List<String> input) {
		if (input.contains("R") && System.currentTimeMillis() 
				- restartTime > MINIMAL_RESTART_TIME) {
			startGame();
		}
		for (final AbstractEntity e : entities) {
			e.update(input);
			checkCollision(e);
			e.draw(gc);
		}
		spawner.update();
		destroyList.forEach(AbstractEntity::onDeath);
		entities.removeAll(destroyList);
		entities.addAll(createList);
		createList.clear();
		destroyList.clear();
		createList.clear();
		if (!player.isAlive()) {
			if(score <= highscore) {
				startGame();
			} else {
				gamemode = GAMEMODE_HIGHSCORESCREEN;
			}
		}
		Display.score(score, gc);
		Display.highscore(highscore, gc);
		Display.lives(player.getLives(), gc);
	}
	
	private final void updateHighscorescreen(final List<String> input) {
		if (input.contains("R")) {
			startGame();
		}
		Display.highscorescreen(highscore, gc);
	}

	/**
	 * checks all collisions of an entity, if there is a hit then collide of the
	 * entity class will be run.
	 * 
	 * @param e1
	 *            - the entity
	 */
	public final void checkCollision(final AbstractEntity e1) {
		for (final AbstractEntity e2 : entities) {
			if (!e1.equals(e2) && AbstractEntity.collision(e1, e2)
					&& !destroyList.contains(e1) && !destroyList.contains(e2)) {
				e1.collide(e2);
			}
		}
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
	 * Game over function, destroys the player.
	 */
	public final void over() {
		destroy(player);
	}

	/**
	 * Adds score to this.score.
	 * @param score - the score to be added.
	 */
	public final void addScore(final int score) {
		if (player.isAlive()) {
			if (this.score % LIFE_SCORE + score >= LIFE_SCORE) {
				player.gainLife();
			}
			this.score += score;
		}
	}
	
	/**
	 * CanvasSize getter.
	 * @return canvas size
	 */
	public static float getCanvasSize() {
		return CANVAS_SIZE;
	}

	/**
	 * Amount of bullets currently in game.
	 * @return amount of bullets
	 */
	public final int bullets() {
		int bullets = 0;
		for (AbstractEntity e : entities) {
			if (e instanceof Bullet && ((Bullet) e).isFriendly()) {
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
		for (AbstractEntity e : entities) {
			if (e instanceof Asteroid || e instanceof Saucer) {
				enemies++;
			}
		}
		return enemies;
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
	 * @return the random
	 */
	public final Random getRandom() {
		return random;
	}
}