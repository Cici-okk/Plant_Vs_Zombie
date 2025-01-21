package model;

import java.awt.*;

/**
 * The Bullet class represents a projectile shot by a Peashooter.
 * It handles the bullet's type, movement, and rendering.
 */
public class Bullet extends Sprite {

    private final static int BULLET_RADIUS = 40; // The size of the bullet
    private double FIRE_POWER = 15.0; // The speed of the bullet

    private Color bulletColor; // The color of the bullet, based on Peashooter
    public int bulletType; // The type of the bullet (0 for regular, 1 for ice)

    /**
     * Constructs a Bullet object fired by a given Peashooter.
     * The bullet inherits its type and color from the Peashooter.
     *
     * @param peashooter The Peashooter object firing the bullet.
     */
    public Bullet(Peashooter peashooter) {
        super();

        bulletColor = peashooter.getColor();

        // Determine bullet type based on color
        if (bulletColor.equals(Color.green)) {
            bulletType = 0; // Regular bullet
        } else if (bulletColor.equals(Color.cyan)) {
            bulletType = 1; // Ice bullet
        }

        setRadius(BULLET_RADIUS); // Set the bullet size
        setDeltaX(FIRE_POWER); // Set the horizontal speed
        setDeltaY(0); // No vertical movement

        // Set the initial position of the bullet relative to the Peashooter
        Point peashooterRelativePoint = peashooter.getPointCenter();
        setPointCenter(new Point(
            (int) peashooterRelativePoint.getX() + 20,
            (int) peashooterRelativePoint.getY() - 20
        ));
    }

    /**
     * Moves the bullet in the game. If the bullet moves out of bounds,
     * it is removed from the game.
     */
    public void move() {
        int x = getPointCenter().x;

        super.move();
        if (x > 1150) { // Remove the bullet if it goes off-screen
            CommandCenter.movBullets.remove(this);
        }
    }

    /**
     * Draws the bullet on the game screen.
     *
     * @param g The Graphics object used to draw the bullet.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(bulletColor); // Set the bullet color
        g.fillOval(getPointCenter().x, getPointCenter().y, BULLET_RADIUS, BULLET_RADIUS); // Draw the bullet
    }

    /**
     * Plays the sound effect for a bullet firing.
     */
    public static void bulletSoundEffect() {
        Sound.playSound("music/woodchopping.wav"); // Play bullet sound effect
    }
}
