package game;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BackgroundAudioTest {
	BackgroundAudio bgaudio;
	
	@Before
	public void setup() {
		Game thisGame = new Game();
		bgaudio = thisGame.getAudio().getBackgroundAudio();
	}
	
	@Test
	public void testUpdate1() {
		bgaudio.setTime(0);
		bgaudio.update(0, true);
		assertEquals(bgaudio.getInterval(), bgaudio.getBASELINE());
	}
	
	@Test
	public void testUpdate2() {
		bgaudio.setTime(0);
		bgaudio.update(5, true);
		assertEquals(bgaudio.getInterval(), bgaudio.getBASELINE() / 5);
	}
	
	@Test
	public void testUpdate3() {
		bgaudio.setTime(System.currentTimeMillis());
		bgaudio.update(5, true);
		assertEquals(bgaudio.getInterval(), bgaudio.getBASELINE());
	}
	
	@Test
	public void testBG1() {
		bgaudio.setTime(0);
		assertEquals(bgaudio.isBg(), true);
		bgaudio.update(5, true);
		bgaudio.setTime(0);
		assertEquals(bgaudio.isBg(), false);
		bgaudio.update(5, true);
		bgaudio.setTime(0);
		assertEquals(bgaudio.isBg(), true);
	}

}
