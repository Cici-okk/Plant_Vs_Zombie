package model;

import java.awt.*;

/**
 * The Movable interface represents any object in the game
 * that can move, be drawn, detect collisions, and expire.
 */
public interface Movable {
    /**
     * Moves the object based on its specific behavior.
     */
    // to draw a movable object
    public void move();

    /**
     * Draws the object on the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g);

    // to check for collision
    /**
     * Gets the center point of the object.
     *
     * @return The Point representing the center of the object.
     */
    public Point getPointCenter();

    /**
     * Gets the radius of the object, used for collision detection.
     *
     * @return The radius of the object.
     */
    public int getRadius();

    // to check if the object is expired
    /**
     * Checks if the object is expired and handles its removal.
     */
    public void expire();

}
