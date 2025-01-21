import model.IcePeashooter;
import model.Peashooter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for Peashooter.
 * This class contains JUnit tests for verifying the functionality of Peashooter and IcePeashooter classes.
 */
public class PeashooterTest {

  private Peashooter peashooter1;
  private IcePeashooter peashooter2;

  /**
   * Sets up the Peashooter objects before each test.
   * Initializes two instances, one Peashooter and one IcePeashooter, to test both functionality and inheritance.
   */
  @Before
  public void setUp() {
    peashooter1 = new Peashooter(100, 200);
    peashooter2 = new IcePeashooter(new Point(150, 250));
  }

  /**
   * Tests the setRadius and getRadius methods.
   * Verifies that the radius of both Peashooter and IcePeashooter objects is correctly set and retrieved.
   */
  @Test
  public void testSetGetRadius() {
    assertEquals(100, peashooter1.getRadius());
    assertEquals(100, peashooter2.getRadius());
  }

  /**
   * Tests the setPointCenter and getPointCenter methods.
   * Verifies that the initial position of Peashooter and IcePeashooter objects is correctly set and retrieved.
   */
  @Test
  public void testSetGetPointCenter() {
    assertEquals(new Point(100, 200), peashooter1.getPointCenter());
    assertEquals(new Point(200, 300), peashooter2.getPointCenter());
  }

  /**
   * Tests the grid alignment logic in the IcePeashooter constructor.
   * Verifies that the coordinates passed to the IcePeashooter constructor are aligned to the grid,
   * ensuring x and y are multiples of 100.
   */
  @Test
  public void testGridAlignment() {
    Point alignedPoint = peashooter2.getPointCenter();
    assertEquals(0, alignedPoint.x % 100);
    assertEquals(0, alignedPoint.y % 100);
  }


  // the move() method cannot be simply tested here because of dependencies on GameController and
  // CommandCenter.GameController.getTick() determines the behavior, so it needs to be controlled or
  // mocked; CommandCenter.movBullets is modified, so we need to verify its state.
  // besides Bullet object should be verified that it is created with the correct parent reference.

  /**
   * Tests the setColor and getColor methods inherited from Sprite.
   * Verifies that the color of Peashooter and IcePeashooter objects is correctly set and retrieved.
   */
  @Test
  public void testSetGetColor() {
    assertEquals(Color.GREEN, peashooter1.getColor());
    assertEquals(Color.CYAN, peashooter2.getColor());
  }

  /**
   * Tests the setDimension and getDimension methods inherited from Sprite.
   * Verifies that the dimension of the gaming environment is correctly set and retrieved for the Peashooter object.
   */
  @Test
  public void testSetGetDimension() {
    Dimension dim = new Dimension(800, 600);
    peashooter1.setDimension(dim);
    assertEquals(dim, peashooter1.getDimension());
  }
}
