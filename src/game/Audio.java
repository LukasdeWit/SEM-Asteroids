package game;

import java.io.File;
import java.net.MalformedURLException;
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
	// ship noises
	/**
	 * Audioclip for the flying sound.
	 */
	private AudioClip flyingnoise;
	/**
	 * Audioclip for the shooting noise.
	 */
	//private final AudioClip shootingnoise;
	/**
	 * Audioclip for life up.
	 */
	//private final AudioClip extralife;
	// asteroid noises
	/**
	 * Audioclip for small asteroid breaking.
	 */
	//private AudioClip smallexplosion;
	/**
	 * Audioclip for medium asteroid breaking.
	 */
	//private AudioClip mediumexplosion;
	/**
	 * Audioclip for large asteroid breaking.
	 */
	//private AudioClip largeexplosion;
	// background beat
	/**
	 * First background beat noise.
	 */
	//private AudioClip beat1;
	/**
	 * Second background beat noise.
	 */
	//private AudioClip beat2;
	/**
	 * Map with key and value to easily find tracks.
	 */
	private Map<String, AudioClip> tracks;

	/**
	 * Constructor for audio class.
	 */
	public Audio() {
		File file = new File("audiofiles/thrust.mp3");
		try {
			flyingnoise = new AudioClip(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			System.out.println("Fail");
			flyingnoise = null;
		}
		/*
		shootingnoise = new AudioClip("fire.mp3");
		extralife = new AudioClip("extraShip.mp3");
		smallexplosion = new AudioClip("bangSmall.mp3");
		mediumexplosion = new AudioClip("bangMedium.mp3");
		largeexplosion = new AudioClip("bangLarge.mp3");
		beat1 = new AudioClip("beat1.mp3");
		beat2 = new AudioClip("beat2.mp3");*/

		
		tracks = new HashMap<String, AudioClip>();
		tracks.put("flying", flyingnoise);
		/*
		tracks.put("shooting", flyingnoise);
		tracks.put("1up", extralife);
		tracks.put("bangsmall", smallexplosion);
		tracks.put("bangmedium", mediumexplosion);
		tracks.put("banglarge", largeexplosion);
		tracks.put("beat1", beat1);
		tracks.put("beat2", beat2);
		 */
	}

	/**
	 * Get a track by title and play it.
	 * 
	 * @param title
	 *            title of track to be played
	 */
	public final void play(final String title) {
		final AudioClip track = tracks.get(title);
		track.play();
	}
}
