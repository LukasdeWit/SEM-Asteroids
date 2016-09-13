package game;

import java.util.List;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public class Display {

	/**
	 * the GraphicsContext, needed to draw things
	 */
	private GraphicsContext gc;
	/**
	 * length of canvas in pixels.
	 */
	private float screenX;
	/**
	 * heigth of canvas in pixels.
	 */
	private float screenY;
	/**
	 * List of all entities currently on the screen.
	 */
	private List<Entity> entities;
	
	/**
	 * Size of canvas.
	 */
	private static final float CANVAS_SIZE = 500;
	
	
}
