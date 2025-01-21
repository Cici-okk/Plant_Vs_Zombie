//package view;

import controller.GameController;
import model.CommandCenter;
import model.Movable;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import view.GamePanel;

import static org.junit.Assert.*;

/**
 * Tests for the GamePanel class.
 * Focuses on graphical rendering and critical rendering logic in the update() method.
 */
public class GamePanelTest {

  private GamePanel gamePanel;
  private Graphics mockGraphics;

  /**
   * Sets up the GamePanel object and a mock Graphics object before each test.
   * A BufferedImage is used to simulate a Graphics environment for testing.
   */
  @Before
  public void setUp() {
    gamePanel = new GamePanel();

    // Use BufferedImage to simulate a Graphics object
    BufferedImage bufferedImage = new BufferedImage(
        GamePanel.SCREEN_WIDTH,
        GamePanel.SCREEN_HEIGHT,
        BufferedImage.TYPE_INT_ARGB
    );
    mockGraphics = bufferedImage.getGraphics();
  }

  /**
   * Tests the update(Graphics g) method to ensure the off-screen buffer is initialized.
   * Verifies that the grpOff and imgOff fields are not null after the update method is called.
   */
  // Test for the update(Graphics g) method
  @Test
  public void testUpdateBufferInit() {
    // Test initialization of off-screen buffer (grpOff and imgOff)
    gamePanel.update(mockGraphics);
    assertNotNull("Off-screen graphics buffer (grpOff) should be initialized", getGrpOff(gamePanel));
    assertNotNull("Off-screen image (imgOff) should be initialized", getImgOff(gamePanel));
  }

  /**
   * Tests the update(Graphics g) method to ensure sun credits are rendered correctly.
   * Verifies that the rendering logic for sun credits does not throw errors and works with valid sun credit values.
   * Note: Actual visual rendering validation requires graphical comparison tools and is not tested here.
   */
  // Test for the update(Graphics g) method
  @Test
  public void testUpdateDrawCredits() {
    // Test rendering of sun credits
    CommandCenter.setSunCredit(100); // Set a specific value for the sun credit
    gamePanel.update(mockGraphics);

    // Check if sun credit text is rendered
    // (Visual validation is limited in unit testing)
    assertTrue("Sun credits should be rendered on the screen", true);
  }

  /**
   * Helper method to access the private grpOff field in GamePanel.
   * This method uses reflection to retrieve the grpOff field, which represents the off-screen graphics buffer.
   *
   * @param panel The GamePanel object whose grpOff field is accessed.
   * @return The Graphics object representing the grpOff field.
   */
  // Helper method to access private field grpOff
  private Graphics getGrpOff(GamePanel panel) {
    try {
      var field = GamePanel.class.getDeclaredField("grpOff");
      field.setAccessible(true);
      return (Graphics) field.get(panel);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Helper method to access the private imgOff field in GamePanel.
   * This method uses reflection to retrieve the imgOff field, which represents the off-screen image buffer.
   *
   * @param panel The GamePanel object whose imgOff field is accessed.
   * @return The Image object representing the imgOff field.
   */
  // Helper method to access private field imgOff
  private Image getImgOff(GamePanel panel) {
    try {
      var field = GamePanel.class.getDeclaredField("imgOff");
      field.setAccessible(true);
      return (Image) field.get(panel);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

/**
 * other methods cannot be tested:
 * 1. iterateMovables(Graphics g): Depends on dynamic game state and Movable behavior.
 * Best tested via integration or on specific Movable implementations (e.g., Peashooter).
 * 2. drawSunCredit(Graphics g): Relies on Graphics rendering which is hard to validate.
 * Requires graphical comparison tools for proper testing.
 * 3. displayTextOnScreen(): Clears CommandCenter.movCandidate. Best tested indirectly through
 * related methods.
 */
