package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * The ExplodingHead class represents the animated explosion effect of a zombie's head.
 * It moves, rotates, and eventually expires, adding a dynamic visual element to the game.
 */
public class ExplodingHead extends Sprite  {

    // =============================2=================================
    // FIELDS
    // ==============================================================

    private final int MAX_EXPIRE = 100;					// how long the explosion lasts
    private final static int SCALER = 3;
    private final static int ZOMBIE_RADIUS = 50;

    private final int originalY;
    private int headRotation=1;

    public Color mainColor;
    // ==============================================================
    // CONSTRUCTOR
    // ==============================================================

    // Sets up the basic settings

    /**
     * Constructs an ExplodingHead at the specified position with the given color.
     *
     * @param pnt        The initial position of the explosion.
     * @param mainColor_ The color of the explosion.
     */
    public ExplodingHead(Point pnt, Color mainColor_) {
        super();

        mainColor = mainColor_;

        originalY = pnt.y;

        setDeltaX(0);
        setDeltaY(1);
        setPointCenter(pnt);

        setnExpiry(MAX_EXPIRE);
        setRadius(ZOMBIE_RADIUS);

        // playExplosionSounds();					// randomly plays an explosion sound

        ArrayList<Point> pntCs = new ArrayList<Point>();
        pntCs.add(new Point(0*SCALER, 18*SCALER));

        //right points
        pntCs.add(new Point(10*SCALER, 18*SCALER));
        pntCs.add(new Point(12*SCALER, 20*SCALER));
        pntCs.add(new Point(12*SCALER, 18*SCALER));
        pntCs.add(new Point(12*SCALER, 0*SCALER));
        pntCs.add(new Point(11*SCALER, -2*SCALER));
        pntCs.add(new Point(4*SCALER, -3*SCALER));

        //left points
        pntCs.add(new Point(-4*SCALER, -3*SCALER));
        pntCs.add(new Point(-11*SCALER, -2*SCALER));
        pntCs.add(new Point(-12*SCALER, 0*SCALER));
        pntCs.add(new Point(-12*SCALER, 18*SCALER));

        assignPolarPoints(pntCs);

        setOrientation(-100);
    }


    // ==============================================================
    // METHODS
    // ==============================================================

    // Randomly plays one of 4 explosion sounds (each sound is actually a sword sound effect from Link's Awakening, a Zelda game)

    /**
     * Plays a random explosion sound effect from a set of predefined sounds.
     */
    public void playExplosionSounds(){
        Random R = new Random();
        int nSound = R.nextInt(4);
        if(nSound == 0){
            Sound.playSound("LA_Sword_Slash1.wav");
        }
        else if(nSound == 1){
            Sound.playSound("LA_Sword_Slash2.wav");
        }
        else if(nSound == 2){
            Sound.playSound("LA_Sword_Slash3.wav");
        }
        else if(nSound == 3){
            Sound.playSound("LA_Sword_Slash4.wav");
        }
    }

    /**
     * Moves the explosion effect by updating its position based on delta values.
     * Ensures the explosion stays within a limited range from its original position.
     */
    public void move(){
        super.move();

        int x = getPointCenter().x;
        int y = getPointCenter().y;

        int yUpdate = 0;
        if (y>originalY+40){
            yUpdate = originalY+40;

        }
        else {
            yUpdate = y+(int)getDeltaY();
        }

        int xUpdate = x+(int)getDeltaX();

        setPointCenter(new Point(xUpdate, yUpdate));
    }

    /**
     * Draws the explosion on the game screen.
     * Includes visual effects like color, shape, and rotation.
     *
     * @param g The Graphics object used to draw the explosion.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // 1
        g.setColor(mainColor);
        g.fillPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());
        // 2
        g.setColor(Color.lightGray);
        g.drawPolygon(toArrayIntCoords(getnXCoords()), toArrayIntCoords(getnYCoords()), getnXCoords().size());
        // 3
        g.setColor(Color.black);
        g.fillArc(getPointCenter().x-45,getPointCenter().y-10, 35, 10,85,-175);
        // 4
        g.setColor(Color.darkGray);
        g.fillOval(getPointCenter().x-20, getPointCenter().y-30, 12, 10);
    }

    /**
     * Updates the explosion's expiry state.
     * Removes the explosion from the debris list when expired and adjusts its movement and rotation over time.
     */
    @Override
    public void expire() {
        if (getnExpiry() == 0){
            CommandCenter.movDebris.remove(this);
        }
        else{
            setnExpiry(getnExpiry() - 1);
        }

        if (getnExpiry()>MAX_EXPIRE-10){
            setDeltaY(-1);
            setDeltaX(1);
        }
        else if(getnExpiry()>MAX_EXPIRE-30) {
            setDeltaY(1);
            setDeltaX(0);
        }

        if (getnExpiry()>MAX_EXPIRE-10){
            setOrientation(getOrientation()+1);
        }
        else if(getnExpiry()<MAX_EXPIRE-10 && getnExpiry()>MAX_EXPIRE-20) {

        }
        else{
            if (getnExpiry()%10==0){
                headRotation = headRotation*(-1);
            }
            setOrientation(getOrientation()-1*headRotation);
        }
    }

}
