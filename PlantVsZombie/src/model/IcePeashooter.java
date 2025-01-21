package model;
import java.awt.*;

/**
 * The IcePeashooter class represents a specialized type of Peashooter
 * that shoots ice bullets. Ice bullets slow down zombies upon impact.
 */
public class IcePeashooter extends Peashooter{

  /**
   * Constructs an IcePeashooter at the specified x and y coordinates.
   * Sets the color to differentiate it from other Peashooters.
   *
   * @param x The x-coordinate of the IcePeashooter.
   * @param y The y-coordinate of the IcePeashooter.
   */
  public IcePeashooter(int x, int y){
    super(x, y);
    setColor(Color.CYAN);
  }

  /**
   * Constructs an IcePeashooter at the specified Point.
   * Deducts the cost of planting (100 sun credits) from the player's total.
   *
   * @param newPoint The Point where the IcePeashooter is placed.
   */
  public IcePeashooter(Point newPoint){

    super(newPoint);
    setColor(Color.CYAN);
    CommandCenter.minusSunCredit(100);
  }
}
