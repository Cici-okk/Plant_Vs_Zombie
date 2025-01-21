package model;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a Sun object in the game. The Sun can be either static or animated,
 * and it provides credit points when collected.
 */
public class Sun extends Sprite {
    private final static int SUN_RADIUS = 20;
    private final static int SCALER = 2;

    private int credit = 50;
    private int isStatic = 1;
    private int stopRotationY = 0;
    private int isLeftRotation = 1;

    /**
     * Gets the credit value of the Sun.
     *
     * @return The credit value.
     */
    public int getCredit() {
        return credit;
    }

    /**
     * Sets the credit value of the Sun.
     *
     * @param credit The credit value to set.
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    // Constructor for instructions
    /**
     * Constructor for creating a static Sun at a specific position.
     *
     * @param x The x-coordinate of the Sun.
     * @param y The y-coordinate of the Sun.
     */
    public Sun(int x, int y) {
        super();

        setRadius(SUN_RADIUS * 2);

        setPointCenter(new Point(x, y));

        figure();
    }

    // Constructor for annimation
    /**
     * Constructor for creating an animated Sun that falls from the top of the screen.
     *
     * @param x The x-coordinate where the Sun starts.
     */
    public Sun(int x){
        super();
        isStatic = 0;

        setnExpiry(400);
        setRadius(SUN_RADIUS*2);

        setPointCenter(new Point(x, 0));
        setDeltaY(1);

        figure();
    }

    /**
     * Defines the shape and appearance of the Sun using polar coordinates.
     */
    public void figure(){

        ArrayList<Point> pntCs = new ArrayList<Point>();
        // plot some figure...
        pntCs.add(new Point(0,20*SCALER));
        pntCs.add(new Point(-5*SCALER, 9*SCALER));
        pntCs.add(new Point(-20*SCALER, 12*SCALER));

        pntCs.add(new Point(-9*SCALER, 0*SCALER));

        pntCs.add(new Point(-18*SCALER, -7*SCALER));

        pntCs.add(new Point(-5*SCALER, -12*SCALER));

        pntCs.add(new Point(1*SCALER,-20*SCALER));

        pntCs.add(new Point(5*SCALER, -7*SCALER));
        pntCs.add(new Point(18*SCALER, -12*SCALER));

        pntCs.add(new Point(10*SCALER, 0*SCALER));
        pntCs.add(new Point(20*SCALER, 5*SCALER));
        pntCs.add(new Point(6*SCALER, 8*SCALER));

        assignPolarPoints(pntCs);
        setObjectPoints(pntCs);
        setOrientation(-90);

        if(GameController.randomNum.nextInt()%2 == 0){
            isLeftRotation=1;
        }
        else {
            isLeftRotation=-1;
        }

        stopRotationY = GameController.randomNum.nextInt()%100;
    }

    /**
     * Moves the Sun, either falling down or remaining static. Also handles rotation logic.
     */
    public void move(){
        super.move();

        int x = getPointCenter().x;
        int y = getPointCenter().y;

        int yUpdate = 0;
        System.out.println( "before ...y is "+ y);
        System.out.println( "this sun is static "+ isStatic);
        if (y>540 && isStatic == 0){
            yUpdate = 540;
        }
        else {
            System.out.println( "before ...y is "+ y);
            System.out.println( "before ...DeltaY is "+ getDeltaY());
            yUpdate = y+(int)getDeltaY();
            System.out.println( "after ...yudate is "+ yUpdate);
        }

        // Stop rotation.
        if (y > 300+stopRotationY){
            isLeftRotation = 0;
        }

        setPointCenter(new Point(x, yUpdate));
        setOrientation(getOrientation()+1*isLeftRotation);
    }

    /**
     * Removes the Sun when it expires.
     */
    public void expire(){
        if (getnExpiry() == 0)
            CommandCenter.movSun.remove(this);
        else
            setnExpiry(getnExpiry() - 1);
    }

    /**
     * Draws the Sun object on the screen.
     *
     * @param g The Graphics object for rendering.
     */
    public void draw(Graphics g) {
        super.draw(g);

        g.setColor(Color.ORANGE);
        //fill this polygon (with whatever color it has)
        g.fillPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());
        //now draw a white border

        g.drawPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());

        g.setColor(Color.YELLOW);
        g.fillOval(getPointCenter().x-SUN_RADIUS-3, getPointCenter().y-SUN_RADIUS-3, 2*SUN_RADIUS+6, 2*SUN_RADIUS+6);

    }

}
