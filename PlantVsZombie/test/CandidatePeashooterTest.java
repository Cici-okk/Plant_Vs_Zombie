import model.CandidatePeashooter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for CandidatePeashooter.
 * This class contains JUnit tests for verifying the functionality of the CandidatePeashooter class.
 */
public class CandidatePeashooterTest {

  private CandidatePeashooter candidatePeashooter1;
  private CandidatePeashooter candidatePeashooter2;

  /**
   * Sets up the CandidatePeashooter objects before each test.
   * This method initializes two CandidatePeashooter instances for testing.
   */
  @Before
  public void setUp() {
    candidatePeashooter1 = new CandidatePeashooter(200, 300);
    candidatePeashooter2 = new CandidatePeashooter(new Point(250, 350));
  }

  /**
   * Tests the constructors of CandidatePeashooter.
   * This test validates both constructor overloads:
   * - Constructor that accepts two integers for x and y coordinates.
   * - Constructor that accepts a Point object for the initial position.
   */
  @Test
  public void testConstructors() {
    // Test first constructor
    CandidatePeashooter c1 = new CandidatePeashooter(100, 150);
    assertEquals(new Point(100, 150), c1.getPointCenter());
    assertEquals(100, c1.getRadius());

    // Test second constructor
    Point p = new Point(300, 400);
    CandidatePeashooter c2 = new CandidatePeashooter(p);
    assertEquals(new Point(300, 400), c2.getPointCenter());
    assertEquals(100, c2.getRadius());
  }

  /**
   * Tests the radius of the CandidatePeashooter.
   * This test ensures that the radius of both CandidatePeashooter instances is correctly set.
   */
  @Test
  public void testRadius() {
    assertEquals(100, candidatePeashooter1.getRadius());
    assertEquals(100, candidatePeashooter2.getRadius());
  }

  /**
   * Tests the initial position of the CandidatePeashooter.
   * This test ensures that the initial position of the CandidatePeashooter objects is set correctly.
   */
  @Test
  public void testInitialPosition() {
    assertEquals(new Point(200, 300), candidatePeashooter1.getPointCenter());
    assertEquals(new Point(300, 400), candidatePeashooter2.getPointCenter());
  }

  /**
   * Tests the grid alignment logic in the second constructor.
   * This test verifies that the coordinates passed to the constructor are correctly aligned to the grid,
   * ensuring that the x and y values are multiples of 100.
   */
  @Test
  public void testGridAlignment() {
    Point alignedPoint = candidatePeashooter2.getPointCenter();
    assertEquals(0, alignedPoint.x % 100);
    assertEquals(0, alignedPoint.y % 100);
  }


  /**
   * Tests inherited color functionality from Sprite.
   * This test checks if the CandidatePeashooter class correctly handles color setting
   * inherited from the Sprite class.
   */
  @Test
  public void testColor() {
    candidatePeashooter1.setColor(Color.BLUE);
    assertEquals(Color.BLUE, candidatePeashooter1.getColor());
  }

  /**
   * Tests setting and getting the dimension of the gaming environment.
   * This test ensures that the CandidatePeashooter objects can correctly update and retrieve the
   * dimension of the gaming environment (screen size).
   */
  @Test
  public void testDimension() {
    Dimension dim = new Dimension(1024, 768);
    candidatePeashooter1.setDimension(dim);
    assertEquals(dim, candidatePeashooter1.getDimension());
  }
}
