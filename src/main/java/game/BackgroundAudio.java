package game;

import javafx.scene.media.AudioClip;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Class that regulates the background track.
 * 
 * @author Esmee
 *
 */
@Getter
public class BackgroundAudio {
	/**
	 * Regulates which boop sound is used for background track.
	 */
	private boolean bg;
	/**
	 * Last time a boop was played.
	 */
	@Setter
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
	@Getter
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
	 * @param enemies new amount of enemies.
	 * @param mute whether the background track should be muted.
	 */
	public final void update(final int enemies, final boolean mute) {
    	if (System.currentTimeMillis() <= time + interval) {
    	    return;
    	}

    	// alternate between booping sounds
    	if (bg && !mute) {
    	    boop1.play();
    	} else if (!mute) {
    	    boop2.play();
    	}

    	// make sure to avoid divide by 0
    	// music slows down again when there are no more enemies left
    	if (enemies < 1) {
    	    interval = BASELINE;
    	} else if (BASELINE / enemies < interval) {
    	    interval = BASELINE / enemies;
    	}

    	// update time to current
    	time = System.currentTimeMillis();
    	// flip background sound so it alternates each time
    	bg = !bg;
    }
}
