import static org.junit.Assert.*;

import model.CommandCenter;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;

/**
 * Test class for CommandCenter.
 * This class tests various methods and functionalities in the CommandCenter class.
 */
public class CommandCenterTest {
  /**
   * Sets up the CommandCenter state before each test.
   * Initializes the game environment by calling the initGame method.
   */
  @Before
  public void setUp() {
    // Initialize the CommandCenter state before each test
    CommandCenter.initGame();
  }

  /**
   * Tests the addSunCredit method for positive amounts.
   * This method tests that sun credit is correctly added and the correct amount is returned.
   */
  // Test for addSunCredit method
  @Test
  public void testAddSunCreditPositive() {
    CommandCenter.addSunCredit(50);
    assertEquals(50, CommandCenter.getSunCredit());
    CommandCenter.addSunCredit(100);
    assertNotEquals(50, CommandCenter.getSunCredit());
  }

  /**
   * Tests the addSunCredit method for negative amounts.
   * This method tests that sun credit can be set to a negative value and reflects correctly.
   */
  @Test
  public void testAddSunCreditNegative() {
    CommandCenter.addSunCredit(-20);
    assertEquals(-20, CommandCenter.getSunCredit());
  }

  /**
   * Tests the getSunCredit method.
   * This method tests the initial state of sun credit and after updates.
   */
  // Test for getSunCredit method
  @Test
  public void testGetSunCreditInitial() {
    assertEquals(0, CommandCenter.getSunCredit());
  }

  /**
   * Tests the getSunCredit method after it has been updated.
   * Verifies that the correct sun credit value is returned after setting a new value.
   */
  @Test
  public void testGetSunCreditAfterUpdate() {
    CommandCenter.setSunCredit(100);
    assertEquals(100, CommandCenter.getSunCredit());
  }

  /**
   * Tests the setSunCredit method with positive values.
   * Verifies that sun credit can be set correctly and the value is updated.
   */
  // Test for setSunCredit method
  @Test
  public void testSetSunCreditPositive() {
    CommandCenter.setSunCredit(200);
    assertEquals(200, CommandCenter.getSunCredit());
  }

  /**
   * Tests the setSunCredit method with zero value.
   * Ensures that the sun credit can be set to zero correctly.
   */
  @Test
  public void testSetSunCreditZero() {
    CommandCenter.setSunCredit(0);
    assertEquals(0, CommandCenter.getSunCredit());
  }

  /**
   * Tests the initGame method to ensure it resets the sun credit.
   * Verifies that the sun credit is reset to zero when the game is initialized.
   */
  // Test for initGame method
  @Test
  public void testInitGameResetsSunCredit() {
    CommandCenter.setSunCredit(300);
    CommandCenter.initGame();
    assertEquals(0, CommandCenter.getSunCredit());
  }

  /**
   * Tests the initGame method to ensure it resets the game-over state.
   * Verifies that the game-over state is reset when the game is initialized.
   */
  @Test
  public void testInitGameResetsGameOver() {
    CommandCenter.setIsGameOver(true);
    CommandCenter.initGame();
    assertFalse(CommandCenter.isGameOver());
  }

  /**
   * Tests the setIsGameOver method by setting the game over state to true.
   * Verifies that the game-over state is correctly set to true.
   */
  // Test for setIsGameOver method
  @Test
  public void testSetIsGameOverTrue() {
    CommandCenter.setIsGameOver(true);
    assertTrue(CommandCenter.isGameOver());
  }

  /**
   * Tests the setIsGameOver method by setting the game over state to false.
   * Verifies that the game-over state is correctly set to false.
   */
  @Test
  public void testSetIsGameOverFalse() {
    CommandCenter.setIsGameOver(false);
    assertFalse(CommandCenter.isGameOver());
  }

  /**
   * Tests the zombieCrossYard method to ensure it triggers game-over state.
   * Verifies that when a zombie crosses the yard, the game-over state is triggered.
   */
  @Test
  public void testZombieCrossYardGameOver() {
    CommandCenter.zombieCrossYard();
    assertTrue(CommandCenter.isGameOver());
  }

  /**
   * Tests the getLevel method to verify the initial level is zero.
   * Ensures the initial level is set to zero when the game is initialized.
   */
  // Test for getLevel method
  @Test
  public void testGetLevelInitial() {
    assertEquals(0, CommandCenter.getLevel());
  }

  /**
   * Tests the getLevel method after updating the level.
   * Verifies that the correct level value is returned after it has been updated.
   */
  @Test
  public void testGetLevelAfterUpdate() {
    CommandCenter.setLevel(2);
    assertEquals(2, CommandCenter.getLevel());
  }

  /**
   * Tests the addLevel method to increment the level.
   * Verifies that the level increases when the addLevel method is called.
   */
  // Test for addLevel method
  @Test
  public void testAddLevelOnce() {
    CommandCenter.addLevel();
    assertEquals(1, CommandCenter.getLevel());
  }

  /**
   * Tests the addLevel method for multiple level increments.
   * Verifies that the level increments correctly when addLevel is called multiple times.
   */
  @Test
  public void testAddLevelMultiple() {
    CommandCenter.addLevel();
    CommandCenter.addLevel();
    assertEquals(2, CommandCenter.getLevel());
  }

  /**
   * Tests the setLevel method with a valid level value.
   * Ensures that the level is set correctly within the valid range.
   */
  // Test for setLevel method
  @Test
  public void testSetLevelWithinRange() {
    CommandCenter.setLevel(3);
    assertEquals(3, CommandCenter.getLevel());
  }

  /**
   * Tests the setLevel method with an invalid level value.
   * Verifies that an invalid level value (outside of range) is not set.
   */
  @Test
  public void testSetLevelExceedsRange() {
    CommandCenter.setLevel(5);
    assertNotEquals(5, CommandCenter.getLevel()); // Invalid level should not be set
  }

  /**
   * Tests the clearMovTemp method to ensure it resets planting state.
   * Verifies that the planting state is reset when the clearMovTemp method is called.
   */
  @Test
  public void testClearMovTempResetsPlanting() {
    CommandCenter.isPlanting = true;
    CommandCenter.clearMovTemp();
    assertFalse(CommandCenter.isPlanting);
  }

  /**
   * Tests the setPlant method with sufficient sun credit.
   * Verifies that the plant is set correctly when there is enough sun credit.
   */
  // Test for setPlant method
  @Test
  public void testSetPlantWithSufficientSun() {
    CommandCenter.setSunCredit(200);
    CommandCenter.setPlant(0);
    assertNotNull(CommandCenter.plant);
  }

  /**
   * Tests the setPlant method with insufficient sun credit.
   * Verifies that the plant is not set when there is not enough sun credit.
   */
  @Test
  public void testSetPlantWithInsufficientSun() {
    CommandCenter.setSunCredit(50);
    CommandCenter.setPlant(0);
    assertNull(CommandCenter.plant);
  }

  /**
   * Tests the setPlantPosition method while planting.
   * Verifies that the plant's position is set correctly when planting is enabled.
   */
  // Test for setPlantPosition method
  @Test
  public void testSetPlantPositionWhilePlanting() {
    CommandCenter.setSunCredit(200);
    CommandCenter.setPlant(0);
    Point position = new Point(100, 100);
    CommandCenter.setPlantPosition(position);
    assertEquals(position, CommandCenter.plant.getPointCenter());
  }

  /**
   * Tests the setPlantPosition method when not planting.
   * Verifies that the plant remains null when planting is disabled.
   */
  @Test
  public void testSetPlantPositionWhileNotPlanting() {
    CommandCenter.isPlanting = false;
    Point position = new Point(100, 100);
    CommandCenter.setPlantPosition(position);
    assertNull(CommandCenter.plant); // Plant should remain null
  }

  /**
   * Tests the setPaused method to pause the game.
   * Verifies that the paused state is correctly set to true when setPaused is called with true.
   */
  // Test for setPaused and isPaused methods
  @Test
  public void testSetPausedTrue() {
    CommandCenter.setPaused(true);
    assertTrue(CommandCenter.isPaused());
  }

  /**
   * Tests the setPaused method to unpause the game.
   * Verifies that the paused state is correctly set to false when setPaused is called with false.
   */
  @Test
  public void testSetPausedFalse() {
    CommandCenter.setPaused(false);
    assertFalse(CommandCenter.isPaused());
  }

  /**
   * Tests the setPlaying method to start playing.
   * Verifies that the playing state is correctly set to true when setPlaying is called with true.
   */
  // Test for isPlaying and setPlaying methods
  @Test
  public void testSetPlayingTrue() {
    CommandCenter.setPlaying(true);
    assertTrue(CommandCenter.isPlaying());
  }

  /**
   * Tests the setPlaying method to stop playing.
   * Verifies that the playing state is correctly set to false when setPlaying is called with false.
   */
  @Test
  public void testSetPlayingFalse() {
    CommandCenter.setPlaying(false);
    assertFalse(CommandCenter.isPlaying());
  }
}

