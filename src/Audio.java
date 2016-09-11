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
	private AudioClip extralife;
	// asteroid noises
	private AudioClip smallexplosion;
	private AudioClip mediumexplosion;
	private AudioClip largeexplosion;
	// background beat
	private AudioClip beat1;
	private AudioClip beat2;
	
	public Audio() {
		
	}
	
	public void FlyingNoise() {
		flyingnoise.play();
	}
	
	public void ShootingNoise() {
		shootingnoise.play();
	}
	
	public void LifeUpNoise() {
		extralife.play();
	}
	
	public void SmallExplosion() {
		smallexplosion.play();
	}
	
	public void MediumExplosion() {
		mediumexplosion.play();
	}
	
	public void LargeExplosion() {
		largeexplosion.play();
	}
	
	public void BackgroundMusic() {
		// method to generate background noise from beat1 and beat2
	}
}
