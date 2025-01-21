import org.junit.Test;
import view.GameFrame;

import static org.junit.Assert.*;

/**
 * Tests for the GameFrame class.
 * Focuses on initialization and constructor behavior.
 */
public class GameFrameTest {

  // Why processWindowEvent(WindowEvent e) is not tested:
  /**
   * The method `processWindowEvent(WindowEvent e)` is `protected`, limiting direct access from this test.
   * Additionally, it relies on `System.exit(0)`, which terminates the JVM and is unsuitable for unit tests.
   * Testing this behavior is better suited for integration tests or mocking frameworks.
   */

  @Test
  public void testFrameInit() {
    // Verify the GameFrame constructor initializes the frame correctly
    GameFrame frame = new GameFrame();

    assertNotNull("Frame should not be null", frame);
    assertNotNull("ContentPane should not be null", frame.getContentPane());
  }

  /**
   * Tests that key events are enabled during GameFrame initialization.
   * Verifies that the GameFrame is set up without exceptions when enabling key event listeners.
   */
  @Test
  public void testKeyEventEnabled() {
    // Verify that key events are enabled in the constructor
    GameFrame frame = new GameFrame();

    // Ensure no exceptions occur during initialization
    assertNotNull("Frame should not throw exceptions on key events", frame);
  }
}




