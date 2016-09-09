import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

/**
 * Class to regulate all audio output.
 * 
 * @author Esmee
 *
 */
public class Audio {
	// ship noises
	private AudioClip flyingnoise;
	private AudioClip shootingnoise;
	private AudioClip deathnoise;
	// asteroid noises
	private AudioClip smallexplosion;
	private AudioClip mediumexplosion;
	private AudioClip largeexplosion;
	// background beat, longer sound so mediaplayer instead of audioclip
	private MediaPlayer background;
}
