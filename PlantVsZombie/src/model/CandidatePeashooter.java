package model;

import controller.GameController;

import java.awt.*;

/**
 * The CandidatePeashooter class represents a candidate peashooter
 * that can be placed in the game but does not move or shoot bullets.
 */

public class CandidatePeashooter extends Peashooter {

    /**
     * Constructs a CandidatePeashooter at the specified x and y coordinates.
     *
     * @param x The x-coordinate of the CandidatePeashooter.
     * @param y The y-coordinate of the CandidatePeashooter.
     */
    public CandidatePeashooter(int x, int y){
        super(x,y);
    }

    /**
     * Constructs a CandidatePeashooter at the specified Point.
     *
     * @param newPoint The Point where the CandidatePeashooter is placed.
     */
    public CandidatePeashooter(Point newPoint){
        super(newPoint);
    }

    /**
     * Overrides the move method. CandidatePeashooter does not move.
     */
    @Override
    // Candidate peashooter don't need to move or shot bullet
    public void move() {}
}