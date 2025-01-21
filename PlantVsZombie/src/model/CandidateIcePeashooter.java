package model;

import controller.GameController;

import java.awt.*;

/**
 * The CandidateIcePeashooter class represents a candidate ice peashooter
 * that can be placed in the game but does not move or shoot bullets.
 * It is a specialized type of Peashooter with ice effects.
 */
public class CandidateIcePeashooter extends Peashooter {

  /**
   * Constructs a CandidateIcePeashooter at the specified x and y coordinates.
   * Sets the color to cyan and type indicator to 1.
   *
   * @param x The x-coordinate of the CandidateIcePeashooter.
   * @param y The y-coordinate of the CandidateIcePeashooter.
   */
  public CandidateIcePeashooter(int x, int y) {
    super(x, y);
    setColor(Color.cyan); // Set the color to cyan
    typeIndicator = 1; // Set the type indicator for ice
  }

  /**
   * Constructs a CandidateIcePeashooter at the specified Point.
   * Sets the color to cyan and type indicator to 1.
   *
   * @param newPoint The Point where the CandidateIcePeashooter is placed.
   */
  public CandidateIcePeashooter(Point newPoint) {
    super(newPoint);
    setColor(Color.cyan); // Set the color to cyan
    typeIndicator = 1; // Set the type indicator for ice
  }

  /**
   * Overrides the move method. CandidateIcePeashooter does not move.
   */
  @Override
  public void move() {
    // Candidate peashooters do not move
  }
}

