package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a Zombie character in the game.
 * Handles movement, drawing, and interaction with other game elements.
 */
public class Zombie extends Sprite {

    private final static int ZOMBIE_RADIUS = 50;
    private final static int SCALER = 3;

    private boolean isSwingToLeft = true;
    public int stepLength = 10;
    private int leftFootX = -stepLength;
    private int rightFootX = stepLength;

    public int speed = 1;
    private int newSpeed = 1;

    public int speedRatio = 1;

    private boolean isFozen = false;

    public int nSize = 3;

    private int iceTime = 0;


    public Color mainColor = Color.red;


    public Point handPoint;

    /**
     * Constructor for creating a Zombie with a specified y-coordinate.
     *
     * @param y The y-coordinate for the zombie's initial position.
     */
    public Zombie(int y){
        super();

        ArrayList<Point> pntCs = new ArrayList<Point>();

        setRadius(ZOMBIE_RADIUS);


        if (y<100){
            y = 300;
        }

        setPointCenter(new Point(1190, y));

        setDeltaX(-speed*0.5);

        pntCs.add(new Point(0*SCALER, 18*SCALER));

        //right points
        pntCs.add(new Point(10*SCALER, 18*SCALER));
        pntCs.add(new Point(12*SCALER, 20*SCALER));
        pntCs.add(new Point(12*SCALER, 18*SCALER));
        pntCs.add(new Point(12*SCALER, 0*SCALER));
        pntCs.add(new Point(11*SCALER, -2*SCALER));
        pntCs.add(new Point(4*SCALER, -3*SCALER));
        pntCs.add(new Point(2*SCALER, -10*SCALER));


        //left points
        pntCs.add(new Point(-2*SCALER, -10*SCALER));
        pntCs.add(new Point(-4*SCALER, -3*SCALER));
        pntCs.add(new Point(-11*SCALER, -2*SCALER));
        pntCs.add(new Point(-12*SCALER, 0*SCALER));
        pntCs.add(new Point(-12*SCALER, 18*SCALER));

        //setObjectPoints(pntCs);
        assignPolarPoints(pntCs);
        setOrientation(-100);
    }

    /**
     * Handles the movement of the zombie.
     */
    public void move(){
        super.move();

        int x = getPointCenter().x;
        int y = getPointCenter().y;

        int xUpdate = x+(int)getDeltaX();
        setPointCenter(new Point(xUpdate, y));

        if(xUpdate<50){
            System.out.println("Game Over....................");
            CommandCenter.zombieCrossYard();
        }

    }

    /**
     * Draws the zombie on the screen.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        updateSpeed();
        if(speed==0){
            g.setColor(Color.cyan);
            g.fillRect(getPointCenter().x-35, getPointCenter().y-50, 63, 92);
            iceTime++;
        }

        if(iceTime==200){
            recover();
        }

        if (nSize >= 2){
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

        g.setColor(mainColor);
        if(leftFootX < -stepLength){
            isSwingToLeft = false;
        }
        else if(leftFootX > stepLength){
            isSwingToLeft = true;
        }

        if (isSwingToLeft){
            leftFootX=leftFootX-speed;
            rightFootX=rightFootX+speed;
        }
        else {
            leftFootX=leftFootX+speed;
            rightFootX=rightFootX-speed;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(getPointCenter().x+3,getPointCenter().y+20,getPointCenter().x-leftFootX, getPointCenter().y+40);
        g2.drawLine(getPointCenter().x+3,getPointCenter().y+20,getPointCenter().x-rightFootX, getPointCenter().y+40);

        g2.setStroke(new BasicStroke(2));
        handPoint = new Point(getPointCenter().x-15, getPointCenter().y+20);
        g2.drawLine(getPointCenter().x,getPointCenter().y+10, (int)handPoint.getX(), (int)handPoint.getY());
        g2.drawLine(getPointCenter().x,getPointCenter().y+10, getPointCenter().x+12, getPointCenter().y+18);

        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Returns the size of the zombie.
     *
     * @return The size of the zombie.
     */
    public int getSize(){
        return nSize;
    }

    /**
     * Handles the effect when the zombie is hit by a bullet.
     *
     * @param bulletType The type of bullet that hit the zombie.
     */
    public void isHit(int bulletType){
        nSize--;
        if (bulletType==1){
            frozen();
        }
    }

    /**
     * Freezes the zombie, stopping its movement temporarily.
     */
    public void frozen(){
        speed = 0;
        iceTime = 0;
        setDeltaX(-speed*0.25);
        isFozen = true;
    }

    /**
     * Recovers the zombie's speed after being frozen.
     */
    public void recover(){
        speed =checkSpeed();
        iceTime = 0;
        setDeltaX(-speed*0.5);
    }

    /**
     * Checks and returns the speed of the zombie based on its state and level.
     *
     * @return The current speed of the zombie.
     */
    public int checkSpeed(){
        if(isFozen){
            newSpeed = 0;
            return newSpeed;
        }

        if(CommandCenter.getLevel()>2){
            newSpeed = 4;
            setDeltaX(-speed*0.5);
            stepLength = 14;
        }
        else {
            newSpeed = 1; // double size?
        }
        return newSpeed;
    }

    /**
     * Updates the zombie's speed based on its state and level.
     */
    public void updateSpeed(){
        speed = checkSpeed()*speedRatio;
        setDeltaX(-speed*0.5);
    }
}
