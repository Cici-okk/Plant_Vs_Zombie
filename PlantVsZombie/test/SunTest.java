import model.Sun;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import static org.junit.Assert.*;

/**
 * Test class for Sun.
 * This class contains unit tests for the Sun class to validate its behavior, including constructors,
 * methods for movement, expiry, radius, color, and dimension handling.
 */
public class SunTest {

  private Sun staticSun1;
  private Sun staticSun2;
  private Sun movingSun1;
  private Sun movingSun2;

  /**
   * Sets up the Sun objects before each test.
   * Initializes two static Sun instances and two moving Sun instances for testing.
   */
  @Before
  public void setUp() {
    staticSun1 = new Sun(100, 200);
    staticSun2 = new Sun(150, 100);
    movingSun1 = new Sun(150);
    movingSun2 = new Sun(100);
  }

  /**
   * Tests the radius of the Sun.
   * Verifies that all Sun objects, whether static or moving, have the correct radius value.
   */
  @Test
  public void testSetGeTRadius() {
    assertEquals(40, staticSun1.getRadius());
    assertEquals(40, staticSun2.getRadius());
    assertEquals(40, movingSun1.getRadius());
    assertEquals(40, movingSun2.getRadius());
  }

  /**
   * Tests the initial position of the static Sun.
   * Ensures that the point center is correctly set for static Sun objects during initialization.
   */
  @Test
  public void testSetGetPointCenter() {
    assertEquals(new Point(100, 200), staticSun1.getPointCenter());
    assertEquals(new Point(150, 100), staticSun2.getPointCenter());
  }

  /**
   * Tests the expiry logic of the moving Sun.
   * Verifies that the expiry countdown decreases correctly when expire() is called,
   * and that the initial expiry value for moving Sun objects is set properly.
   */
  @Test
  public void testExpiry() {
    movingSun1.setnExpiry(5);
    movingSun1.expire();
    movingSun1.expire();
//    movingSun2.setnExpiry(5);
//    movingSun2.expire();
    assertEquals(3, movingSun1.getnExpiry());
    assertEquals(400, movingSun2.getnExpiry());
  }

  /**
   * Tests inherited movement logic from Sprite.
   * Verifies that moving Sun objects correctly update their position based on delta values.
   */
  @Test
  public void testMove() {
    movingSun1.setDeltaY(300);
    movingSun1.move();
    movingSun1.move();
    movingSun1.move();
    movingSun2.setDeltaX(0);
    movingSun2.setDeltaY(10);
    System.out.println("original point is " + movingSun2.getPointCenter());
    movingSun2.move();
    System.out.println(movingSun2.getPointCenter());
    movingSun2.move();
    movingSun2.move();

    Point expected1 = new Point(150, 540);
    assertEquals(expected1, movingSun1.getPointCenter());
    Point expected2 = new Point(100, 60);
    assertEquals(expected2, movingSun2.getPointCenter());
  }

  /**
   * Tests inherited color functionality from Sprite.
   * Verifies that Sun objects correctly handle color setting and retrieval.
   */
  @Test
  public void testColor() {
    staticSun1.setColor(Color.ORANGE);
    assertEquals(Color.ORANGE, staticSun1.getColor());
    staticSun2.setColor(Color.YELLOW);
    assertEquals(Color.YELLOW, staticSun2.getColor());
  }

  /**
   * Tests setting and getting the dimension of the gaming environment.
   * Verifies that Sun objects correctly update and retrieve the dimensions of the game screen.
   */
  @Test
  public void testDimension() {
    Dimension dim1 = new Dimension(800, 600);
    Dimension dim2 = new Dimension(400, 300);
    staticSun1.setDimension(dim1);
    assertEquals(dim1, staticSun1.getDimension());
    staticSun2.setDimension(dim2);
    assertEquals(dim2, staticSun2.getDimension());
  }
}
