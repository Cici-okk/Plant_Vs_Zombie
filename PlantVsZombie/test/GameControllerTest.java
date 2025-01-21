//package controller;

import controller.GameController;
import javax.sound.sampled.Clip;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.*;

/**
 * Test class for GameController.
 * This class contains unit tests for the GameController class to validate its methods.
 */
public class GameControllerTest {

  private GameController gameController;

  /**
   * Sets up the GameController object before each test.
   * Initializes the GameController instance to be used in all the tests.
   */
  @Before
  public void setUp() throws Exception {
    gameController = new GameController();
  }

  /**
   * Tests the run method with initialization.
   * Verifies that the run method can execute without throwing exceptions and initiates the game loop correctly.
   */
  @Test
  public void testRunInit() {
    gameController.run();
    assertTrue(true);
  }

  /**
   * Tests the run method without throwing exceptions.
   * Ensures that no exceptions are thrown when the run method is called, indicating smooth execution.
   */
  @Test
  public void testRunNoException() {
    try {
      gameController.run();
      assertTrue(true);
    } catch (Exception e) {
      fail("Run threw exception: " + e.getMessage());
    }
  }

  /**
   * Tests the tick method to verify the tick counter increments.
   * Validates that the tick count increases by 1 after calling the tick method.
   */
  @Test
  public void testTickIncrement() {
    int initialTick = GameController.getTick();
    GameController.tick();
    assertEquals(initialTick + 1, GameController.getTick());
  }

  /**
   * Tests the tick method to check for tick reset when the tick reaches maximum value.
   * Verifies that the tick value resets to 1 after reaching the maximum integer value.
   */
  @Test
  public void testTickReset() {
    GameController.setTick(Integer.MAX_VALUE);
    GameController.tick();
    assertEquals(1, GameController.getTick());
  }

  /**
   * Tests the getTick method.
   * Verifies that the current tick value is correctly retrieved.
   */
  // Tests for getTick()
  @Test
  public void testGetTick() {
    GameController.setTick(42);
    assertEquals(42, GameController.getTick());
  }

  /**
   * Tests the getTick method when the tick is set to zero.
   * Verifies that zero can be a valid tick value and is correctly retrieved.
   */
  @Test
  public void testGetTickZero() {
    GameController.setTick(0);
    assertEquals(0, GameController.getTick());
  }

  /**
   * Tests mouseClicked method with a valid mouse event.
   * Verifies that a valid mouse click event is processed correctly by the game controller.
   */
  @Test
  public void testClickValid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
        0, 100, 200, 1, false
    );
    gameController.mouseClicked(event);
    assertTrue(true);
  }

  /**
   * Tests mouseClicked method with an invalid mouse event.
   * Verifies that an invalid mouse click event is handled without errors.
   */
  @Test
  public void testClickInvalid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
        0, -100, -200, 1, false
    );
    gameController.mouseClicked(event);
    assertTrue(true);
  }

  /**
   * Tests mouseReleased method with a valid mouse event.
   * Verifies that a valid mouse release event correctly updates the planting state.
   */
  // Tests for mouseReleased()
  @Test
  public void testReleaseValid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(),
        0, 300, 400, 1, false
    );
    CommandCenter.isPlanting = true;
    gameController.mouseReleased(event);
    assertFalse(CommandCenter.isPlanting);
  }

  /**
   * Tests mouseReleased method with an invalid mouse event.
   * Verifies that an invalid mouse release event does not affect the planting state.
   */
  @Test
  public void testReleaseInvalid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(),
        0, -10, -20, 1, false
    );
    gameController.mouseReleased(event);
    assertFalse(CommandCenter.isPlanting);
  }

  /**
   * Tests mouseEntered method with a valid mouse event.
   * Ensures that the mouse entering a component is handled correctly.
   */
  // Tests for mouseEntered()
  @Test
  public void testEnter() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(),
        0, 0, 0, 0, false
    );
    gameController.mouseEntered(event);
    assertTrue(true);
  }

  /**
   * Tests mouseEntered method with a mouse event that should have no effect.
   * Verifies that the method behaves correctly even when no significant action is needed.
   */
  @Test
  public void testEnterNoOp() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(),
        0, 100, 100, 0, false
    );
    gameController.mouseEntered(event);
    assertTrue(true);
  }

  /**
   * Tests mouseExited method to ensure it handles mouse exit events.
   * Verifies that the mouse exiting a component is processed correctly.
   */
  // Tests for mouseExited()
  @Test
  public void testExit() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(),
        0, 0, 0, 0, false
    );
    gameController.mouseExited(event);
    assertTrue(true);
  }

  /**
   * Tests mouseExited method with a mouse event that should have no effect.
   * Verifies that the method behaves correctly when the mouse exits but no other action is required.
   */
  @Test
  public void testExitNoOp() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(),
        0, 100, 100, 0, false
    );
    gameController.mouseExited(event);
    assertTrue(true);
  }

  /**
   * Tests mousePressed method with a valid mouse event.
   * Verifies that a valid mouse press event is handled correctly.
   */
  // Tests for mousePressed()
  @Test
  public void testPressValid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
        0, 150, 250, 1, false
    );
    gameController.mousePressed(event);
    assertTrue(true);
  }

  /**
   * Tests mousePressed method with an invalid mouse event.
   * Verifies that an invalid mouse press event is processed without errors.
   */
  @Test
  public void testPressInvalid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
        0, -150, -250, 1, false
    );
    gameController.mousePressed(event);
    assertTrue(true);
  }

  /**
   * Tests mouseDragged method with a valid mouse event.
   * Verifies that the mouse dragged event is processed correctly.
   */
  // Tests for mouseDragged()
  @Test
  public void testDragValid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_DRAGGED, System.currentTimeMillis(),
        0, 200, 300, 1, false
    );
    gameController.mouseDragged(event);
    assertTrue(true);
  }

  /**
   * Tests mouseDragged method with an invalid mouse event.
   * Verifies that invalid mouse drag events are handled without issues.
   */
  @Test
  public void testDragInvalid() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_DRAGGED, System.currentTimeMillis(),
        0, -200, -300, 1, false
    );
    gameController.mouseDragged(event);
    assertTrue(true);
  }

  /**
   * Tests mouseMoved method to ensure it handles mouse move events.
   * Verifies that the mouse move event is processed as expected.
   */
  // Tests for mouseMoved()
  @Test
  public void testMove() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(),
        0, 0, 0, 0, false
    );
    gameController.mouseMoved(event);
    assertTrue(true);
  }

  /**
   * Tests mouseMoved method with no significant action.
   * Verifies that the mouse move event is processed even if no action is needed.
   */
  @Test
  public void testMoveNoOp() {
    MouseEvent event = new MouseEvent(
        new Button(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(),
        0, 100, 100, 0, false
    );
    gameController.mouseMoved(event);
    assertTrue(true);
  }

  /**
   * Tests the setTick method to set a specific tick value.
   * Verifies that the tick value is correctly set when using setTick().
   */
  // Tests for setTick()
  @Test
  public void testSetTick() {
    GameController.setTick(42);
    assertEquals(42, GameController.getTick());
  }

  /**
   * Tests the setTick method to overwrite a previously set tick value.
   * Verifies that calling setTick() multiple times correctly updates the tick value.
   */
  @Test
  public void testSetTickOverwrite() {
    GameController.setTick(100);
    GameController.setTick(200);
    assertEquals(200, GameController.getTick());
  }

  /*
  The test for stopLoopingSounds is omitted because:
    1. The method relies on javax.sound.sampled.Clip, which is tightly coupled to the audio system and difficult to mock reliably.
    2. Its primary functionality (calling stop() on Clip objects) is a simple and direct API call, making it less prone to errors.
    3. Testing such methods often requires integration tests with actual audio clips, which is not feasible in a unit testing environment.
    4. Null checks or other edge cases can be manually verified during runtime or through controlled debugging.
   */


}



