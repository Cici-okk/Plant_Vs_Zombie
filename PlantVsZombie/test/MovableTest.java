/**
 * No direct tests are written for the Movable interface because it only defines
 * the behavior that must be implemented by concrete classes. The actual functionality
 * of Movable methods (e.g., move(), draw(), getPointCenter()) is tested indirectly
 * through the concrete classes that implement Movable, such as Sprite and its subclasses
 * (e.g., Peashooter). This ensures that the behavior defined by the interface is validated
 * in real-world use cases without redundant tests.
 */
//package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MovableTest {

  @Test
  public void move() {
  }

  @Test
  public void draw() {
  }

  @Test
  public void getPointCenter() {
  }

  @Test
  public void getRadius() {
  }

  @Test
  public void expire() {
  }
}