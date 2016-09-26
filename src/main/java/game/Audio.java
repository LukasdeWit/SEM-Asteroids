package game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
	protected final String PATH = "src/main/resources/audiofiles/";
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
	 * Track number for gaining a life.
	 */
	public static final int LIFEUP = 4;
	/**
	 * Track number for small ufo.
	 */
	public static final int UFOSMALL = 5;
	/**
	 * Track number for big ufo.
	 */
	public static final int UFOBIG = 6;
	/**
	 * Track number for rocket boost.
	 */
	public static final int BOOST = 7;
	/**
	 * Map with key and value to easily find tracks.
	 */
	private List<AudioClip> tracks;
	/**
	 * Class that regulates the background track.
	 */
	private BackgroundAudio backgroundtrack;

	

	/**
	 * Constructor for audio class.
	 */
	public Audio() {
		tracks = new ArrayList<AudioClip>();
		backgroundtrack = new BackgroundAudio(this);
		
		try {
			final AudioClip shooting = new AudioClip(new File(
					PATH + "fire.mp3").toURI().toURL().toString());
			final AudioClip smallexplosion = new AudioClip(new File(
					PATH + "bangSmall.mp3").toURI().toURL().toString());
			final AudioClip mediumexplosion = new AudioClip(new File(
					PATH + "bangMedium.mp3").toURI().toURL().toString());
			final AudioClip largeexplosion = new AudioClip(new File(
					PATH + "bangLarge.mp3").toURI().toURL().toString());
			final AudioClip lifeup = new AudioClip(new File(
					PATH + "extraShip.mp3").toURI().toURL().toString());
			final AudioClip ufosmall = new AudioClip(new File(
					PATH + "ufoSmall.mp3").toURI().toURL().toString());
			final AudioClip ufobig = new AudioClip(new File(
					PATH + "ufoBig.mp3").toURI().toURL().toString());
			final AudioClip boost = new AudioClip(new File(
					PATH + "thrust.mp3").toURI().toURL().toString());

			
			ufosmall.setCycleCount(AudioClip.INDEFINITE);
			ufobig.setCycleCount(AudioClip.INDEFINITE);
			boost.setCycleCount(AudioClip.INDEFINITE);
			
			tracks.add(shooting);
			tracks.add(smallexplosion);
			tracks.add(mediumexplosion);
			tracks.add(largeexplosion);
			tracks.add(lifeup);
			tracks.add(ufosmall);
			tracks.add(ufobig);
			tracks.add(boost);
		} catch (MalformedURLException e) {
			Logger.getInstance().log("failed to initialize audio");
		}
	}

	/**
	 * Get a track by title.
	 * 
	 * @param tracknumber
	 *            number of track to be played
	 * @return AudioClip with that title
	 */
	public final AudioClip get(final int tracknumber) {
		return tracks.get(tracknumber);
	}

	/**
	 * Get a track by title and play it.
	 * 
	 * @param tracknumber
	 *            number of track to be played
	 */
	public final void play(final int tracknumber) {
		final AudioClip track = get(tracknumber);
		if (!track.isPlaying()) {
			track.play();
		}
	}
	
	/**
	 * Get a track by title and stop it from playing.
	 * @param tracknumber number of track to be stopped
	 */
	public final void stop(final int tracknumber) {
		if (get(tracknumber).isPlaying()) {
			get(tracknumber).stop();
		}
	}
	
	/**
	 * Pass the background track on to the appropriate class.
	 * @param enemies amount of enemies in the game.
	 */
	public final void backgroundTrack(final int enemies) {
		backgroundtrack.update(enemies);
	}
}
