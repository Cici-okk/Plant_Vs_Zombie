import model.IcePeashooter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for IcePeashooter.
 * This class contains JUnit tests to verify the functionality of the IcePeashooter class.
 */
public class IcePeashooterTest {

  private IcePeashooter icePeashooter1;
  private IcePeashooter icePeashooter2;

  /**
   * Sets up the IcePeashooter objects before each test.
   * This method initializes two IcePeashooter instances using different constructors.
   */
  @Before
  public void setUp() {
    icePeashooter1 = new IcePeashooter(300, 400);
    icePeashooter2 = new IcePeashooter(new Point(350, 450));
  }

  /**
   * Tests the constructors of IcePeashooter.
   * Validates both constructor overloads:
   * - Constructor that accepts integer coordinates for the x and y position.
   * - Constructor that accepts a Point object for the initial position.
   * Ensures the point is set correctly and the color is initialized as cyan.
   */
  @Test
  public void testConstructors() {
    // Test first constructor
    IcePeashooter ip1 = new IcePeashooter(100, 200);
    assertEquals(new Point(100, 200), ip1.getPointCenter());
    assertEquals(Color.CYAN, ip1.getColor());

    // Test second constructor
    Point p = new Point(500, 600);
    IcePeashooter ip2 = new IcePeashooter(p);
    assertEquals(p, ip2.getPointCenter());
    assertEquals(Color.CYAN, ip2.getColor());
  }

  /**
   * Tests the color of IcePeashooter.
   * Verifies that the color of IcePeashooter is set to cyan as expected.
   */
  @Test
  public void testColor() {
    assertEquals(Color.CYAN, icePeashooter1.getColor());
    assertEquals(Color.CYAN, icePeashooter2.getColor());
  }


  /**
   * Tests the initial position of IcePeashooter.
   * Ensures that the initial position of IcePeashooter is set correctly
   * for both constructors.
   */
  @Test
  public void testInitialPosition() {
    assertEquals(new Point(300, 400), icePeashooter1.getPointCenter());
    assertEquals(new Point(400, 500), icePeashooter2.getPointCenter());
  }

}
