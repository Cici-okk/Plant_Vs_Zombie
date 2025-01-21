import model.CandidateIcePeashooter;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for CandidateIcePeashooter.
 * This class contains unit tests for the CandidateIcePeashooter class, focusing on constructors, color, radius, and position.
 */
public class CandidateIcePeashooterTest {

  private CandidateIcePeashooter candidateIcePeashooter1;
  private CandidateIcePeashooter candidateIcePeashooter2;

  /**
   * Sets up the CandidateIcePeashooter objects before each test.
   * This method initializes two instances of CandidateIcePeashooter for testing.
   */
  @Before
  public void setUp() {
    candidateIcePeashooter1 = new CandidateIcePeashooter(400, 500);
    candidateIcePeashooter2 = new CandidateIcePeashooter(new Point(430, 530));
  }

  /**
   * Tests the constructors of CandidateIcePeashooter.
   * This method verifies the correct initialization of the CandidateIcePeashooter instances
   * using both the constructor that accepts (int, int) and the one that accepts a Point object.
   */
  @Test
  public void testConstructors() {
    // Test first constructor
    CandidateIcePeashooter cip1 = new CandidateIcePeashooter(200, 300);
    assertEquals(new Point(200, 300), cip1.getPointCenter());
    assertEquals(Color.CYAN, cip1.getColor());
    assertEquals(1, cip1.typeIndicator);

    // Test second constructor
    Point p = new Point(600, 700);
    CandidateIcePeashooter cip2 = new CandidateIcePeashooter(p);
    assertEquals(p, cip2.getPointCenter());
    assertEquals(Color.CYAN, cip2.getColor());
    assertEquals(1, cip1.typeIndicator);
  }

  /**
   * Tests the color of CandidateIcePeashooter.
   * This method checks that the color of the CandidateIcePeashooter instances is set to cyan.
   */
  @Test
  public void testColor() {
    assertEquals(Color.CYAN, candidateIcePeashooter1.getColor());
    assertEquals(Color.CYAN, candidateIcePeashooter2.getColor());
  }

  /**
   * Tests the radius of CandidateIcePeashooter.
   * This method verifies that the radius of CandidateIcePeashooter is correctly initialized to 100.
   */
  @Test
  public void testRadius() {
    assertEquals(100, candidateIcePeashooter1.getRadius());
    assertEquals(100, candidateIcePeashooter2.getRadius());
  }

  /**
   * Tests the initial position of CandidateIcePeashooter.
   * This method ensures that the initial position of CandidateIcePeashooter is correctly set
   * based on the constructor input.
   */
  @Test
  public void testInitialPosition() {
    assertEquals(new Point(400, 500), candidateIcePeashooter1.getPointCenter());
    assertEquals(new Point(400, 500), candidateIcePeashooter2.getPointCenter());
  }

}
