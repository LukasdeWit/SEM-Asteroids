package game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;

/**
 * Class to regulate all audio output.
 * 
 * @author Esmee
 *
 */
public class Audio {
	/**
	 * Path for the location of the audiofiles.
	 */
	private static final String PATH = "src/main/resources/audiofiles/";
	/**
	 * Track number for shooting.
	 */
	public static final int SHOOTING = 0;
	/**
	 * Track number for a small asteroid exploding.
	 */
	public static final int SMALLEXPLOSION = 1;
	/**
	 * Track number for a medium asteroid exploding.
	 */
	public static final int MEDIUMEXPLOSION = 2;
	/**
	 * Track number for a large asteroid exploding.
	 */
	public static final int LARGEEXPLOSION = 3;
	/**
	 * Audioclip for the shooting noise.
	 */
	private AudioClip shooting;
	/**
	 * Audioclip for the small asteroid explosion.
	 */
	private AudioClip smallexplosion;
	/**
	 * Audioclip for the medium asteroid explosion.
	 */
	private AudioClip mediumexplosion;
	/**
	 * Audioclip for the large asteroid explosion.
	 */
	private AudioClip largeexplosion;
	/**
	 * Map with key and value to easily find tracks.
	 */
	private ArrayList<AudioClip> tracks;

	/**
	 * Constructor for audio class.
	 */
	public Audio() {
		try {
			shooting = new AudioClip(
					new File(PATH + "fire.mp3").toURI().toURL().toString());
			smallexplosion = new AudioClip(new File(
					PATH + "bangSmall.mp3").toURI().toURL().toString());
			mediumexplosion = new AudioClip(new File(
					PATH + "bangMedium.mp3").toURI().toURL().toString());
			largeexplosion = new AudioClip(new File(
					PATH + "bangLarge.mp3").toURI().toURL().toString());
		} catch (MalformedURLException e) {
		}
		tracks = new ArrayList<AudioClip>();
		tracks.add(shooting);
		tracks.add(smallexplosion);
		tracks.add(mediumexplosion);
		tracks.add(largeexplosion);
	}

	/**
	 * Get a track by title.
	 * 
	 * @param tracknumber
	 *            number of track to be played
	 * @return AudioClip with that title
	 */
	public final AudioClip get(final int tracknumber) {
		final AudioClip track = tracks.get(tracknumber);
		return track;
	}

	/**
	 * Get a track by title and play it.
	 * 
	 * @param tracknumber
	 *            number of track to be played
	 */
	public final void play(final int tracknumber) {
		get(tracknumber).play();
	}
}
