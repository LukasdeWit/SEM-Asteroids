package game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import entity.Player;
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
	 * Track number for player 2's rocket boost.
	 */
	public static final int BOOST2 = 8;
	/**
	 * Track number for powerup.
	 */
	public static final int POWERUP = 9;
	/**
	 * Track number for hyperspace.
	 */
	public static final int TELEPORT = 10;
	/**
	 * Track number for second player shooting.
	 */
	public static final int SHOOTING2 = 11;

	/**
	 * Map with key and value to easily find tracks.
	 */
	private final List<AudioClip> tracks;
	/**
	 * Class that regulates the background track.
	 */
	private final BackgroundAudio bgtrack;
	private boolean mute;
	private boolean released;
	
	private static final double BOOSTVOLUME = 0.5;
	private static final double SHOOTINGVOLUME = 0.4;
	private static final double UFOSMALLVOLUME = 0.3;
	private static final double UFOBIGVOLUME = 0.3;
	private static final double TELEPORTVOLUME = 0.7;
	private static final double SHOOTING2VOLUME = 0.3;
	private static final double ASTEROIDVOLUME = 0.5;
	private static final double POWERUPVOLUME = 0.5;
	

	/**
	 * Constructor for audio class.
	 */
	public Audio() {
		mute = false;
		tracks = new ArrayList<AudioClip>();
		bgtrack = new BackgroundAudio(PATH);
		
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
					PATH + "lifeup.wav").toURI().toURL().toString());
			final AudioClip ufosmall = new AudioClip(new File(
					PATH + "ufoSmall.mp3").toURI().toURL().toString());
			final AudioClip ufobig = new AudioClip(new File(
					PATH + "ufoBig.mp3").toURI().toURL().toString());
			final AudioClip boost = new AudioClip(new File(
					PATH + "boost.mp3").toURI().toURL().toString());
			final AudioClip teleport = new AudioClip(new File(
					PATH + "teleport.wav").toURI().toURL().toString());
			final AudioClip powerup = new AudioClip(new File(
					PATH + "pickup.wav").toURI().toURL().toString());
			final AudioClip boost2 = new AudioClip(new File(
					PATH + "boost2.mp3").toURI().toURL().toString());
			final AudioClip shooting2 = new AudioClip(new File(
					PATH + "fire2.wav").toURI().toURL().toString());

			
			ufosmall.setCycleCount(AudioClip.INDEFINITE);
			ufobig.setCycleCount(AudioClip.INDEFINITE);
			boost.setCycleCount(AudioClip.INDEFINITE);
			
			// Adjust volume of the noisier audio clips
			boost.setVolume(BOOSTVOLUME);
			shooting.setVolume(SHOOTINGVOLUME);
			ufosmall.setVolume(UFOSMALLVOLUME);
			ufobig.setVolume(UFOBIGVOLUME);
			teleport.setVolume(TELEPORTVOLUME);
			shooting2.setVolume(SHOOTING2VOLUME);
			smallexplosion.setVolume(ASTEROIDVOLUME);
			mediumexplosion.setVolume(ASTEROIDVOLUME);
			largeexplosion.setVolume(ASTEROIDVOLUME);
			powerup.setVolume(POWERUPVOLUME);
			
			tracks.add(shooting);
			tracks.add(smallexplosion);
			tracks.add(mediumexplosion);
			tracks.add(largeexplosion);
			tracks.add(lifeup);
			tracks.add(ufosmall);
			tracks.add(ufobig);
			tracks.add(boost);
			tracks.add(boost2);
			tracks.add(powerup);
			tracks.add(teleport);
			tracks.add(shooting2);
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
		if (!mute) {
			final AudioClip track = get(tracknumber);
			if (!track.isPlaying()) {
				track.play();
			}
		}
	}
	
	/**
	 * Get a track by title and play it (if it's fine that the track is playing multiple times).
	 * 
	 * @param tracknumber
	 *            number of track to be played
	 */
	public final void playMultiple(final int tracknumber) {
		if (!mute) {
			final AudioClip track = get(tracknumber);
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
		if (!mute) {
			bgtrack.update(enemies);
		}
	}
	
	/**
	 * Silence all currently playing tracks.
	 */
	public final void stopAll() {
		for (int i = 0; i < tracks.size(); i++) {
			stop(i);
		}
	}
	
	/**
	 * @param p - player for whom the rocketboost should be played.
	 */
	public final void rocketBoost(final Player p) {
		if (p.isPlayerTwo()) {
			if (p.isBoost() && p.isAlive()) {
				play(BOOST2);
			} else {
				stop(BOOST2);
			}
		} else {
			if (p.isBoost() && p.isAlive()) {
				play(BOOST);
			} else {
				stop(BOOST);
			}
		}
	}

	/**
	 * Switch between being mute or not muted.
	 */
	public final void switchMute() {
		if (mute) {
			mute = false;
			Logger.getInstance().log("Sounds unmuted");
		} else {
			mute = true;
			Logger.getInstance().log("Sounds muted");
			stopAll();
		}
	}

	/**
	 * @return the mute
	 */
	public final boolean isMute() {
		return mute;
	}

	/**
	 * @param mute the mute to set
	 */
	public final void setMute(final boolean mute) {
		this.mute = mute;
	}

	/**
	 * mutes if m is pressed.
	 * @param input - the input
	 */
	public final void update(final List<String> input) {
		if (input.contains("M") && released) {
			switchMute();
			released = false;
		} else if (!input.contains("M")) {
			released = true;
		}
	}
}
