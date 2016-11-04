package game;

import entity.Player;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to regulate all foreground audio output. background audio is handled by {@link BackgroundAudio}
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
		tracks = new ArrayList<>();
		bgtrack = new BackgroundAudio(PATH);

		try {
			initAudio();
		} catch (MalformedURLException e) {
			Logger.getInstance().log("failed to initialize audio");
		}
	}

	/**
	 * initialise all audio tracks and add them to the track collection.
	 *
	 * @throws MalformedURLException when one of the URLS for the audio files was malformed
	 */
	private void initAudio() throws MalformedURLException {
		final AudioClip ufosmall = createTrackWithVolume(UFOSMALLVOLUME, "ufoSmall.mp3");
		final AudioClip ufobig = createTrackWithVolume(UFOBIGVOLUME, "ufoBig.mp3");
		final AudioClip boost = createTrackWithVolume(BOOSTVOLUME, "boost.mp3");

		ufosmall.setCycleCount(AudioClip.INDEFINITE);
		ufobig.setCycleCount(AudioClip.INDEFINITE);
		boost.setCycleCount(AudioClip.INDEFINITE);

		tracks.add(createTrackWithVolume(SHOOTINGVOLUME, "fire.mp3"));
		tracks.add(createTrackWithVolume(ASTEROIDVOLUME, "bangSmall.mp3"));
		tracks.add(createTrackWithVolume(ASTEROIDVOLUME, "bangMedium.mp3"));
		tracks.add(createTrackWithVolume(ASTEROIDVOLUME, "bangLarge.mp3"));
		tracks.add(createTrack("lifeup.wav"));
		tracks.add(ufosmall);
		tracks.add(ufobig);
		tracks.add(boost);
		tracks.add(createTrack("boost2.mp3"));
		tracks.add(createTrackWithVolume(POWERUPVOLUME, "pickup.wav"));
		tracks.add(createTrackWithVolume(TELEPORTVOLUME, "teleport.wav"));
		tracks.add(createTrackWithVolume(SHOOTING2VOLUME, "fire2.wav"));
	}

	/**
	 * factory method to create a track from a file name.
	 * @param filename the name of the sound file we want to add
	 * @return the resulting AudioClip
	 * @throws MalformedURLException when the filename caused the URL to be malformed
	 */
	private static AudioClip createTrack(final String filename) throws MalformedURLException {
		return new AudioClip(new File(PATH + filename).toURI().toURL().toString());
	}

	/**
	 * factory method to create a track from a file name and set the volume.
	 * @param volume the volume we want this audioclip to use
	 * @param fileName the name of the sound file we want to add
	 * @return the resulting AudioClip
	 * @throws MalformedURLException when the filename caused the URL to be malformed
	 */
	private static AudioClip createTrackWithVolume(final double volume, final String fileName)
			throws MalformedURLException {
		final AudioClip audioClip = new AudioClip(new File(PATH + fileName).toURI().toURL().toString());

		audioClip.setVolume(volume);
		return audioClip;
	}

	/**
	 * Get a track by title.
	 * 
	 * @param trackNumber
	 *            number of track to be played
	 * @return AudioClip with that title
	 */
	public final AudioClip get(final int trackNumber) {
		return tracks.get(trackNumber);
	}

	/**
	 * Get a track by title and play it.
	 * 
	 * @param trackNumber
	 *            number of track to be played
	 */
	public final void play(final int trackNumber) {
		if (!mute) {
			final AudioClip track = get(trackNumber);
			if (!track.isPlaying()) {
				track.play();
			}
		}
	}
	
	/**
	 * Get a track by title and play it (if it's fine that the track is playing multiple times).
	 * 
	 * @param trackNumber
	 *            number of track to be played
	 */
	public final void playMultiple(final int trackNumber) {
		if (!mute) {
			final AudioClip track = get(trackNumber);
			track.play();
		}
	}
	
	/**
	 * Get a track by title and stop it from playing.
	 * @param trackNumber number of track to be stopped
	 */
	public final void stop(final int trackNumber) {
		if (get(trackNumber).isPlaying()) {
			get(trackNumber).stop();
		}
	}
	
	/**
	 * Pass the background track on to the appropriate class.
	 * @param enemies amount of enemies in the game.
	 */
	public final void backgroundTrack(final int enemies) {
		bgtrack.update(enemies, mute);
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
	 * Setter that says whether the m key is released or not (for tests).
	 * @param released true if m is released
	 */
	public final void setReleased(final boolean released) {
		this.released = released;
	}
	
	/**
	 * Getter to determine whether the m key is released or not.
	 * @return true if it is released.
	 */
	public final boolean isReleased() {
		return released;
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
	
	/**
	 * @return backgroundaudio.
	 */
	public final BackgroundAudio getBackgroundAudio() {
		return bgtrack;
	}
}
