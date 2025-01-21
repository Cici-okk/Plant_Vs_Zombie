package model;

import controller.GameController;
import view.GamePanel;

import java.awt.*;

/**
 * The LevelInstruction class represents an on-screen message
 * displayed to inform the player about the current level or important events.
 * It moves, expires, and can be drawn on the game panel.
 */
public class LevelInstruction  extends Sprite {

    public String strDisplay = "Level Instruction............";
    private FontMetrics fmt;
    private int nFontHeight;

    /**
     * Constructs a LevelInstruction at the specified position with a given message.
     * Initializes its expiry time, radius, and movement behavior.
     *
     * @param x    The x-coordinate of the instruction.
     * @param y    The y-coordinate of the instruction.
     * @param str_ The message to display.
     */
    public LevelInstruction(int x, int y, String str_) {
        super();


        strDisplay = str_;

        setnExpiry(150);

        setRadius(500);

        setDeltaY(1.5);

        setPointCenter(new Point(x, y));


    }

    /**
     * Moves the instruction vertically until it reaches a certain y-coordinate.
     * Updates the position based on delta values.
     */
    public void move() {
        super.move();

        int x = getPointCenter().x;
        int y = getPointCenter().y;

        int yUpdate = 0;
        if (y > 200) {
            yUpdate = 200;
        } else {
            yUpdate = y + (int) getDeltaY();
        }

        setPointCenter(new Point(x, yUpdate));
    }

    //override the expire method - once an object expires, then remove it from the arrayList.
    /**
     * Decreases the instruction's expiry time. Removes the instruction from
     * the list when it expires.
     */
    public void expire() {
        if (getnExpiry() == 0)
            CommandCenter.movLevelInstruction.remove(this);
        else
            setnExpiry(getnExpiry() - 1);
    }

    /**
     * Draws the instruction on the screen.
     * Displays the message centered horizontally at the instruction's position.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        // super.draw(g);

        g.setFont(GamePanel.fntBig);

        fmt = g.getFontMetrics();
        nFontHeight = fmt.getHeight();

        g.setColor(new Color(118, 210, 182));
        g.drawString(strDisplay,
                (GamePanel.SCREEN_WIDTH - fmt.stringWidth(strDisplay)) / 2, (int) getPointCenter().y);

        g.setFont(GamePanel.fnt);
    }
}
