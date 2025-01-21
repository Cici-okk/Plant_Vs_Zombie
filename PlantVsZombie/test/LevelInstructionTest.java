import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.LevelInstruction;
import model.CommandCenter;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Test class for LevelInstruction.
 * This class contains JUnit tests to validate the functionality of the LevelInstruction class.
 */
public class LevelInstructionTest {

  private LevelInstruction levelInstruction;

  /**
   * Sets up a LevelInstruction object before each test.
   * Initializes the CommandCenter's movLevelInstruction list and creates a LevelInstruction instance for testing.
   */
  @Before
  public void setUp() {
    // Initialize a LevelInstruction object before each test
    levelInstruction = new LevelInstruction(100, 50, "Test Level Instruction");
    CommandCenter.movLevelInstruction = new CopyOnWriteArrayList<>(); // Ensure list is initialized
  }

  /**
   * Tests the move method to verify that the position of LevelInstruction is updated.
   * Ensures the position of the LevelInstruction object changes after calling the move method.
   */
  // Test for move method
  @Test
  public void testMoveUpdatesPosition() {
    // Verify that the move method updates the position of LevelInstruction
    Point initialPosition = levelInstruction.getPointCenter();
    levelInstruction.move();
    Point updatedPosition = levelInstruction.getPointCenter();
    assertNotEquals("Position should be updated after move", initialPosition, updatedPosition);
  }

  /**
   * Tests the move method to ensure that the Y position respects the boundary condition.
   * Verifies that the Y position does not exceed the upper limit (200) after multiple moves.
   */
  @Test
  public void testMoveRespectsBoundary() {
    // Ensure that the move method respects the boundary (y <= 200)
    for (int i = 0; i < 100; i++) {
      levelInstruction.move(); // Move multiple times to reach the boundary
    }
    assertTrue("Y position should not exceed 200", levelInstruction.getPointCenter().y <= 200);
  }

  /**
   * Tests the expire method to verify that it reduces the expiry value.
   * Ensures the expiry value decreases by 1 after calling the expire method.
   */
  // Test for expire method
  @Test
  public void testExpireReducesExpiry() {
    // Verify that the expire method reduces expiry by 1
    int initialExpiry = levelInstruction.getnExpiry();
    levelInstruction.expire();
    assertEquals("Expiry should reduce by 1", initialExpiry - 1, levelInstruction.getnExpiry());
  }

  /**
   * Tests the expire method to ensure that the LevelInstruction is removed from movLevelInstruction when expiry reaches 0.
   * Verifies that the LevelInstruction object is correctly removed from the CommandCenter's movLevelInstruction list.
   */
  @Test
  public void testExpireRemovesFromMovLevelInstruction() {
    // Verify that the LevelInstruction is removed from movLevelInstruction when expiry reaches 0
    CommandCenter.movLevelInstruction.add(levelInstruction); // Add to the list
    levelInstruction.setnExpiry(1); // Set expiry to 1
    levelInstruction.expire();
    assertFalse("LevelInstruction should be removed from movLevelInstruction",
        CommandCenter.movLevelInstruction.contains(levelInstruction));
  }


    /*
        The draw(Graphics g) method is omitted because:
        1. Graphics is abstract and tightly coupled with AWT rendering.
        2. Mocking Graphics requires complex setup with limited benefit.
        3. Drawing-related behavior like text alignment and color display is better tested through manual or integration tests.
    */
}
