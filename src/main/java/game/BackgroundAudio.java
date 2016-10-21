package game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.AudioClip;

/**
 * Class that regulates the background track.
 * 
 * @author Esmee
 *
 */
public class BackgroundAudio {
	/**
	 * Regulates which boop sound is used for background track.
	 */
	private boolean bg;
	/**
	 * Last time a boop was played.
	 */
	private long time;
	/**
	 * Interval between boops.
	 */
	private long interval;
	/**
	 * First boop to be played.
	 */
	private AudioClip boop1;
	/**
	 * Second boop to be played.
	 */
	private AudioClip boop2;
	/**
	 * Length between boops with no entities on board.
	 */
	private static final long BASELINE = 7000;

	/**
	 * Constructor for backgroundaudio.
	 * 
	 * @param audiopath
	 *            path where the audio is located
	 */
	public BackgroundAudio(final String audiopath) {
		bg = true;
		time = System.currentTimeMillis();
		interval = BASELINE;

		try {
			boop1 = new AudioClip(new File(audiopath 
					+ "beat1.mp3").toURI().toURL().toString());
			boop2 = new AudioClip(new File(audiopath
					+ "beat2.mp3").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			Logger.getInstance().log("failed to initialize background audio");
		}
	}

	/**
	 * Play the background track.
	 * 
	 * @param enemies
	 *            new amount of enemies.
	 */
	public final void update(final int enemies) {
		if (System.currentTimeMillis() > time + interval) {
			// alternate between booping sounds
			if (bg) {
				boop1.play();
				bg = false;
			} else {
				boop2.play();
				bg = true;
			}
			// make sure to avoid divide by 0
			// music slows down again when there are no more enemies left
			if (enemies > 1) {
				if (BASELINE / enemies < interval) {
				interval = BASELINE / enemies;
				}
			} else {
				interval = BASELINE;
			}
			// update time to current
			time = System.currentTimeMillis();
		}
	}
}
