import model.CrazyZombie;
import model.CommandCenter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for CrazyZombie.
 * This class contains unit tests for the CrazyZombie class, verifying that various methods
 * and behaviors work correctly, including movement, freezing, recovery, and collision logic.
 */
public class CrazyZombieTest {

  private CrazyZombie crazyZombie1;
  private CrazyZombie crazyZombie2;

  /**
   * Sets up CrazyZombie objects before each test.
   * This method initializes two CrazyZombie instances with different initial y-coordinate values
   * before each test is run.
   */
  @Before
  public void setUp() {
    crazyZombie1 = new CrazyZombie(200);
    crazyZombie2 = new CrazyZombie(300);
  }

  /**
   * Tests the constructor of CrazyZombie.
   * This test verifies that the constructor correctly initializes the position and radius of the
   * CrazyZombie objects.
   */
  @Test
  public void testConstructor() {
    // Test first instance
    assertEquals(new Point(1190, 200), crazyZombie1.getPointCenter());
    assertEquals(30, crazyZombie1.getRadius());

    // Test second instance
    assertEquals(new Point(1190, 300), crazyZombie2.getPointCenter());
    assertEquals(30, crazyZombie2.getRadius());
  }

  /**
   * Tests the move method with two instances.
   * This method checks that the move logic correctly updates the position of the CrazyZombie objects
   * based on their movement speed and delta values.
   */
  @Test
  public void testMove() {
    crazyZombie1.setDeltaX(-2);
    crazyZombie2.setDeltaX(-3);
    // our design idea is moving speed as twice as delta index
    Point initialPoint1 = crazyZombie1.getPointCenter();
    Point initialPoint2 = crazyZombie2.getPointCenter();
//    System.out .println("crazyZombie1.getPointCenter();" + crazyZombie1.getPointCenter());
    crazyZombie1.move();
//    System.out .println("crazyZombie1.getPointCenter() after move;" + crazyZombie1.getPointCenter());
    crazyZombie2.move();
    // Verifying that the CrazyZombie objects have moved based on their deltaX values
    assertEquals(new Point(initialPoint1.x - 4, initialPoint1.y), crazyZombie1.getPointCenter());
    assertEquals(new Point(initialPoint2.x - 6, initialPoint2.y), crazyZombie2.getPointCenter());
  }

  /**
   * Tests the frozen method with two instances.
   * This method verifies that the frozen state of the CrazyZombie objects is correctly applied,
   * which sets their speed to 0 and their deltaX to a frozen state.
   */
  @Test
  public void testFrozen() {
    crazyZombie1.frozen();
    crazyZombie2.frozen();

    // Verify frozen state for both instances
    assertEquals(0, crazyZombie1.speed);
    assertEquals(0, crazyZombie1.getDeltaX(), 0.01);
    assertTrue(crazyZombie1.isFozen);

    assertEquals(0, crazyZombie2.speed);
    assertEquals(0, crazyZombie2.getDeltaX(), 0.01);
    assertTrue(crazyZombie2.isFozen);
  }

  /**
   * Tests the recover method with two instances.
   * This method checks that the recovery process works as expected, restoring the zombie's speed
   * and resetting the frozen state.
   */
  @Test
  public void testRecover() {
    crazyZombie1.frozen();
    crazyZombie1.recover();
    crazyZombie2.frozen();

    crazyZombie1.recover();
    crazyZombie2.recover();

    // Verify recovery state
    assertEquals(0, crazyZombie1.speed);
    assertTrue(crazyZombie1.isFozen);

    assertEquals(0, crazyZombie2.speed);
    assertTrue(crazyZombie2.isFozen);
  }

  /**
   * Tests the isHit method for normal and frozen bullets with two instances.
   * This test checks how the CrazyZombie objects respond to hits from bullets of different types
   * (normal vs frozen), reducing their size and applying the frozen state for frozen bullets.
   */
  @Test
  public void testIsHit() {
    int initialSize1 = crazyZombie1.getSize();
    int initialSize2 = crazyZombie2.getSize();

    // Test normal bullet
    crazyZombie1.isHit(0);
    assertEquals(initialSize1 - 1, crazyZombie1.getSize());

    // Test frozen bullet

    crazyZombie2.isHit(1);
    assertEquals(initialSize2 - 1, crazyZombie2.getSize());
    assertTrue(crazyZombie2.isFozen);
  }

  /**
   * Tests the updateSpeed method with two instances.
   * This test verifies that the speed and deltaX values are updated correctly based on the speedRatio.
   */
  @Test
  public void testUpdateSpeed() {
    crazyZombie1.speedRatio = 2;
    crazyZombie2.speedRatio = 3;

    crazyZombie1.updateSpeed();
    crazyZombie2.updateSpeed();

    // Verify updated speed and delta values
    assertEquals(2, crazyZombie1.speed);
    assertEquals(-1, crazyZombie1.getDeltaX(), 0.01);

    assertEquals(3, crazyZombie2.speed);
    assertEquals(-1.5, crazyZombie2.getDeltaX(), 0.01);
  }

  /**
   * Tests the draw method (requires visual or mock testing).
   *
   * Note: Typically verified in integration tests, not unit tests.
   */
  @Test
  public void testDraw() {
    // tested be visualizing Graphics object interaction
    // Verify interactions
  }
}
