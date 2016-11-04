package game;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoggerTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@Test
	public void testLog() {
		final String message = "Test";
		Logger.getInstance().log(message);
		final String actual = outContent.toString().substring(28, 28 + message.length());
	    assertEquals(message, actual);
	}

}
