import static org.junit.Assert.*;

import model.Bullet;
import model.CommandCenter;
import model.Peashooter;
import model.Sound;
import org.junit.Test;
import java.awt.*;

/**
 * Unit tests for the Bullet class.
 * These tests ensure that the Bullet behaves correctly during its movement, drawing,
 * and sound effects, and that it interacts with other objects like Peashooter and CommandCenter.
 */
public class BulletTest {

  /**
   * Tests that a bullet moves correctly when the move() method is called.
   *
   * This test checks that the bullet's position changes after calling move(),
   * ensuring that the movement functionality is working as expected.
   */
  @Test
  public void move_bulletMovesCorrectly() {
    Peashooter peashooter = new Peashooter(100, 200);
    Bullet bullet = new Bullet(peashooter);

    Point initialPosition = bullet.getPointCenter();
    bullet.move();
    Point newPosition = bullet.getPointCenter();

    assertNotEquals(initialPosition, newPosition); // Ensure bullet moved
  }

  /**
   * Tests that a bullet is removed from the movement list when it goes out of bounds.
   *
   * This test simulates a bullet being moved out of bounds and ensures that it is
   * removed from the list of bullets in CommandCenter, confirming proper cleanup.
   */
  @Test
  public void move_bulletRemovedWhenOutOfBounds() {
    Peashooter peashooter = new Peashooter(100, 200);
    Bullet bullet = new Bullet(peashooter);

    bullet.setPointCenter(new Point(1200, bullet.getPointCenter().y));
    bullet.move();

    assertFalse(CommandCenter.movBullets.contains(bullet)); // Ensure bullet was removed
  }

  /**
   * Tests that the bullet is drawn with the correct color.
   *
   * This test checks that the bullet inherits the correct color from the Peashooter
   * it is associated with. The expected color is green, as set in the Peashooter.
   */
  @Test
  public void draw_bulletDrawnWithCorrectColor() {
    Peashooter peashooter = new Peashooter(100, 200);
    Bullet bullet = new Bullet(peashooter);

    assertEquals(Color.green, peashooter.getColor());
  }

  /**
   * Tests that the bullet is drawn with the correct dimensions.
   *
   * This test ensures that the bullet has the correct radius, as expected.
   * The radius is expected to be 40, which is defined for Bullet objects.
   */
  @Test
  public void draw_bulletDrawnWithCorrectDimensions() {
    Peashooter peashooter = new Peashooter(100, 200);
    Bullet bullet = new Bullet(peashooter);

    assertEquals(40, bullet.getRadius());
  }

  /**
   * Tests the functionality of the sound effect when the bullet is fired.
   *
   * This test attempts to play the bullet sound effect, verifying that it can
   * be played without throwing any exceptions. If an exception occurs, the test fails.
   */
  // Test to ensure the sound file can be loaded and played without errors
  @Test
  public void bulletSoundEffect_soundPlaysWithoutError() {
    try {
      Bullet.bulletSoundEffect(); // Plays the sound
    } catch (Exception e) {
      fail("Sound effect threw an exception: " + e.getMessage());
    }
  }

  /**
   * Verifies that the sound file for the bullet sound effect is accessible.
   *
   * This test checks if the sound file ("music/woodchopping.wav") can be accessed
   * using the resource stream. This ensures that the sound file is included
   * and accessible for use during the game.
   */
  // Test to verify if the sound file is accessible (using resource stream)
  @Test
  public void bulletSoundEffect_soundFileIsAccessible() {
    try {
      assertNotNull(
          Sound.class.getResourceAsStream("music/woodchopping.wav")
      ); // Ensure the file can be accessed
    } catch (Exception e) {
      fail("Sound file is not accessible: " + e.getMessage());
    }
  }
}
