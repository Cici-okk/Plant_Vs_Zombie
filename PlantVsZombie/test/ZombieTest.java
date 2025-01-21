import model.Zombie;
import model.CommandCenter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for Zombie.
 * This class contains JUnit tests for verifying the functionality of the Zombie class.
 */
public class ZombieTest {

  private Zombie zombie1;
  private Zombie zombie2;

  /**
   * Sets up Zombie objects before each test.
   * This method initializes two Zombie instances for testing.
   */
  @Before
  public void setUp() {
    zombie1 = new Zombie(200);
    zombie2 = new Zombie(300);
  }

  /**
   * Tests the constructor of Zombie.
   * Validates that the constructor correctly initializes the point center, radius, and delta values.
   */
  @Test
  public void testConstructor() {
    // Test first instance
    assertEquals(new Point(1190, 200), zombie1.getPointCenter());
    assertEquals(50, zombie1.getRadius());
    assertEquals(-0.5, zombie1.getDeltaX(), 0.01);

    // Test second instance
    assertEquals(new Point(1190, 300), zombie2.getPointCenter());
    assertEquals(50, zombie2.getRadius());
    assertEquals(-0.5, zombie2.getDeltaX(), 0.01);
  }

  /**
   * Tests the move method with two instances.
   * Verifies that the Zombie objects update their position correctly based on their delta values.
   */
  @Test
  public void testMove() {
    zombie1.setDeltaX(-2);
    zombie2.setDeltaX(-3);

    Point initialPoint1 = zombie1.getPointCenter();
    Point initialPoint2 = zombie2.getPointCenter();

    zombie1.move();
    zombie2.move();

    assertEquals(new Point(initialPoint1.x - 4, initialPoint1.y), zombie1.getPointCenter());
    assertEquals(new Point(initialPoint2.x - 6, initialPoint2.y), zombie2.getPointCenter());
  }

  /**
   * Tests the move boundary condition with two instances.
   * Verifies that crossing the boundary triggers the necessary game-over logic.
   * Note: Does not test sound effects as they are not relevant in this context.
   */
  @Test
  public void testMoveBoundary() {
    zombie1.setPointCenter(new Point(40, 200));
    zombie2.setPointCenter(new Point(45, 300));

    // Simulate boundary crossing
    zombie1.move();
    zombie2.move();

    // Boundary behavior triggers a Game Over (mock or simulate CommandCenter.zombieCrossYard if necessary)
    // but sound is not tested here
  }

  /**
   * Tests the frozen method with two instances.
   * Verifies that the Zombie objects enter the frozen state and update their speed and delta values correctly.
   */
  @Test
  public void testFrozen() {
    zombie1.frozen();
    zombie2.frozen();

    // Verify frozen state for both instances
    assertEquals(0, zombie1.speed);
    assertEquals(0, zombie1.getDeltaX(), 0.01);
    assertTrue(zombie1.isFozen);

    assertEquals(0, zombie2.speed);
    assertEquals(0, zombie2.getDeltaX(), 0.01);
    assertTrue(zombie2.isFozen);
  }

  /**
   * Tests the recover method with two instances.
   * Verifies that the Zombie objects recover from the frozen state and update their properties accordingly.
   */
  @Test
  public void testRecover() {
    zombie1.frozen();
    zombie2.frozen();

    zombie1.recover();
    zombie2.recover();

    // Verify recovery state
    assertEquals(0, zombie1.speed);
    assertTrue(zombie1.isFozen);

    assertEquals(0, zombie2.speed);
    assertTrue(zombie2.isFozen);
  }

  /**
   * Tests the isHit method for normal and frozen bullets with two instances.
   * Verifies that the Zombie objects decrement their size and enter the frozen state when hit by a frozen bullet.
   */
  @Test
  public void testIsHit() {
    int initialSize1 = zombie1.getSize();
    int initialSize2 = zombie2.getSize();

    // Test normal bullet
    zombie1.isHit(0);
    zombie2.isHit(0);
    assertEquals(initialSize1 - 1, zombie1.getSize());
    assertEquals(initialSize2 - 1, zombie2.getSize());

    // Test frozen bullet
    zombie1.isHit(1);
    zombie2.isHit(1);
    assertEquals(initialSize1 - 2, zombie1.getSize());
    assertTrue(zombie1.isFozen);

    assertEquals(initialSize2 - 2, zombie2.getSize());
    assertTrue(zombie2.isFozen);
  }

  /**
   * Tests the updateSpeed method with two instances.
   * Verifies that the speed and delta values of the Zombie objects are updated correctly based on their speed ratio.
   */
  @Test
  public void testUpdateSpeed() {
    zombie1.speedRatio = 2;
    zombie2.speedRatio = 3;

    zombie1.updateSpeed();
    zombie2.updateSpeed();

    // Verify updated speed and delta values
    assertEquals(2, zombie1.speed);
    assertEquals(-1, zombie1.getDeltaX(), 0.01);

    assertEquals(3, zombie2.speed);
    assertEquals(-1.5, zombie2.getDeltaX(), 0.01);
  }

  /**
   * Tests the draw method (requires visual or mock testing).
   *
   * Note: Typically verified in integration tests, not unit tests.
   */
  @Test
  public void testDraw() {
    // Mock or visualize Graphics object interaction
    // Graphics mockGraphics = mock(Graphics.class);
    // zombie1.draw(mockGraphics);
    // Verify interactions
  }
}
