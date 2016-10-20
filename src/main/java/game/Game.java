package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import display.DisplayHud;
import display.DisplayText;
import entity.AbstractEntity;
import entity.Asteroid;
import entity.Boss;
import entity.Bullet;
import entity.Player;
import entity.Saucer;
import entity.builders.PlayerBuilder;
import game.highscore.HighscoreStore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class defines everything within the game.
 *
 * @author Kibo
 */
public final class Game {
	private Player player;
	private Player playerTwo;
	private List<AbstractEntity> entities;
	private List<AbstractEntity> destroyList;
	private List<AbstractEntity> createList;
	private final ScoreCounter scorecounter;
	private final float screenX;
	private final float screenY;
	private final Spawner spawner;
	private final Gamestate gamestate;
	private final Audio audio;
	private static final float CANVAS_SIZE = 500;
	private static final long SURVIVAL_ASTEROID_SIZE_BIG = 4;
	private static final boolean LOG_SCORE = false;

	/**
	 * Constructor for a new game.
	 */
	public Game() {
		Logger.getInstance().log("Game constructed.");
		screenX = CANVAS_SIZE;
		screenY = CANVAS_SIZE;
		entities = new ArrayList<>();
		spawner = new Spawner(this);
		destroyList = new ArrayList<>();
		createList = new ArrayList<>();
		gamestate = new Gamestate(this);
		scorecounter = new ScoreCounter(this, new HighscoreStore());
		audio = new Audio();
	}

	/**
	 * Starts or restarts the game, with initial entities.
	 */
	public void startGame() {
		gamestate.start();
		entities.clear();
		final PlayerBuilder pBuilder = new PlayerBuilder();
		if (gamestate.isCoop()) {
			// Create player 1
			pBuilder.setX(screenX / 2 - Player.getSpawnOffset());
			pBuilder.setY(screenY / 2);
			pBuilder.setDX(0);
			pBuilder.setDY(0);
			pBuilder.setThisGame(this);
			pBuilder.setPlayerTwo(false);
			player = (Player) pBuilder.getResult();
			// Change the relevant player two statistics
			pBuilder.setPlayerTwo(true);
			pBuilder.setX(screenX / 2 + Player.getSpawnOffset());
			playerTwo = (Player) pBuilder.getResult();
			
			entities.add(player);
			entities.add(playerTwo);
		} else {
			pBuilder.setX(screenX / 2);
			pBuilder.setY(screenY / 2);
			pBuilder.setDX(0);
			pBuilder.setDY(0);
			pBuilder.setThisGame(this);
			player = (Player) pBuilder.getResult();
			entities.add(player);
		}
		spawner.reset();
		Logger.getInstance().log(gamestate.toString() + " game started.");
	}

	/**
	 * update runs every game tick and updates all necessary entities.
	 *
	 * @param input - all keys pressed at the time of update
	 */
	public void update(final List<String> input) {
		Launcher.getRoot().getChildren().clear();
		final Rectangle r = new Rectangle(0, 0, screenX, screenY);
		r.setFill(Color.BLACK);
		Launcher.getRoot().getChildren().add(r);
		gamestate.update(input);
		DisplayText.wave(spawner.getWave());
	}

	/**
	 * handles the update logic of the game itself.
	 *
	 * @param input - all keys pressed at the time of update
	 */
	public void updateGame(final List<String> input) {
		entities.forEach(e -> {
			e.update(input);
			checkCollision(e);
			e.draw();
		});
		
		if (gamestate.isArcade()) {
			spawner.updateArcade();
		} else if (gamestate.isBoss()) {
			spawner.updateBoss();
		} else {
			spawner.updateSurvival();
		}
		
		destroyList.forEach(AbstractEntity::onDeath);
		entities.removeAll(destroyList);
		entities.addAll(createList);
		createList.clear();
		destroyList.clear();
		createList.clear();
		audio.backgroundTrack(enemies());
		scorecounter.displayScore();
		if (gamestate.isCoop()) {
			if (playerTwo == null) {
				return;
			}
			DisplayHud.lives(playerTwo.getLives(), playerTwo.isPlayerTwo());
		}
		if (player == null) {
			return;
		}
		DisplayHud.lives(player.getLives(), player.isPlayerTwo());
		
	}

	/**
	 * checks all collisions of an entity, if there is a hit then collide of the
	 * entity class will be run.
	 *
	 * @param e1 - the entity
	 */
	public void checkCollision(final AbstractEntity e1) {
		entities.stream()
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
	 * @param e - the Entity
	 */
	public void destroy(final AbstractEntity e) {
		destroyList.add(e);
	}

	/**
	 * adds an Entity to the createList, and will be added to the game at the
	 * and of the current tick.
	 *
	 * @param e - the Entity
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
			Logger.getInstance().log("Player 2 died.");
			destroy(playerTwo);
			return;
		} else if (gamestate.isCoop() && playerTwo.isAlive()) {
			Logger.getInstance().log("Player 1 died.");
			destroy(player);
			return;
		}
		Logger.getInstance().log("Game over.");
		overSwitch();
	}

	/**
	 * Switches the gamemode when game is over.
	 */
	public void overSwitch() {
		destroy(player);
		if (gamestate.isCoop()) {
			destroy(playerTwo);
		}
		if (scorecounter.isNotHighscore()) {
			scorecounter.setScore(0);
			gamestate.setMode(Gamestate.getModeNone());
			gamestate.setState(Gamestate.getStateStartScreen());
		} else {
			Logger.getInstance().log("New highscore is " + scorecounter.getHighscore() + ".");
			gamestate.setState(Gamestate.getStateHighscoreScreen());
		}
		audio.stopAll();
	}

	/**
	 * Adds score to this.score.
	 *
	 * @param score - the score to be added.
	 */
	public void addScore(final int score) {
		if (player == null) {
			scorecounter.addScore(score);
			return;
		}
		if (player.isAlive() || gamestate.isCoop() && playerTwo.isAlive()) {
			if (LOG_SCORE) {
				Logger.getInstance().log(score + " points gained.");
			}
			extraLife(score);
			scorecounter.addScore(score);
		}
	}

	/**
	 * handles the gaining of extra lives.
	 * @param score - the score that will be added
	 */
	private void extraLife(final int score) {
		if (scorecounter.canGainLife(score)) {
			player.gainLife();
			Logger.getInstance().log(player.getPlayerString() + " gained an extra life.");
			if (gamestate.isCoop()) {
				playerTwo.gainLife();
				Logger.getInstance().log("Player 2 gained an extra life.");
			}
		}
	}

	/**
	 * Amount of bullets currently in game for a specific player.
	 *
	 * @param player the player whose bullets to count
	 * @return amount of bullets
	 */
	public int bullets(final Player player) {
		return Math.toIntExact(entities.stream()
				.filter(e -> e instanceof Bullet)
				.map(e -> (Bullet) e)
				.filter(Bullet::isFriendly)
				.filter(bullet -> bullet.getShooter().equals(player))
				.count());
	}

	/**
	 * Amount of enemies currently in game.
	 *
	 * @return amount of enemies
	 */
	public int enemies() {
		return Math.toIntExact(entities.stream()
				.filter(e -> e instanceof Asteroid || e instanceof Saucer || e instanceof Boss)
				.count());
	}
	
	/**
	 * Amount of big enemies, where 2 medium asteroids count as 1 big
	 * big asteroid, and 2 small asteroids count as 1 medium asteroid.
	 * @return amount of converted big enemies
	 */
	public int convertedBigEnemies() {
		int enemies = 0;
		for (final AbstractEntity entity : entities) {
			if (entity instanceof Asteroid) {
				enemies += ((Asteroid) entity).getSurvivalSize();
			}
		}
		if (enemies % SURVIVAL_ASTEROID_SIZE_BIG == 0) {
			return (int) (enemies / SURVIVAL_ASTEROID_SIZE_BIG);
		}
		return (int) (enemies / SURVIVAL_ASTEROID_SIZE_BIG) + 1;
	}

	/**
	 * CanvasSize getter.
	 *
	 * @return canvas size
	 */
	public static float getCanvasSize() {
		return CANVAS_SIZE;
	}

	/**
	 * getter for screenX.
	 *
	 * @return - screenX
	 */
	public float getScreenX() {
		return screenX;
	}

	/**
	 * getter for screenY.
	 *
	 * @return - screenY
	 */
	public float getScreenY() {
		return screenY;
	}

	/**
	 * Player getter.
	 *
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
	public Optional<Player> getPlayerTwo() {
		return Optional.of(playerTwo);
	}

	/**
	 * @param playerTwo - a new player two.
	 */
	public void setPlayerTwo(final Player playerTwo) {
		this.playerTwo = playerTwo;
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

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(final List<AbstractEntity> entities) {
		this.entities = entities;
	}

	/**
	 * @return the entities
	 */
	public List<AbstractEntity> getEntities() {
		return entities;
	}	
	
	/**
	 * @return the gamestate
	 */
	public Gamestate getGamestate() {
		return gamestate;
	}

	/**
	 * @return the spawner
	 */
	public Spawner getSpawner() {
		return spawner;
	}
	
	/**
	 * @return the audio
	 */
	public Audio getAudio() {
		return audio;
	}
		
	/**
	 * @return the scorecounter
	 */
	public ScoreCounter getScoreCounter() {
		return scorecounter;
	}
}